CREATE TABLE inspection_places (
inspection_place_id INTEGER PRIMARY KEY AUTOINCREMENT,
inspection_place TEXT
);

CREATE TABLE inspection_types (
inspection_type_id INTEGER PRIMARY KEY AUTOINCREMENT,
inspection_type VARCHAR(100)
);

CREATE TABLE responsible (
responsible_id INTEGER PRIMARY KEY AUTOINCREMENT,
responsible VARCHAR(100)
);

CREATE TABLE regular_conditions (
regular_condition_id INTEGER PRIMARY KEY AUTOINCREMENT,
regular_condition VARCHAR(100)
);

CREATE TABLE device_locations (
device_location_id INTEGER PRIMARY KEY AUTOINCREMENT,
device_location VARCHAR(100)
);

CREATE TABLE measurement_types (
measurement_type_id INTEGER PRIMARY KEY AUTOINCREMENT,
measurement_type VARCHAR(100)
);

CREATE TABLE devices (
device_id INTEGER PRIMARY KEY AUTOINCREMENT,
device_name_id INTEGER,
type VARCHAR(50),
range VARCHAR(30),
category VARCHAR(100),
factory_number VARCHAR(100),
last_inspection_date REAL,
next_inspection_date REAL,
inspection_frequency INTEGER,
inspection_place_id INTEGER,
inspection_protocol_number VARCHAR(100),
inspection_type_id INTEGER,
device_location_id INTEGER,
responsible VARCHAR(255),
regular_condition_id INTEGER,
measurement_type_id INTEGER,
history text,
FOREIGN KEY (inspection_place_id) REFERENCES inspection_places (inspection_place_id),
FOREIGN KEY (inspection_type_id) REFERENCES inspection_types (inspection_type_id),
FOREIGN KEY (device_name_id) REFERENCES device_names (device_name_id),
FOREIGN KEY (regular_condition_id) REFERENCES regular_conditions (regular_condition_id),
FOREIGN KEY (device_location_id) REFERENCES device_locations (device_location_id),
FOREIGN KEY (measurement_type_id) REFERENCES measurement_types (measurement_type_id)
ON DELETE CASCADE
);
