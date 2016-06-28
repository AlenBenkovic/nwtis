CREATE TABLE alebenkov_korisnici (
  user varchar(15) NOT NULL,
  pass varchar(25) NOT NULL,
  role int(1) NOT NULL DEFAULT '2',
  rang int(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (user)
);
