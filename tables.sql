CREATE TABLE IF NOT EXISTS users (
                                     id UUID PRIMARY KEY,
                                     password VARCHAR(255) NOT NULL,
                                     first_name VARCHAR(255),
                                     last_name VARCHAR(255),
                                     email VARCHAR(255) NOT NULL UNIQUE,
                                     phone VARCHAR(50),
                                     patronymic VARCHAR(255),
                                     role VARCHAR(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS unit_of_measurement (
                                                   id BIGSERIAL PRIMARY KEY,
                                                   unit VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS transport_method (
                                                id BIGSERIAL PRIMARY KEY,
                                                method VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS trade_direction (
                                               id BIGSERIAL PRIMARY KEY,
                                               direction VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS reason_for_seizure (
                                                  id BIGSERIAL PRIMARY KEY,
                                                  reason VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS discovery_method (
                                                id BIGSERIAL PRIMARY KEY,
                                                method VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS country (
                                       id UUID PRIMARY KEY,
                                       name VARCHAR(255) NOT NULL,
                                       short_name VARCHAR(50),
                                       code VARCHAR(50)
);

CREATE TABLE IF NOT EXISTS authority (
                                         id BIGSERIAL PRIMARY KEY,
                                         name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE IF NOT EXISTS cites_permit (
                                            id UUID PRIMARY KEY,
                                            permit_number VARCHAR(255),
                                            issue_date TIMESTAMP,
                                            expiry_date TIMESTAMP,
                                            company_name VARCHAR(255),
                                            object VARCHAR(255),
                                            quantity DOUBLE PRECISION,
                                            importer_country VARCHAR(255),
                                            exporter_country VARCHAR(255),
                                            purpose VARCHAR(255),
                                            remarks TEXT,
                                            protection_mark_number VARCHAR(255),
                                            status VARCHAR(50) -- Enum для статуса (used, unused, canceled)
);

CREATE TABLE IF NOT EXISTS incident (
                                        id UUID PRIMARY KEY,
                                        registered_at TIMESTAMP,
                                        species VARCHAR(255),
                                        description TEXT,
                                        count DOUBLE PRECISION,
                                        unit_of_measurement_id BIGSERIAL,
                                        authority_id BIGSERIAL,
                                        discovery_method_id BIGSERIAL,
                                        reason_for_seizure_id BIGSERIAL,
                                        transport_method_id BIGSERIAL,
                                        trade_direction_id BIGSERIAL,
                                        country_id UUID,
                                        hiding_method VARCHAR(255),
                                        suspected_origin_country VARCHAR(255),
                                        transit_countries TEXT,
                                        final_destination VARCHAR(255),
                                        law_used VARCHAR(255),
                                        punishment VARCHAR(255),
                                        additional_info TEXT,
                                        FOREIGN KEY (unit_of_measurement_id) REFERENCES unit_of_measurement(id),
                                        FOREIGN KEY (authority_id) REFERENCES authority(id),
                                        FOREIGN KEY (discovery_method_id) REFERENCES discovery_method(id),
                                        FOREIGN KEY (reason_for_seizure_id) REFERENCES reason_for_seizure(id),
                                        FOREIGN KEY (transport_method_id) REFERENCES transport_method(id),
                                        FOREIGN KEY (trade_direction_id) REFERENCES trade_direction(id),
                                        FOREIGN KEY (country_id) REFERENCES country(id)
);


CREATE TABLE if not exists transit_country (
     id BIGSERIAL PRIMARY KEY,  -- Автоматическая генерация идентификатора
     country_name VARCHAR(255) NOT NULL,  -- Название страны
     incident_id UUID NOT NULL,  -- Внешний ключ для инцидента
     CONSTRAINT fk_incident FOREIGN KEY (incident_id) REFERENCES incident (id) ON DELETE CASCADE
);