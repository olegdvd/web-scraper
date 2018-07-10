package org.kaidzen.webscrap.mapper;

import org.kaidzen.webscrap.model.IssuedLicense;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ObjectToCsvMapper implements Function<Collection<IssuedLicense>, List<String>> {

    private static final Logger LOG = LoggerFactory.getLogger(ObjectToCsvMapper.class);

    @Override
    public List<String> apply(Collection<IssuedLicense> objectsList) {
        if (objectsList.isEmpty()) return Collections.emptyList();
        return objectsList.stream()
                .map(IssuedLicense::toCsv)
                .collect(Collectors.toList());
    }
}
