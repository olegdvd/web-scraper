package org.kaidzen.webscrap.document.service;

import org.kaidzen.webscrap.document.dao.PermitDocumentDao;
import org.kaidzen.webscrap.document.model.PermitDocument;
import org.kaidzen.webscrap.document.util.WriteBulkToFile;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;

@Service
public class PermitDocumentService implements ScrapPermitService<PermitDocument> {

    private final WriteBulkToFile bulkToFile;
    private final PermitDocumentDao permitDocumentDao;

    public PermitDocumentService(PermitDocumentDao permitDocumentDao) {
        this.permitDocumentDao = permitDocumentDao;
        bulkToFile = new WriteBulkToFile();
    }

    @Override
    public void saveDocument(PermitDocument document) {
        permitDocumentDao.add(document);
    }

    @Override
    public void saveAll(List<PermitDocument> documents) {
        permitDocumentDao.addAll(documents);
    }

    @Override
    public List<PermitDocument> findAllDocuments() {
        return permitDocumentDao.getAll();
    }

    @Override
    public Path saveToFile(String fileName, List<String> licenses) {
        return bulkToFile.writeToFile(fileName, licenses);
    }

    public Path saveToFile(String fileName, List<String> licenses, String presentPageFilter) {
        return bulkToFile.writeToFile(fileName, licenses, presentPageFilter);
    }
}
