CREATE TABLE alebenkov_dnevnik (
	id integer NOT NULL PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
	korisnik varchar(15) NOT NULL DEFAULT '',
	akcija varchar(255) NOT NULL DEFAULT '',
	trajanje integer NOT NULL DEFAULT 0,
	vrijeme timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
);
