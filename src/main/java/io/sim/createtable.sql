CREATE TABLE sumodriving (
    id_sumodriving SERIAL PRIMARY KEY,
    time_stamp double precision NOT NULL,
    id_card VARCHAR(18) NOT NULL,
    id_route INT NOT NULL,
    speed float NOT NULL,
    distance float NOT NULL,
    fuel_comsumption float NOT NULL,
    fuel_type VARCHAR(20) NOT NULL,
    co2_emission float NOT NULL,
    longitude point NOT NULL,
    latitude point NOT NULL
);