CREATE TABLE IF NOT EXISTS users
(
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    name          TEXT        NOT NULL,
    email         TEXT UNIQUE NOT NULL,
    date_of_birth DATE,
    created_at    TIMESTAMP        DEFAULT NOW(),
    updated_at    TIMESTAMP        DEFAULT NOW()
);