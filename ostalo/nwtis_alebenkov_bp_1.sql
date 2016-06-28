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

CREATE TABLE alebenkov_meteo (
  idMeteo integer NOT NULL AUTO_INCREMENT,
  idAdresa integer NOT NULL,				
  adresaStanice varchar(255) NOT NULL DEFAULT '',
  latitude varchar(25) NOT NULL DEFAULT '',
  longitude varchar(25) NOT NULL DEFAULT '',
  vrijeme varchar(25) NOT NULL DEFAULT '',
  vrijemeOpis varchar(25) NOT NULL DEFAULT '',
  temp float NOT NULL DEFAULT -999,
  tempMin float NOT NULL DEFAULT -999,
  tempMax float NOT NULL DEFAULT -999,
  vlaga float NOT NULL DEFAULT -999,
  tlak float NOT NULL DEFAULT -999,
  vjetar float NOT NULL DEFAULT -999,
  vjetarSmjer float NOT NULL DEFAULT -999,
  preuzeto timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (idMeteo),
  CONSTRAINT meteo_FK1 FOREIGN KEY (idAdresa) REFERENCES alebenkov_adrese (id)
);

CREATE TABLE alebenkov_meteoPrognoza (
  idPrognoza integer NOT NULL AUTO_INCREMENT,
  idAdresa integer NOT NULL,				
  adresaStanice varchar(255) NOT NULL DEFAULT '',
  latitude varchar(25) NOT NULL DEFAULT '',
  longitude varchar(25) NOT NULL DEFAULT '',
  vrijeme varchar(25) NOT NULL DEFAULT '',
  vrijemeOpis varchar(25) NOT NULL DEFAULT '',
  temp float NOT NULL DEFAULT -999,
  tempMin float NOT NULL DEFAULT -999,
  tempMax float NOT NULL DEFAULT -999,
  vlaga float NOT NULL DEFAULT -999,
  tlak float NOT NULL DEFAULT -999,
  vjetar float NOT NULL DEFAULT -999,
  vjetarSmjer float NOT NULL DEFAULT -999,
  prognozaZa timestamp NOT NULL,
  preuzeto timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (idPrognoza),
  CONSTRAINT meteo_FK2 FOREIGN KEY (idAdresa) REFERENCES alebenkov_adrese (id)
);
