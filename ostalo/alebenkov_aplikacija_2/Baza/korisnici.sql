CREATE TABLE alebenkov_korisnici (
	id integer NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	korisnik varchar(15) NOT NULL DEFAULT '' UNIQUE,
	pass varchar(25) NOT NULL DEFAULT '',
	role integer NOT NULL DEFAULT 2,
	rang integer NOT NULL DEFAULT 1,
	odobreno integer NOT NULL DEFAULT 0
);
