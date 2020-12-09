DROP TABLE public.superhero_award;

ALTER TABLE public.award ADD COLUMN superhero_id INT8 NULL;

ALTER TABLE public.award ADD CONSTRAINT superhero_award_fk FOREIGN KEY (superhero_id) REFERENCES superhero(id)