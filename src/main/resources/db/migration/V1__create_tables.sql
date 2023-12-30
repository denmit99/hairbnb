CREATE TABLE IF NOT EXISTS user_info (
    id SERIAL PRIMARY KEY,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    role TEXT NOT NULL,
    creation_date timestamptz NOT NULL,
    last_login_date timestamptz NOT NULL
);

CREATE TABLE IF NOT EXISTS token (
    id SERIAL PRIMARY KEY,
    user_id BIGINT REFERENCES user_info(id),
    token TEXT NOT NULL,
    expired BOOLEAN NOT NULL DEFAULT FALSE,
    revoked BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS public.listing (
    id SERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL REFERENCES user_info(id),
    title TEXT NOT NULL,
    description TEXT NOT NULL,
    address TEXT NOT NULL,
    price INT NOT NULL,
    currency TEXT NOT NULL,
    property_type TEXT NOT NULL,
    place_type TEXT NOT NULL,
    max_guests INT NOT NULL,
    num_of_bathrooms INT NOT NULL,
    creation_date timestamptz NOT NULL,
    update_date timestamptz NOT NULL
);

CREATE TABLE IF NOT EXISTS public.bed_arrangement (
    id SERIAL PRIMARY KEY,
    listing_id BIGINT NOT NULL REFERENCES public.listing(id),
    room_number INT NOT NULL,
    bed_type TEXT NOT NULL,
    number_of_beds INT NOT NULL
);

CREATE TABLE IF NOT EXISTS public.amenity_category (
    id SERIAL PRIMARY KEY,
    code TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS public.amenity (
    id SERIAL PRIMARY KEY,
    category_id BIGINT NOT NULL REFERENCES public.amenity_category(id),
    code TEXT NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS public.listing_amenity (
    id SERIAL PRIMARY KEY,
    listing_id BIGINT NOT NULL REFERENCES public.listing(id),
    amenity_id BIGINT NOT NULL REFERENCES public.amenity(id)
);

INSERT INTO public.amenity_category(id, code) VALUES
(1, 'DEFAULT'),
(2, 'SAFETY');

INSERT INTO public.amenity(category_id, code) VALUES
(1, 'WI_FI'),
(1, 'WASHING_MACHINE'),
(1, 'AIR_CONDITIONING'),
(1, 'TV'),
(1, 'HAIR_DRYER'),
(1, 'IRON'),
(1, 'ESSENTIALS'),
(1, 'COOKING_BASICS'),
(1, 'WORKSPACE'),
(1, 'HEATING'),
(1, 'KITCHEN'),
(1, 'PARKING'),
(1, 'POOL'),
(1, 'FIREPLACE'),
(2, 'SMOKE_DETECTOR'),
(2, 'FIRST_AID_KIT'),
(2, 'FIRE_EXTINGUISHER');

CREATE INDEX IF NOT EXISTS amenity_code_index ON public.amenity(code);
