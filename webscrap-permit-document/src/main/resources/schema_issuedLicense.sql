# DROP TABLE IF EXISTS issuedLicenses;

CREATE TABLE IF NOT EXISTS issuedLicenses_aug (

  licenseId   INT          NOT NULL,
  type        VARCHAR(100) NOT NULL,
  license     VARCHAR(40)  NOT NULL,
  edrpo       VARCHAR(20)  NOT NULL,
  theLicensee VARCHAR(228) NOT NULL,
  address     VARCHAR(200) NOT NULL,
  issueDate   DATE         NOT NULL,
  validToDate DATE         NOT NULL,
  timestamp   TIMESTAMP    NOT NULL DEFAULT now(),
  md5         VARCHAR(32)  NOT NULL,

  PRIMARY KEY (md5)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;