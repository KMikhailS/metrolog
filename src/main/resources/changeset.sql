ALTER TABLE devices ADD COLUMN next_inspection_date REAL;
ALTER TABLE devices ADD COLUMN history text;

CREATE TABLE device_names (
device_name_id INTEGER PRIMARY KEY AUTOINCREMENT,
device_name VARCHAR(255)
);

ALTER TABLE devices DROP COLUMN name;
ALTER TABLE devices DROP COLUMN responsible_id;

ALTER TABLE devices ADD COLUMN device_name_id;
ALTER TABLE devices ADD COLUMN responsible;

DROP TABLE devices;
DROP TABLE responsible;
