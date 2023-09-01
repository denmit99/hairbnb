CREATE TABLE IF NOT EXISTS public.listing (
    id SERIAL PRIMARY KEY,
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
    code TEXT NOT NULL
);

CREATE TABLE IF NOT EXISTS public.listing_amenity (
    id SERIAL PRIMARY KEY,
    listing_id BIGINT NOT NULL REFERENCES public.listing(id),
    amenity_id BIGINT NOT NULL REFERENCES public.amenity(id)
);
