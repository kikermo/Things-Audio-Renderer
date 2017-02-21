package org.kikermo.thingsaudioreceiver.util;

/**
 * Created by EnriqueR on 19/02/2017.
 */

public class Utils {
    public static boolean isNull(Object object) {
        return object == null;
    }

    public static boolean notNull(Object object) {
        return object != null;
    }


    public static String formatSeconds(int seconds) {
        int remSeconds = seconds % 60;
        int min = seconds / 60;
        int hour = min / 60;
        min = min % 60;

        String formatted = String.format("%02d:%02d", min, remSeconds);

        return ((hour > 0) ? hour + ":" + formatted : formatted);
    }
}
