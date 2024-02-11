CREATE TABLE IF NOT EXISTS public.file (
    id SERIAL PRIMARY KEY,
    data BYTEA NOT NULL,
    name TEXT NOT NULL,
    type TEXT NOT NULL,
    creation_date timestamptz NOT NULL,
    user_id BIGINT REFERENCES user_info(id)
)