CREATE TABLE public.battle_participant (
	id_battle int8 NOT NULL,
	id_participant int8 NOT NULL,
	CONSTRAINT battle_participant_pkey PRIMARY KEY (id_battle, id_participant),
	CONSTRAINT id_battle_fk FOREIGN KEY (id_battle) REFERENCES arena(id),
    CONSTRAINT id_participant_fk FOREIGN KEY (id_participant) REFERENCES superhero(id)
);