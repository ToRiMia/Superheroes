CREATE TABLE public.battle_progress (
	id int8 NOT NULL,
	attacker_id int8 NOT NULL,
	defender_id int8 NOT NULL,
	damage int4 NOT NULL,
	residual_health int4 NOT NULL
);