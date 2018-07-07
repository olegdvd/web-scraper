DROP TABLE IF EXISTS issuedLicenses;

CREATE TABLE IF NOT EXISTS issuedLicenses (

  id          INT          NOT NULL,
  type        VARCHAR(100) NOT NULL,
  license     VARCHAR(14)  NOT NULL,
  edrpo       INT          NOT NULL,
  theLicensee VARCHAR(180) NOT NULL,
  address     VARCHAR(200) NOT NULL,
  issueDate   DATE         NOT NULL,
  validToDate DATE         NOT NULL,
  timestamp   TIMESTAMP DEFAULT NOT NULL,
  PRIMARY KEY (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8;