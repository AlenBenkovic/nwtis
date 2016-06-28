CREATE DATABASE nwtis_alebenkov_bp_1
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;

CREATE TABLE alebenkov_adrese (
  id integer NOT NULL AUTO_INCREMENT,
  adresa varchar(255) NOT NULL,
  latitude varchar(25) NOT NULL,
  longitude varchar(25) NOT NULL,
  kreirao varchar(15) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE alebenkov_dnevnik (
  id integer NOT NULL AUTO_INCREMENT,
  user varchar(15) NOT NULL,
  naredba varchar(255) NOT NULL,
  odgovor varchar(255) NOT NULL,
  time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

CREATE TABLE alebenkov_korisnici (
  user varchar(15) NOT NULL,
  pass varchar(25) NOT NULL,
  role int(1) NOT NULL DEFAULT '2',
  rang int(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (user)
);
