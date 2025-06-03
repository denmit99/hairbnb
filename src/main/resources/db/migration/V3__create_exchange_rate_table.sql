CREATE TABLE IF NOT EXISTS public.exchange_rate_eur (
    id UUID PRIMARY KEY,
    currency TEXT NOT NULL,
    rate DECIMAL(18, 6) NOT NULL,
    date timestamptz NOT NULL
);

CREATE INDEX IF NOT EXISTS idx_exchange_rate_eur_currency_date ON exchange_rate_eur (currency, date DESC);