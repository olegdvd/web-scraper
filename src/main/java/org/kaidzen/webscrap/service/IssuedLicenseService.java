package org.kaidzen.webscrap.service;

import org.kaidzen.webscrap.model.IssuedLicense;
import org.kaidzen.webscrap.repository.IssuedLicenseRepository;
import org.kaidzen.webscrap.util.WriteBulkToFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
public class IssuedLicenseService implements IssuedService {

    private static final Logger LOG = LoggerFactory.getLogger(IssuedLicenseService.class);

    @Autowired
    private IssuedLicenseRepository licenseRepository;
    private final WriteBulkToFile bulkToFile;

    public IssuedLicenseService() {
        bulkToFile = new WriteBulkToFile();
    }

    @Override
    public void saveLicense(IssuedLicense license) {
        licenseRepository.save(license);
    }

    @Override
    public void saveAll(Collection<IssuedLicense> licenses) {
        licenseRepository.save(licenses);
        LOG.info("Saved to base rows: {}", licenses.size());
    }

    @Override
    public List<IssuedLicense> findAllLicenses() {
        return licenseRepository.findAll();
    }

    @Override
    public void saveToFile(String fileName, List<String> licenses) {
        bulkToFile.writeToFile(fileName, licenses);
    }


}
