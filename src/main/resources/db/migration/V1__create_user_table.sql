CREATE TABLE IF NOT EXISTS public.listing (
    id BIGINT PRIMARY KEY,
    name TEXT NOT NULL,
    description TEXT NOT NULL,
    address TEXT NOT NULL,
    price INT NOT NULL,
    property_type TEXT NOT NULL,
    bed_type TEXT NOT NULL,
    bed_number INT NOT NULL,
    bath_type TEXT NOT NULL,
    creation_date timestamptz NOT NULL,
    update_date timestamptz NOT NULL
)