package org.kaidzen.webscrap.service;

import org.kaidzen.webscrap.model.IssuedLicense;

import java.nio.file.Path;
import java.util.List;

public interface IssuedService {

    void saveLicense(IssuedLicense license);
    void saveAll(List<IssuedLicense> licenses);
    List<IssuedLicense> findAllLicenses();
    Path saveToFile(String fileName, List<String> licenses);
}
