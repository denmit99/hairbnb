CREATE TABLE IF NOT EXISTS public.file (
    id UUID PRIMARY KEY,
    data BYTEA NOT NULL,
    name TEXT NOT NULL,
    type TEXT NOT NULL,
    creation_date timestamptz NOT NULL,
    user_id UUID REFERENCES user_info(id)
)