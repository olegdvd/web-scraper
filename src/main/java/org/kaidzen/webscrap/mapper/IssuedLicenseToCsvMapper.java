package org.kaidzen.webscrap.mapper;

import org.kaidzen.webscrap.model.IssuedLicense;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class IssuedLicenseToCsvMapper implements Function<Collection<IssuedLicense>, List<String>> {

    @Override
    public List<String> apply(Collection<IssuedLicense> objectsList) {
        if (objectsList.isEmpty()) return Collections.emptyList();
        return objectsList.stream()
                .map(IssuedLicense::toCsv)
                .collect(Collectors.toList());
    }
}
