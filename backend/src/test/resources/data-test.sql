-- Truncate is faster
-- DROP TABLE IF EXISTS address, person, person_address, person_main_address, animal, dog, cat;
--
-- CREATE TABLE `address` (
--   `id` bigint(20) NOT NULL AUTO_INCREMENT,
--   `city_name` varchar(30) NOT NULL,
--   `street_name` varchar(50) NOT NULL,
--   `zip_code` varchar(6) NOT NULL,
--   PRIMARY KEY (`id`)
-- ) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
--
-- CREATE TABLE `animal` (
--   `id` bigint(20) NOT NULL AUTO_INCREMENT,
--   `animal_identifier` varchar(8) DEFAULT NULL,
--   `available_for_adoption` varchar(25) NOT NULL,
--   `behavior_description` varchar(500) DEFAULT NULL,
--   `date_of_birth` date DEFAULT NULL,
--   `name` varchar(25) NOT NULL,
--   `type` varchar(15) NOT NULL,
--   `previous_owner_id` bigint(20) DEFAULT NULL,
--   PRIMARY KEY (`id`),
--   UNIQUE KEY `UK_ma2mbfpfpm7nj8otvpntscx66` (`animal_identifier`),
--   KEY `FKcrf9elpwsi2uvvikdvdhiltk4` (`previous_owner_id`)
-- ) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
--
-- CREATE TABLE `cat` (
--   `cat_race` varchar(15) NOT NULL,
--   `id` bigint(20) NOT NULL,
--   PRIMARY KEY (`id`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--
-- CREATE TABLE `dog` (
--   `dog_race` varchar(15) NOT NULL,
--   `id` bigint(20) NOT NULL,
--   PRIMARY KEY (`id`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--
-- CREATE TABLE `person` (
--   `id` bigint(20) NOT NULL AUTO_INCREMENT,
--   `date_of_registration` date NOT NULL,
--   `first_name` varchar(30) NOT NULL,
--   `last_name` varchar(50) NOT NULL,
--   PRIMARY KEY (`id`)
-- ) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
--
-- CREATE TABLE `person_address` (
--   `person_id` bigint(20) DEFAULT NULL,
--   `address_id` bigint(20) NOT NULL,
--   PRIMARY KEY (`address_id`),
--   KEY `FKnndfs0btabect8upo03uwgfxt` (`person_id`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--
-- CREATE TABLE `person_main_address` (
--   `address_id` bigint(20) DEFAULT NULL,
--   `person_id` bigint(20) NOT NULL,
--   PRIMARY KEY (`person_id`),
--   KEY `FK8689fvht85svevbyo38k5w69e` (`address_id`)
-- ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
--
-- TRUNCATE TABLE address;
-- TRUNCATE TABLE person;
-- TRUNCATE TABLE person_address;
-- TRUNCATE TABLE person_main_address;
-- TRUNCATE TABLE animal;
-- TRUNCATE TABLE dog;
-- TRUNCATE TABLE cat;

DELETE FROM person_animal WHERE person_animal.person_id> 0;
DELETE FROM person_address WHERE person_address.person_id > 0;
DELETE FROM person_main_address WHERE person_main_address.person_id > 0;
DELETE FROM address WHERE address.id > 0;
DELETE FROM person WHERE person.id> 0;
DELETE FROM dog WHERE dog.id > 0;
DELETE FROM cat WHERE cat.id > 0;
DELETE FROM animal WHERE animal.id > 0;

INSERT INTO `address` VALUES (1, 'Katowice', 'Krakowska', '47-789'), (2, 'Kraków', 'Katowicka', '65-789');

INSERT INTO `person` VALUES (1, '2017-10-16', 'Dany', 'Devito'), (2, '2017-10-16', 'Bany', 'Devito');

INSERT INTO `person_address` VALUES (1, 1), (1, 2);

INSERT INTO `person_main_address` VALUES (1, 1);

INSERT INTO `animal` VALUES (1, '00150001', 'AVAILABLE', 'Good boy', CURDATE(), 'Sparky', 'DOG'),
  (2, '00150002', 'BEFORE_VACCINATION', 'Sleeps all day', CURDATE(), 'Puffy', 'CAT');

INSERT INTO `dog` VALUES ('GERMAN_SHEPERD', 1);

INSERT INTO `cat` VALUES ('PERSIAN', 2);

INSERT INTO `person_animal` VALUES (1, 1);