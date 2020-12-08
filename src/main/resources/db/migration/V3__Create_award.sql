CREATE TABLE IF NOT EXISTS public.award (
	id int8 NOT NULL,
	"name" varchar(255) NOT NULL,
	rarity varchar(255) NOT NULL,
	CONSTRAINT award_pkey PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS public.superhero_award (
	superhero_id int8 NOT NULL,
	award_id int8 NOT NULL,
	CONSTRAINT superhero_award_pkey PRIMARY KEY (superhero_id, award_id),
	CONSTRAINT superhero_id_fk FOREIGN KEY (superhero_id) REFERENCES superhero(id),
	CONSTRAINT award_id_fk FOREIGN KEY (award_id) REFERENCES award(id)
);
