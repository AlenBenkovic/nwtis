CREATE TABLE alebenkov_korisnici (
	korisnik varchar(15) NOT NULL PRIMARY KEY,
	prezime varchar(15) NOT NULL DEFAULT '',
	pass varchar(25) NOT NULL DEFAULT '',
	mail varchar(25) NOT NULL DEFAULT'' UNIQUE,
	role integer NOT NULL DEFAULT 2,
	rang integer NOT NULL DEFAULT 1,
	odobreno integer NOT NULL DEFAULT 0
);
