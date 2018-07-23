package org.kaidzen.webscrap.util;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class Md5CalculatorTest {

    private static final String ONE_STRING = "First string";
    private static final String NEXT_STRING = "Second string";

    @Test
    public void calculateMd5() {
        String md5FirstVariant = Md5Calculator.calculateMd5(ONE_STRING, NEXT_STRING);
        String md5SecondVariant = Md5Calculator.calculateMd5(NEXT_STRING, ONE_STRING);
        String md5ThirdVariant = Md5Calculator.calculateMd5(ONE_STRING);
        String md5FourthVariant = Md5Calculator.calculateMd5(ONE_STRING);

        assertNotEquals(md5FirstVariant, md5SecondVariant);
        assertEquals(md5FourthVariant, md5ThirdVariant);
    }
}