CREATE TABLE public.superhero (
	id int8 NOT NULL,
	age int4 NOT NULL,
	first_name varchar(255),
	last_name varchar(255),
	nickname varchar(255),
	super_power varchar(1000),
	CONSTRAINT superhero_pkey PRIMARY KEY (id)
);

CREATE TABLE public.superhero_list_of_enemies (
	superhero_id int8 NOT NULL,
	list_of_enemies_id int8 NOT NULL,
	CONSTRAINT superhero_with_enemy_id_fk FOREIGN KEY (superhero_id) REFERENCES superhero(id),
	CONSTRAINT enemy_id_fk FOREIGN KEY (list_of_enemies_id) REFERENCES superhero(id)
);

CREATE TABLE public.superhero_list_of_friends (
	superhero_id int8 NOT NULL,
	list_of_friends_id int8 NOT NULL,
	CONSTRAINT friend_id_fk FOREIGN KEY (list_of_friends_id) REFERENCES superhero(id),
	CONSTRAINT superhero_with_friend_id_fk FOREIGN KEY (superhero_id) REFERENCES superhero(id)
);

CREATE SEQUENCE IF NOT EXISTS hibernate_sequence START 1;


