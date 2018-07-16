package org.kaidzen.webscrap.service;

import org.kaidzen.webscrap.model.IssuedLicense;

import java.util.List;

public interface IssuedService {

    void saveLicense(IssuedLicense license);
    void saveAll(List<IssuedLicense> licenses);
    List<IssuedLicense> findAllLicenses();
    void saveToFile(String fileName, List<String> licenses);
}
