CREATE TABLE IF NOT EXISTS tickets (
    id BIGSERIAL PRIMARY KEY,
    person_id BIGSERIAL NOT NULL,
    plane_id BIGSERIAL NOT NULL,
    city_from VARCHAR(100) NOT NULL,
    city_to VARCHAR(100) NOT NULL
);

CREATE TABLE IF NOT EXISTS persons (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100)
);

CREATE TABLE IF NOT EXISTS planes (
    id BIGSERIAL PRIMARY KEY,
    model VARCHAR(50) NOT NULL
);

CREATE INDEX IF NOT EXISTS plane_model_idx ON planes (model);
CREATE INDEX IF NOT EXISTS tickets_city_from_idx ON tickets (city_from);
CREATE INDEX IF NOT EXISTS tickets_city_to_idx ON tickets (city_to);