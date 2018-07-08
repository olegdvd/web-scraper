package org.kaidzen.webscrap.service;

import org.kaidzen.webscrap.model.IssuedLicense;

import java.util.Collection;
import java.util.List;

public interface IssuedService {

    void saveLicense(IssuedLicense license);
    void saveAll(Collection<IssuedLicense> licenses);
    List<IssuedLicense> findAllLicenses();
}