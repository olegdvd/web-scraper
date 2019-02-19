package org.kaidzen.webscrap.document.mapper;

import org.kaidzen.webscrap.document.model.PermitDocument;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class PermitDocumentToCsvMapper implements Function<Collection<PermitDocument>, List<String>> {

    @Override
    public List<String> apply(Collection<PermitDocument> objectsList) {
        if (objectsList.isEmpty()) return Collections.emptyList();
        return objectsList.stream()
                .map(PermitDocument::toCsv)
                .collect(Collectors.toList());
    }
}
