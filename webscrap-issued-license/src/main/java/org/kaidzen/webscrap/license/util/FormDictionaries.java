package org.kaidzen.webscrap.license.util;

import com.google.common.collect.ImmutableMap;

public class FormDictionaries {

    public static ImmutableMap<String, String> regions() {
        return new ImmutableMap.Builder<String, String>()
                .put("99", "ДАБІ")
                .put("1", "АР Крим")
                .put("2", "Вінницька")
                .put("3", "Волинська")
                .put("4", "Дніпропетровська")
                .put("5", "Донецька")
                .put("6", "Житомирська")
                .put("7", "Закарпатська")
                .put("8", "Запорізька")
                .put("9", "Івано-Франківська")
                .put("10", "Київська")
                .put("11", "Кіровоградська")
                .put("12", "Луганська")
                .put("13", "Львівська")
                .put("14", "Миколаївська")
                .put("15", "Одеська")
                .put("16", "Полтавська")
                .put("17", "Рівненська")
                .put("18", "Сумська")
                .put("19", "Тернопільська")
                .put("20", "Харківська")
                .put("21", "Херсонська")
                .put("22", "Хмельницька")
                .put("23", "Черкаська")
                .put("24", "Чернівецька")
                .put("25", "Чернігівська")
                .put("26", "м.Київ")
                .put("27", "м.Севастополь")
                .build();
    }

    public ImmutableMap<String, String> years() {
        return new ImmutableMap.Builder<String, String>()
                .put("2010", "2010")
                .put("2011", "2011")
                .put("2012", "2012")
                .put("2013", "2013")
                .put("2014", "2014")
                .put("2015", "2015")
                .put("2016", "2016")
                .put("2017", "2017")
                .put("2018", "2018")
                .build();
    }

    public ImmutableMap<String, String> months() {
        return new ImmutableMap.Builder<String, String>()
                .put("01", "січень")
                .put("02", "лютий")
                .put("03", "березень")
                .put("04", "квітень")
                .put("05", "травень")
                .put("06", "червень")
                .put("07", "липень")
                .put("08", "серпень")
                .put("09", "вересень")
                .put("10", "жовтень")
                .put("11", "листопад")
                .put("12", "грудень")
                .build();
    }

}
