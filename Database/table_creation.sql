CREATE TABLE IF NOT EXISTS house
(
id BIGINT,
owner_name TINYTEXT,
contact_number INT,
date_of_birth DATE,
blood_group TINYTEXT,
profession TINYTEXT,
house_name TINYTEXT,
address TINYTEXT,
area TINYTEXT,
city TINYTEXT,
size SMALLINT,
number_of_rooms TINYINT,
lock_phrase TINYTEXT,
key_phrase TINYTEXT,
service_discovery TINYTEXT,
date_of_registration DATE,
CONSTRAINT pk_house PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS room
(
id TINYINT,
house_id BIGINT,
name TINYTEXT,
number_of_appliances TINYTEXT,
CONSTRAINT pk_room PRIMARY KEY (id,house_id),
CONSTRAINT fk_room FOREIGN KEY (house_id) REFERENCES house (id) ON DELETE CASCADE
);

CREATE TABLE IF NOT EXISTS appliance
(
id TINYINT,
name TINYTEXT,
room_id TINYINT,
house_id BIGINT,
power_control TINYINT,
status TINYINT,
change_executed TINYINT,
CONSTRAINT pk_appliance PRIMARY KEY (id,room_id,house_id),
CONSTRAINT fk_appliance FOREIGN KEY (room_id, house_id) REFERENCES room(id, house_id) ON DELETE CASCADE
);