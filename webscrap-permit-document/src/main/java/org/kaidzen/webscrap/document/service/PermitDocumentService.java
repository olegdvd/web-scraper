package org.kaidzen.webscrap.document.service;

import org.kaidzen.webscrap.document.model.PermitDocument;
import org.kaidzen.webscrap.document.util.WriteBulkToFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;

@Service
public class PermitDocumentService implements ScrapService<PermitDocument> {

    private static final Logger LOG = LoggerFactory.getLogger(PermitDocumentService.class);

    private final WriteBulkToFile bulkToFile;

    public PermitDocumentService() {
        bulkToFile = new WriteBulkToFile();
    }

    @Override
    public void saveLicense(PermitDocument license) {

    }

    @Override
    public void saveAll(List<PermitDocument> licenses) {

    }

    @Override
    public List<PermitDocument> findAllLicenses() {
        return null;
    }

    @Override
    public Path saveToFile(String fileName, List<String> licenses) {
        return bulkToFile.writeToFile(fileName, licenses);
    }

    public Path saveToFile(String fileName, List<String> licenses, String presentPageFilter) {
        return bulkToFile.writeToFile(fileName, licenses, presentPageFilter);
    }
}
