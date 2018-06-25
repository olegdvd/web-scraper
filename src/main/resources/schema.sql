DROP TABLE IF EXISTS issuedLicenses;

CREATE TABLE issuedLicenses (

  id INT NOT NULL,
  type VARCHAR(100) NOT NULL,
  license VARCHAR(14) NOT NULL,
  edrpo INT NOT NULL,
  theLicensee VARCHAR(180) NOT NULL,
  adress VARCHAR(200) NOT NULL,
  issueDate DATE NOT NULL,
  validToDate DATE NOT NULL,

  PRIMARY KEY (id));