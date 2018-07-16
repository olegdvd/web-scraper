package org.kaidzen.webscrap.dao;

import java.util.List;

public interface GeneralDao<T> {
    List<T> getAllLicensees();

    T getLicenseById(Integer licenseId);

    void addLicense(T license);

    void addAllLicenses(List<T> licenses);

    void updateLicense(T license);

    void deleteLicense(Integer licenseId);

    boolean isLicenseIdExists(Integer licenseId);
}
