CREATE TABLE IF NOT EXISTS user_info (
    id UUID PRIMARY KEY,
    first_name TEXT,
    last_name TEXT,
    email TEXT NOT NULL UNIQUE,
    password TEXT NOT NULL,
    role TEXT NOT NULL,
    creation_date timestamptz NOT NULL,
    last_login_date timestamptz NOT NULL
);

CREATE TABLE IF NOT EXISTS token (
    id UUID PRIMARY KEY,
    user_id UUID REFERENCES user_info(id),
    token TEXT NOT NULL,
    refresh_token TEXT NOT NULL,
    expired BOOLEAN NOT NULL DEFAULT FALSE,
    revoked BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS public.listing (
    id UUID PRIMARY KEY,
    user_id UUID NOT NULL REFERENCES user_info(id),
    title TEXT NOT NULL,
    description TEXT NOT NULL,
    country TEXT NOT NULL,
    city TEXT NOT NULL,
    street TEXT NOT NULL,
    house_number TEXT NOT NULL,
    zip_code TEXT NOT NULL,
    price DOUBLE PRECISION NOT NULL,
    currency TEXT NOT NULL,
    price_usd DOUBLE PRECISION NOT NULL,
    property_type TEXT NOT NULL,
    place_type TEXT NOT NULL,
    max_guests INT NOT NULL,
    num_of_bathrooms INT NOT NULL,
    num_of_bedrooms INT NOT NULL,
    creation_date timestamptz NOT NULL,
    update_date timestamptz NOT NULL
);

CREATE TABLE IF NOT EXISTS public.bedroom (
    id UUID PRIMARY KEY,
    listing_id UUID NOT NULL REFERENCES public.listing(id),
    room_number INT NOT NULL,
    single_beds_number INT NOT NULL,
    double_beds_number INT NOT NULL,
    queen_beds_number INT NOT NULL,
    sofa_beds_number INT NOT NULL
);

CREATE TABLE IF NOT EXISTS public.listing_amenity (
    id UUID PRIMARY KEY,
    listing_id UUID NOT NULL REFERENCES public.listing(id),
    amenity_code TEXT NOT NULL
);
