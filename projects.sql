-- Table: public.projects

-- DROP TABLE public.projects;
DROP TABLE IF EXISTS public.projects;
DROP SEQUENCE IF EXISTS public.seq_projects_id;


CREATE SEQUENCE public.seq_projects_id
  INCREMENT 1
  MINVALUE 1
  MAXVALUE 9223372036854775807
  START 1
  CACHE 1;
ALTER TABLE public.seq_projects_id
  OWNER TO postgres;


CREATE TABLE public.projects
(
    id bigint NOT NULL DEFAULT nextval('seq_projects_id'::regclass),
    name text COLLATE pg_catalog."default",
    quota integer,
    CONSTRAINT projects_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE public.projects
    OWNER to postgres;