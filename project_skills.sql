-- Table: public.project_skills

DROP TABLE IF EXISTS public.project_skills;
DROP SEQUENCE IF EXISTS public.seq_project_skills_id;

CREATE SEQUENCE public.seq_project_skills_id
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.seq_project_skills_id
  OWNER TO postgres;

CREATE TABLE public.project_skills
(
    id bigint NOT NULL DEFAULT nextval('seq_project_skills_id'::regclass),
    project_id bigint,
    skill_name text COLLATE pg_catalog."default",
    CONSTRAINT project_skills_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.project_skills
    OWNER to postgres;