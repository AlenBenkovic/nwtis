CREATE DATABASE nwtis_alebenkov_bp_2
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;

USE nwtis_alebenkov_bp_2;


CREATE TABLE alebenkov_dnevnik (
  id integer NOT NULL AUTO_INCREMENT,
  user varchar(15) NOT NULL,
  akcija varchar(255) NOT NULL,
  trajanje integer(25) NOT NULL,
  time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);

CREATE TABLE alebenkov_korisnici (
  user varchar(15) NOT NULL,
  pass varchar(25) NOT NULL,
  email varchar(25) NOT NULL,
  role int(1) NOT NULL DEFAULT '2',
  rang int(1) NOT NULL DEFAULT '1',
  odobreno int(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (user)
);
