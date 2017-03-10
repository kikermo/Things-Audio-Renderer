# Things Audio Renderer

This project is an example of how to implement a very simple network audio player controlled by a RESTful API. It provides visual representation of the metadata asociated to the audio that is being played.

Usage
-----
When the app is running, there is a RESTful service listening on port 8080, on the IP address of the device. Calls are then made by doing GETs or POST using the format:
```
<ip of the device>:8080/<api method>
```

At this first stage, just a few API methods have been implemented for the basic functionality.

For adding songs to play, the API method is:
```
/songs/add
```

The request parameters are specified on a Json object and sent by ***POST*** request, ase the example below.
```json
[
  {
      "title": "Come together",
      "album": "Abbey Road",
      "artist": "The Beatles",
      "length": 258,
      "url": "http://192.168.1.34:9000/beatles/cometoguether.flac",
      "art": "http://192.168.1.34/beatles/abbey.jpg"
  },
  ...
]
```

This will add a list of songs and start playing from the first one.

The other api methods are accessible by ***GET*** request and are used to do basic control.

 ```
 /control/play
 ```
 This method will resume the playing of a song if paused previously


 ```
 /control/pause
 ```
 Calling this api method, the music is paused if previously playing.

```
/control/skp_next
/control/skip_prev
```

Skip songs forward or backwards on the list of songs.

Notes
-----

This project is on a very early stage, so you cannot expect it to work with no bugs. It was initially designed for a demo, but it has been considered to have some potential. Do not hesitate in getting involved either as a tester giving rising issues to give feedbakc or as a coder fixing code and pull requesting.