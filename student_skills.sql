-- Table: public.student_skills

-- DROP TABLE public.student_skills;
DROP TABLE IF EXISTS public.student_skills;
DROP SEQUENCE IF EXISTS public.seq_student_skills_id;

CREATE SEQUENCE public.seq_student_skills_id
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.seq_student_skills_id
  OWNER TO postgres;

CREATE TABLE public.student_skills
(
    id bigint NOT NULL DEFAULT nextval('seq_student_skills_id'::regclass),
    student_id bigint,
    skill_name text COLLATE pg_catalog."default",
    CONSTRAINT student_skills_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.student_skills
    OWNER to postgres;