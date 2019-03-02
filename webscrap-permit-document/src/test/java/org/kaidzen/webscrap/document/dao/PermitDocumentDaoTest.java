package org.kaidzen.webscrap.document.dao;

import org.junit.Test;
import org.kaidzen.webscrap.common.util.StandardTimeClock;
import org.kaidzen.webscrap.document.model.PermitDocument;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toMap;
import static org.junit.Assert.assertTrue;

public class PermitDocumentDaoTest {

    private List<String> ids = Arrays.asList(
            "ІУ 08216088010",
            "ІУ 14116091120",
            "ІУ 114160911157",
            "ІУ 114160911157"
    );

    List<PermitDocument> newDocuments = IntStream.range(0, ids.size()).boxed()
            .map(integer -> new PermitDocument.Builder(new StandardTimeClock())
                    .documentId(ids.get(integer))
                    .customer(String.valueOf(integer))
                    .build())
            .collect(toList());

    @Test
    public void addAllNonDuplicated() {
        List<PermitDocument> permitDocumentList = ids.stream()
                .map(id -> new PermitDocument.Builder(new StandardTimeClock())
                        .documentId(id)
                        .build())
                .collect(Collectors.toList());
        Map<String, PermitDocument> currentDocumentsPair = newDocuments.stream()
                .collect(toMap(PermitDocument::getMd5, Function.identity()));

        final PermitDocument permitDocument = new PermitDocument.Builder(new StandardTimeClock())
                .documentId("123456")
                .build();
        permitDocumentList.add(permitDocument);
        permitDocumentList.add(permitDocument);

        List<PermitDocument> unsavedDocuments = permitDocumentList.stream()
//                .filter(getPermitDocumentPredicate(currentDocumentsPair))
                .filter(document -> !currentDocumentsPair.containsKey(document.getMd5()))
                .collect(toList());
        assertTrue(unsavedDocuments.size() > 0);
    }

    private Predicate<PermitDocument> getPermitDocumentPredicate(Map<String, String> presentDocumentsIds) {
        return permitDocument -> !presentDocumentsIds.containsKey(permitDocument.getDocumentId());
    }
}