package org.kaidzen.webscrap.dao;

import org.kaidzen.webscrap.model.IssuedLicense;

import java.util.List;

public interface GeneralDao<T> {
    List<T> getAllLicensees();

    T getLicenseById(Integer licenseId);

    void addLicense(T license);

    int addAllLicenses(List<T> licenses);

    void updateLicense(T license);

    void deleteLicense(Integer licenseId);

    boolean isLicenseIdExists(Integer licenseId);

    void addAllNonDuplicatedLicenses(List<IssuedLicense> licenses);
}
