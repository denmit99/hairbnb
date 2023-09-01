CREATE TABLE IF NOT EXISTS public.listing (
    id BIGINT PRIMARY KEY,
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
)