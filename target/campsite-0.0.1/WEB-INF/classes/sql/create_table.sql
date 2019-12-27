CREATE TABLE `volcano_reservation` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `full_name` varchar(40) NOT NULL,
  `email` varchar(100) NOT NULL,
  `date_from` date NOT NULL,
  `date_to` date NOT NULL,
  PRIMARY KEY (`id`)
);