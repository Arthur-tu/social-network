CREATE TABLE IF NOT EXISTS users_scheme.likes
(
    id serial NOT NULL,
    user_id integer NOT NULL,
    post_id integer NOT NULL,
    CONSTRAINT likes_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS users_scheme.likes
    OWNER to artur;

CREATE TABLE IF NOT EXISTS users_scheme.posts
(
    id serial NOT NULL,
    user_id integer NOT NULL,
    text text COLLATE pg_catalog."default" NOT NULL,
    img_url character varying(255) COLLATE pg_catalog."default" NOT NULL,
    created_date timestamp without time zone NOT NULL,
    updated_date timestamp without time zone,
    CONSTRAINT posts_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS users_scheme.posts
    OWNER to artur;

CREATE TABLE IF NOT EXISTS users_scheme.subs
(
    id serial NOT NULL,
    user_id integer NOT NULL,
    target_id integer NOT NULL,
    date date NOT NULL,
    CONSTRAINT subs_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS users_scheme.subs
    OWNER to artur;

CREATE TABLE IF NOT EXISTS users_scheme.users
(
    id serial NOT NULL,
    name character varying(50) COLLATE pg_catalog."default" NOT NULL,
    surname character varying(50) COLLATE pg_catalog."default" NOT NULL,
    sex character varying(10) COLLATE pg_catalog."default",
    country character varying(50) COLLATE pg_catalog."default",
    phone character varying(11) COLLATE pg_catalog."default" NOT NULL,
    birth_date date NOT NULL,
    profile_text character varying(255) COLLATE pg_catalog."default",
    email character varying(150) COLLATE pg_catalog."default" NOT NULL,
    password_hash character varying(255) COLLATE pg_catalog."default" NOT NULL,
    img_url character varying(255) COLLATE pg_catalog."default",
    CONSTRAINT users_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS users_scheme.users
    OWNER to artur;   
	
CREATE TABLE IF NOT EXISTS users_scheme.comments
(
    id serial NOT NULL,
    user_id integer NOT NULL,
    post_id integer NOT NULL,
    text character varying(500) COLLATE pg_catalog."default" NOT NULL,
    created_date timestamp without time zone NOT NULL,
    updated_date timestamp without time zone,
    CONSTRAINT comments_pkey PRIMARY KEY (id)
)

TABLESPACE pg_default;

ALTER TABLE IF EXISTS users_scheme.comments
    OWNER to artur;

ALTER TABLE IF EXISTS users_scheme.users
    ADD COLUMN is_deleted boolean NOT NULL;