INSERT INTO `address` VALUES (1, 'Katowice', 'Krakowska', '47-789'), (2, 'Kraków', 'Katowicka', '65-789');

INSERT INTO `person` VALUES (1, '2017-10-16', 'Dany', 'Devito'), (2, '2017-10-16', 'Bany', 'Devito');

INSERT INTO `person_address` VALUES (1, 1), (1, 2);

INSERT INTO `person_main_address` VALUES (1, 1);

INSERT INTO `animal` VALUES (1, '00150001', 'ADOPTED', 'Good boy', '1999-11-06', CURDATE(), 'Sparky', 'DOG'),
  (2, '00150002', 'BEFORE_VACCINATION', 'Sleeps all day', '2010-11-12', CURDATE(), 'Puffy', 'CAT'),
  (3, '00150003', 'AVAILABLE', 'Such a boy', '2005-04-30', CURDATE(), 'Fire', 'DOG');

INSERT INTO `dog` VALUES ('GERMAN_SHEPERD', 1), ('CROSSBREAD', 3);

INSERT INTO `cat` VALUES ('PERSIAN', 2);

INSERT INTO `person_animal` VALUES (1, 1);