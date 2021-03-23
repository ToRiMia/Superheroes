CREATE TABLE public.user_superheroes (
	id_user varchar(255) NOT NULL,
	id_superhero int8 NOT NULL,
	CONSTRAINT user_superhero_pkey PRIMARY KEY (id_superhero),
	CONSTRAINT id_user_fk FOREIGN KEY (id_user) REFERENCES usr(id),
    CONSTRAINT id_superhero_fk FOREIGN KEY (id_superhero) REFERENCES superhero(id)
);