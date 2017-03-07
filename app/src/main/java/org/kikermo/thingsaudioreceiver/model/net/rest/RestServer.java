package org.kikermo.thingsaudioreceiver.model.net.rest;

import android.text.TextUtils;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.kikermo.thingsaudioreceiver.model.data.Track;
import org.kikermo.thingsaudioreceiver.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.List;

import static org.kikermo.thingsaudioreceiver.util.Utils.notNull;


public class RestServer implements Runnable {
    private static final String TAG = "RestServer";


    private static final int GET = 1;
    private static final int POST = 2;
    private static final int DELETE = 3;
    private static final int PUT = 4;

    private final int port;
    private boolean isRunning;
    private ServerSocket serverSocket;
    private RestCallback restCallback;
    private int currentResponseType;
    private Gson gson;


    public RestServer(int port) {
        this.port = port;
        restCallback = null;
        gson = new GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    public void setRestCallback(RestCallback restCallback) {
        this.restCallback = restCallback;
    }

    /**
     * This method starts the web server listening to the specified port.
     */
    public void start() {
        isRunning = true;
        new Thread(this).start();
    }

    /**
     * This method stops the web server
     */
    public void stop() {
        try {
            isRunning = false;
            if (null != serverSocket) {
                serverSocket.close();
                serverSocket = null;
            }
        } catch (IOException e) {
            Log.e(TAG, "Error closing the server socket.", e);
        }
    }

    public int getPort() {
        return port;
    }

    @Override
    public void run() {
        try {
            serverSocket = new ServerSocket(port);
            while (isRunning) {
                Socket socket = serverSocket.accept();
                handle(socket);
                socket.close();
            }
        } catch (SocketException e) {
            // The server was stopped; ignore.
        } catch (IOException e) {
            Log.e(TAG, "Web server error.", e);
        }
    }

    /**
     * Respond to a request from a client.
     *
     * @param socket The client socket.
     * @throws IOException
     */
    private void handle(Socket socket) throws IOException {
        BufferedReader reader = null;
        PrintStream output = null;
        try {
            String route = null;

            // Read HTTP headers and parse out the route.
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String line;
            while (!TextUtils.isEmpty(line = reader.readLine())) {
                boolean validType = getMessageType(line);
                if (validType) {
                    int start = line.indexOf('/') + 1;
                    int end = line.indexOf(' ', start);
                    route = line.substring(start, end);
                    break;
                } else {
                    writeServerError(output);
                }
            }
            String body = getBody(reader);

            // Output stream that we send the response to
            output = new PrintStream(socket.getOutputStream());

            // Prepare the content to send.
            if (null == route) {
                writeServerError(output);
                return;
            }
            byte[] response = new byte[0];
            switch (route) {
                case "/control/play":
                    if (notNull(restCallback))
                        restCallback.playReceived();
                    break;
                case "/control/pause":
                    if (notNull(restCallback))
                        restCallback.pauseReceived();
                    break;
                case "/control/skip_prev":
                    if (notNull(restCallback))
                        restCallback.skipPrevReceived();
                    break;
                case "/control/skip_next":
                    if (notNull(restCallback))
                        restCallback.playReceived();
                    break;
                case "/songs/add":
                    if (currentResponseType == GET) {
                        List<Track> trackList = gson.fromJson(body, new TypeToken<List<Track>>() {
                        }.getType());
                        if (notNull(restCallback))
                            restCallback.listReceived(trackList);
                    }
                    break;
                default:
                    writeNotFoundError(output);
                    return;
            }

//            byte[] bytes = loadContent(route);
//            if (null == bytes) {
//                writeServerError(output);
//                return;
//            }

            // Send out the content.
            output.println("HTTP/1.0 200 OK");
            if (response != null && response.length > 0) {
                output.println("Content-Type: application/json");
                output.println("Content-Length: " + response.length);
                output.println();
                output.write(response);
            }
            output.flush();
        } finally {
            if (null != output) {
                output.close();
            }
            if (null != reader) {
                reader.close();
            }
        }
    }

    /**
     * Writes a server error response (HTTP/1.0 500) to the given output stream.
     *
     * @param output The output stream.
     */
    private void writeServerError(PrintStream output) {
        output.println("HTTP/1.0 500 Internal Server Error");
        output.flush();
    }


    private void writeNotFoundError(PrintStream output) {
        output.println("HTTP/1.0 404 Not Found");
        output.flush();
    }

    private boolean getMessageType(String header) {
        if (header.startsWith("GET /"))
            currentResponseType = GET;
        else if (header.startsWith("POST /"))
            currentResponseType = POST;
        else if (header.startsWith("DELETE /"))
            currentResponseType = DELETE;
        else if (header.startsWith("PUT /"))
            currentResponseType = PUT;
        else
            return false;

        return true;
    }


    private String getBody(BufferedReader reader) {
        try {
            Log.d("Line", reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "[]";
    }
}
