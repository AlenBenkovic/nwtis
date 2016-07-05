CREATE TABLE alebenkov_korisnici (
	korisnik varchar(15) NOT NULL PRIMARY KEY,
	pass varchar(25) NOT NULL DEFAULT '',
	role integer NOT NULL DEFAULT 2,
	rang integer NOT NULL DEFAULT 1,
	odobreno integer NOT NULL DEFAULT 0
);
