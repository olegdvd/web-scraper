package org.kaidzen.webscrap.license.service;

import org.kaidzen.webscrap.license.dao.IssuedLicenseDao;
import org.kaidzen.webscrap.license.model.IssuedLicense;
import org.kaidzen.webscrap.license.util.WriteBulkToFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;

@Service
public class IssuedLicenseService implements ScrapService<IssuedLicense> {

    private static final Logger LOG = LoggerFactory.getLogger(IssuedLicenseService.class);
    private final IssuedLicenseDao issuedLicenseDao;
    private final WriteBulkToFile bulkToFile;

    public IssuedLicenseService(IssuedLicenseDao issuedLicenseDao) {
        this.issuedLicenseDao = issuedLicenseDao;
        bulkToFile = new WriteBulkToFile();
    }

    @Override
    public void saveLicense(IssuedLicense license) {
        issuedLicenseDao.add(license);
    }

    @Override
    public void saveAll(List<IssuedLicense> licenses){
        int wasSaved = issuedLicenseDao.addAll(licenses);
        LOG.info("Saved to base rows: {}", wasSaved);
    }

    @Override
    public List<IssuedLicense> findAllLicenses() {
        return issuedLicenseDao.getAll();
    }

    @Override
    public Path saveToFile(String fileName, List<String> licenses) {
        return bulkToFile.writeToFile(fileName, licenses);
    }
}
