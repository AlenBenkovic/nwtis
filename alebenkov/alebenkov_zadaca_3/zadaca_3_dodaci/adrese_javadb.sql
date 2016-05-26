CREATE TABLE adrese (
  idAdresa integer NOT NULL 
                PRIMARY KEY GENERATED ALWAYS AS IDENTITY 
                (START WITH 1, INCREMENT BY 1),
  adresa varchar(255) NOT NULL DEFAULT '' UNIQUE,
  latitude varchar(25) NOT NULL DEFAULT '',
  longitude varchar(25) NOT NULL DEFAULT ''
);