-- Table: public.students

-- DROP TABLE public.students;
DROP TABLE IF EXISTS public.students;
DROP SEQUENCE IF EXISTS public.seq_students_id;

CREATE SEQUENCE public.seq_students_id
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.seq_students_id
  OWNER TO postgres;

CREATE TABLE public.students
(
    id bigint NOT NULL DEFAULT nextval('seq_students_id'::regclass),
    name text COLLATE pg_catalog."default",
    preferredproject text COLLATE pg_catalog."default",
    allocatedproject text COLLATE pg_catalog."default",
    CONSTRAINT pkey_id PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.students
    OWNER to postgres;