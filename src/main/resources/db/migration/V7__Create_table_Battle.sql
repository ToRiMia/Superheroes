CREATE TABLE public.arena (
	id int8 NOT NULL,
	winner_id int8 NOT NULL,
	loser_id int8 NOT NULL,
	battle_time time NOT NULL,
	attack_number int4 NOT NULL,
	"date" date NOT NULL,
	CONSTRAINT battle_pkey PRIMARY KEY (id),
	CONSTRAINT superhero_winner_id_fk FOREIGN KEY (winner_id) REFERENCES superhero(id),
    CONSTRAINT superhero_loser_id_fk FOREIGN KEY (loser_id) REFERENCES superhero(id)
);