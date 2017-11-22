-- Truncate is faster
-- DROP TABLE IF EXISTS address, person, person_address, person_main_address, animal, dog, cat;
--
-- CREATE TABLE `address` (
--   `id` bigint(20) NOT NULL AUTO_INCREMENT,
--   `city_name` varchar(30) NOT NULL,
--   `street_name` varchar(50) NOT NULL,
--   `zip_code` varchar(6) NOT NULL,
--   PRIMARY KEY (`id`)
-- ) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
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
-- ) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=utf8;
--
-- CREATE TABLE `cat` (
--   `cat_race` varchar(15) NOT NULL,
--   `id` bigint(20) NOT NULL,
--   PRIMARY KEY (`id`)
-- ) ENGINE=MyISAM DEFAULT CHARSET=utf8;
--
-- CREATE TABLE `dog` (
--   `dog_race` varchar(15) NOT NULL,
--   `id` bigint(20) NOT NULL,
--   PRIMARY KEY (`id`)
-- ) ENGINE=MyISAM DEFAULT CHARSET=utf8;
--
-- CREATE TABLE `person` (
--   `id` bigint(20) NOT NULL AUTO_INCREMENT,
--   `date_of_registration` date NOT NULL,
--   `first_name` varchar(30) NOT NULL,
--   `last_name` varchar(30) NOT NULL,
--   PRIMARY KEY (`id`)
-- ) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8;
--
-- CREATE TABLE `person_address` (
--   `person_id` bigint(20) DEFAULT NULL,
--   `address_id` bigint(20) NOT NULL,
--   PRIMARY KEY (`address_id`),
--   KEY `FKnndfs0btabect8upo03uwgfxt` (`person_id`)
-- ) ENGINE=MyISAM DEFAULT CHARSET=utf8;
--
-- CREATE TABLE `person_main_address` (
--   `address_id` bigint(20) DEFAULT NULL,
--   `person_id` bigint(20) NOT NULL,
--   PRIMARY KEY (`person_id`),
--   KEY `FK8689fvht85svevbyo38k5w69e` (`address_id`)
-- ) ENGINE=MyISAM DEFAULT CHARSET=utf8;

TRUNCATE TABLE address;
TRUNCATE TABLE person;
TRUNCATE TABLE person_address;
TRUNCATE TABLE person_main_address;
TRUNCATE TABLE animal;
TRUNCATE TABLE dog;
TRUNCATE TABLE cat;

INSERT INTO `address` VALUES (1, 'Katowice', 'Krakowska', '47-789'), (2, 'Kraków', 'Katowicka', '65-789'),
  (3, 'Mysłowice', 'Gliwicka', '47-987'), (4, 'Gliwice', 'Mysłowicka', '98-784'),
  (5, 'Wrocław', 'Warszawska', '78-987'), (6, 'Warszawa', 'Wrocławska', '98-784'),
  (7, 'Czikago', 'Baker', '66-789'), (8, 'Arkanzas', 'Downtown', '12-345'),
  (9, 'Zamość', 'Jasnogórska', '64-745'), (10, 'Katowice', 'Górnośląska', '34-576'),
  (11, 'Katowice', 'Mysłowicka', '40-478'), (12, 'Mysłowice', 'Katowicka', '47-977');

INSERT INTO `person` VALUES (1, '2017-10-16', 'Dany', 'Devito'), (2, '2017-10-16', 'Bany', 'Devito'),
  (3, '2017-10-16', 'Nany', 'Devito'), (4, '2017-10-16', 'Without', 'Address'),
  (5, '2017-10-16', 'Devito', 'Kurkuma'), (6, '2017-10-16', 'Bill', 'Colab'),
  (7, '2017-10-16', 'Andrzej', 'Chrząszcz');

INSERT INTO `person_address` VALUES (1, 1), (1, 2), (1, 11), (1, 12), (2, 5), (2, 6), (3, 3), (3, 4),
  (5, 7), (5, 8), (6, 9), (7, 10);

INSERT INTO `person_main_address` VALUES (1, 1), (3, 3), (5, 2), (7, 5), (9, 6), (10, 7);

INSERT INTO `animal` VALUES (1, '00150001', 'BEFORE_VACCINATION', 'Good boy', CURDATE(), 'Frank', 'DOG', 1),
  (2, '00150002', 'AVAILABLE', 'Good boy', CURDATE(), 'Dino', 'DOG', 3),
  (3, '00150003', 'AVAILABLE', 'Sleeps all day', CURDATE(), 'Puffy', 'CAT', 2),
  (4, '00150004', 'AVAILABLE', 'Active at night', CURDATE(), 'Loco', 'CAT', 4);

INSERT INTO `dog` VALUES ('GERMAN_SHEPERD', 1), ('CROSSBREAD', 2);

INSERT INTO `cat` VALUES ('PERSIAN', 3), ('ROOFLANDER', 4);