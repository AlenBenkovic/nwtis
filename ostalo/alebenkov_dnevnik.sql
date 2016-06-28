CREATE TABLE alebenkov_dnevnik (
  id integer NOT NULL AUTO_INCREMENT,
  user varchar(15) NOT NULL,
  naredba varchar(255) NOT NULL,
  odgovor varchar(255) NOT NULL,
  time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id)
);
