package org.kaidzen.webscrap.repository;

import org.kaidzen.webscrap.model.IssuedLicense;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface IssuedLicenseRepository extends JpaRepository<IssuedLicense, Integer>{

    IssuedLicense findById(Integer  id);
    List<IssuedLicense> save(Collection<IssuedLicense> licenses);
}
