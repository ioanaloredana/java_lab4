-- Table: public.skills

-- DROP TABLE public.skills;
DROP TABLE IF EXISTS public.skills;
DROP SEQUENCE IF EXISTS public.seq_skills_id;

CREATE SEQUENCE public.seq_skills_id
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.seq_skills_id
  OWNER TO postgres;

CREATE TABLE public.skills
(
    id bigint NOT NULL DEFAULT nextval('seq_skills_id'::regclass),
    name text COLLATE pg_catalog."default",
    CONSTRAINT skills_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.skills
    OWNER to postgres;