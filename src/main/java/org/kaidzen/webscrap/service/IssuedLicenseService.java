package org.kaidzen.webscrap.service;

import org.kaidzen.webscrap.dao.IssuedLicenseDao;
import org.kaidzen.webscrap.model.IssuedLicense;
import org.kaidzen.webscrap.util.WriteBulkToFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;

@Service
public class IssuedLicenseService implements IssuedService {

    private static final Logger LOG = LoggerFactory.getLogger(IssuedLicenseService.class);
    private final IssuedLicenseDao issuedLicenseDao;
    private final WriteBulkToFile bulkToFile;

    public IssuedLicenseService(IssuedLicenseDao issuedLicenseDao) {
        this.issuedLicenseDao = issuedLicenseDao;
        bulkToFile = new WriteBulkToFile();
    }

    @Override
    public void saveLicense(IssuedLicense license) {
        issuedLicenseDao.addLicense(license);
    }

    @Override
    public void saveAll(List<IssuedLicense> licenses){
        issuedLicenseDao.addAllLicenses(licenses);
        LOG.info("Saved to base rows: {}", licenses.size());
    }

    @Override
    public List<IssuedLicense> findAllLicenses() {
        return issuedLicenseDao.getAllLicensees();
    }

    @Override
    public Path saveToFile(String fileName, List<String> licenses) {
        return bulkToFile.writeToFile(fileName, licenses);
    }
}
