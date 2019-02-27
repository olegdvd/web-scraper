CREATE TABLE IF NOT EXISTS permitdocuments (

documentId  VARCHAR(22) NOT NULL,
region  VARCHAR(22) NOT NULL,
documentType  VARCHAR(60) NOT NULL,
subject VARCHAR(400) NOT NULL,
category  VARCHAR(15) NOT NULL
customer VARCHAR(450) NOT NULL
techSupervision VARCHAR(100) NOT NULL
designer VARCHAR(100) NOT NULL
supervision VARCHAR(80) NOT NULL
contractor VARCHAR(100) NOT NULL
landInfo VARCHAR(100) NOT NULL
month VARCHAR(2) NOT NULL
year VARCHAR(4) NOT NULL
timestamp TIMESTAMP NOT NULL DEFAULT now(),
md5 VARCHAR(32)  NOT NULL,

PRIMARY KEY (md5)
)
  ENGINE = InnoDB
  DEFAULT CHARSET = utf8;