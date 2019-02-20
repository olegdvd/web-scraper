package org.kaidzen.webscrap.license.service;

import java.nio.file.Path;
import java.util.List;

public interface ScrapService<T> {

    void saveLicense(T license);
    void saveAll(List<T> licenses);
    List<T> findAllLicenses();
    Path saveToFile(String fileName, List<String> licenses);
}
