package org.kaidzen.webscrap.service;

import java.nio.file.Path;
import java.util.List;

public interface IssuedService<T> {

    void saveLicense(T license);
    void saveAll(List<T> licenses);
    List<T> findAllLicenses();
    Path saveToFile(String fileName, List<String> licenses);
}
