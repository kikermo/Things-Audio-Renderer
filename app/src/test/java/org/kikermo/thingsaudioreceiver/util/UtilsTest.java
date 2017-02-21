package org.kikermo.thingsaudioreceiver.util;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.kikermo.thingsaudioreceiver.util.Utils.*;
/**
 * Created by EnriqueR on 21/02/2017.
 */
public class UtilsTest {


    @Test
    public void correctFormattedSeconds() {
        final int input1 = 3656;
        final String result1 = "1:00:56";

        final int input2 = 2000;
        final String result2 = "33:20";

        assertEquals(result1,formatSeconds(input1));
        assertEquals(result2,formatSeconds(input2));
        assertNotEquals(result1,formatSeconds(input2));

    }

}