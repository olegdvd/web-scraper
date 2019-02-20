package org.kaidzen.webscrap.license.model;

import java.util.Arrays;
import java.util.List;

public class FormFilterConstants {

    //TODO change to immutable ones

    private static final List<String> years = Arrays.asList(
            //TODO check for 2010 - bug with requesting
            "2011", "2013", "2014", "2015", "2016", "2017", "2018"
    );
    private static final List<String> months = Arrays.asList(
            "01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12"
    );
    private static final List<String> regions = Arrays.asList(
            "99", "1", "2", "3", "4", "5", "6", "7", "8",
            "9", "10", "11", "12", "13", "14", "15", "16",
            "17", "18", "19", "20", "21", "22", "23",
            "24", "25", "26", "27"
    );

    public static List<String> getYears() {
        return years;
    }

    public static List<String> getMonths() {
        return months;
    }

    public static List<String> getRegions() {
        return regions;
    }
}
