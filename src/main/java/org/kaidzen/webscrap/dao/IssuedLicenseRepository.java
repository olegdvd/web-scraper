package org.kaidzen.webscrap.dao;

import org.kaidzen.webscrap.model.IssuedLicense;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IssuedLicenseRepository extends CrudRepository<IssuedLicense, Integer>{

}
