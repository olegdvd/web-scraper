package org.kaidzen.webscrap.document.service;

import java.nio.file.Path;
import java.util.List;

public interface ScrapPermitService<T> {

    void saveDocument(T document);
    void saveAll(List<T> documents);
    List<T> findAllDocuments();
    Path saveToFile(String fileName, List<String> documents);
}
