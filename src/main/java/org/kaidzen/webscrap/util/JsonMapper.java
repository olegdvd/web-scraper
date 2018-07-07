package org.kaidzen.webscrap.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.kaidzen.webscrap.model.IssuedLicense;

import java.util.List;

import static java.util.stream.Collectors.toList;

public class JsonMapper {

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private JsonMapper() {
    }
    public static List<String> toPrettyString(List<IssuedLicense> themes) {
        return themes.stream()
                .map(GSON::toJson)
                .collect(toList());
    }
}
