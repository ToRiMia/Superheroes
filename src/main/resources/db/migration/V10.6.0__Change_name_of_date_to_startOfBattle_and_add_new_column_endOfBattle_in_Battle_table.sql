ALTER TABLE public.battle RENAME COLUMN date TO start_of_battle;

ALTER TABLE public.battle ADD COLUMN end_of_battle timestamp;