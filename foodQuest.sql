-- MySQL dump 10.13  Distrib 5.7.16, for Linux (x86_64)
--
-- Host: localhost    Database: food_quest
-- ------------------------------------------------------
-- Server version	5.7.16-0ubuntu0.16.04.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cusine_type`
--

DROP TABLE IF EXISTS `cusine_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `cusine_type` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(24) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image_secure_url` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `image_public_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cusine_type`
--

LOCK TABLES `cusine_type` WRITE;
/*!40000 ALTER TABLE `cusine_type` DISABLE KEYS */;
INSERT INTO `cusine_type` VALUES (1,'Mexican','Mexican cusine is primarily a fusion of indigenous Mesoamerican cooking with European and spanish element added','https://res.cloudinary.com/di2fxuii1/image/upload/v1480478472/jl1ikl184iqyv8ero2ev.jpg','http://res.cloudinary.com/di2fxuii1/image/upload/v1480478472/jl1ikl184iqyv8ero2ev.jpg','jl1ikl184iqyv8ero2ev'),(2,'Italian','Italian cusine is characterized by its simplicity with many dishes having only four to eight ingredients with cheese and wine playing the major role','https://res.cloudinary.com/di2fxuii1/image/upload/v1480478472/jl1ikl184iqyv8ero2ev.jpg','http://res.cloudinary.com/di2fxuii1/image/upload/v1480478472/jl1ikl184iqyv8ero2ev.jpg','jl1ikl184iqyv8ero2ev'),(3,'Indian','Indian cusine reflects a 8000-year history of various groups and cultures interacting with the subcontinent leading to diversity of flavours and regional cusines found in modern-day India','https://res.cloudinary.com/di2fxuii1/image/upload/v1480478472/jl1ikl184iqyv8ero2ev.jpg','http://res.cloudinary.com/di2fxuii1/image/upload/v1480478472/jl1ikl184iqyv8ero2ev.jpg','jl1ikl184iqyv8ero2ev'),(4,'Thai','Thai cusine emphasis on lightly prepared dishes with strong aromatic components and a spicy edge','https://res.cloudinary.com/di2fxuii1/image/upload/v1480478472/jl1ikl184iqyv8ero2ev.jpg','http://res.cloudinary.com/di2fxuii1/image/upload/v1480478472/jl1ikl184iqyv8ero2ev.jpg','jl1ikl184iqyv8ero2ev'),(5,'Chinese','The history of Chinese cusine in China stretches back for thousands of years and has changed from period to period and in each region according to climate, imperial fashions, and local preferences','https://res.cloudinary.com/di2fxuii1/image/upload/v1480478472/jl1ikl184iqyv8ero2ev.jpg','http://res.cloudinary.com/di2fxuii1/image/upload/v1480478472/jl1ikl184iqyv8ero2ev.jpg','jl1ikl184iqyv8ero2ev'),(6,'Japanese','The history of Chinese cusine in China stretches back for thousands of years and has changed from period to period and in each region according to climate, imperial fashions, and local preferences','https://res.cloudinary.com/di2fxuii1/image/upload/v1480478472/jl1ikl184iqyv8ero2ev.jpg','http://res.cloudinary.com/di2fxuii1/image/upload/v1480478472/jl1ikl184iqyv8ero2ev.jpg','jl1ikl184iqyv8ero2ev');
/*!40000 ALTER TABLE `cusine_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `customer_order`
--

DROP TABLE IF EXISTS `customer_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `customer_order` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `customer_id` int(10) unsigned NOT NULL,
  `total` decimal(10,2) NOT NULL,
  `confirmation_number` varchar(48) DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  `created_at` bigint(20) unsigned DEFAULT NULL,
  `updated_at` bigint(20) unsigned DEFAULT NULL,
  `ordered_at` bigint(20) unsigned DEFAULT NULL,
  `location_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_customer_order_user` (`customer_id`),
  KEY `location_id` (`location_id`),
  CONSTRAINT `customer_order_ibfk_1` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`),
  CONSTRAINT `fk_customer_order_user` FOREIGN KEY (`customer_id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `customer_order`
--

LOCK TABLES `customer_order` WRITE;
/*!40000 ALTER TABLE `customer_order` DISABLE KEYS */;
INSERT INTO `customer_order` VALUES (1,2,5.00,'UXTA','CONFIRMED',1480841870281,1480841870291,1480843321577,2),(2,2,20.00,'ZAZ2','CONFIRMED',1480845533820,1480845533820,1480845562164,2);
/*!40000 ALTER TABLE `customer_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `distributor`
--

DROP TABLE IF EXISTS `distributor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `distributor` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `website_url` varchar(255) DEFAULT NULL,
  `phone` varchar(24) NOT NULL,
  `total_orders_delivered` int(10) unsigned DEFAULT '0',
  `total_orders_to_be_delivered` int(10) unsigned DEFAULT '0',
  `image_secure_url` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `image_public_id` varchar(255) DEFAULT NULL,
  `location_id` int(10) unsigned DEFAULT NULL,
  `status` varchar(24) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  KEY `location_id` (`location_id`),
  CONSTRAINT `distributor_ibfk_1` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `distributor`
--

LOCK TABLES `distributor` WRITE;
/*!40000 ALTER TABLE `distributor` DISABLE KEYS */;
INSERT INTO `distributor` VALUES (1,'Cargo Distributors','www.cargo.com','3128667056',0,4,'https://res.cloudinary.com/di2fxuii1/image/upload/v1480491536/e9hrnmrmphvvlafkxpea.jpg','http://res.cloudinary.com/di2fxuii1/image/upload/v1480491536/e9hrnmrmphvvlafkxpea.jpg','e9hrnmrmphvvlafkxpea',4,'VERIFIED');
/*!40000 ALTER TABLE `distributor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `location`
--

DROP TABLE IF EXISTS `location`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `location` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `addressline` varchar(255) DEFAULT NULL,
  `city` varchar(48) NOT NULL,
  `state` varchar(48) DEFAULT NULL,
  `zipcode` varchar(24) NOT NULL,
  `country` varchar(48) DEFAULT NULL,
  `latitude` decimal(10,7) DEFAULT NULL,
  `longitude` decimal(10,7) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `location`
--

LOCK TABLES `location` WRITE;
/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` VALUES (1,'2851, S. King Drive','Chicago','IL','60616','USA',41.8425894,-87.6164694),(2,'s king drive','Chicago','IL','60616',NULL,41.8403395,-87.6137011),(3,'cermak place','Chicago','IL','60617',NULL,41.7246539,-87.5494745),(4,'Roosevelt street ','Chicago','IL','60614',NULL,41.9203468,-87.6433139),(5,'2851 Roosevelt','Chicago','IL','60613',NULL,41.9577761,-87.6556468);
/*!40000 ALTER TABLE `location` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_item`
--

DROP TABLE IF EXISTS `order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `order_item` (
  `order_id` int(10) unsigned NOT NULL,
  `recipe_id` int(10) unsigned NOT NULL,
  `quantity` int(11) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  PRIMARY KEY (`order_id`,`recipe_id`),
  KEY `fk_order_item_recipe` (`recipe_id`),
  CONSTRAINT `fk_order_item_order` FOREIGN KEY (`order_id`) REFERENCES `customer_order` (`id`),
  CONSTRAINT `fk_order_item_recipe` FOREIGN KEY (`recipe_id`) REFERENCES `recipe` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_item`
--

LOCK TABLES `order_item` WRITE;
/*!40000 ALTER TABLE `order_item` DISABLE KEYS */;
INSERT INTO `order_item` VALUES (1,1,1,5.00),(2,1,4,20.00);
/*!40000 ALTER TABLE `order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recipe`
--

DROP TABLE IF EXISTS `recipe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recipe` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(24) NOT NULL,
  `category_id` int(10) unsigned NOT NULL,
  `cusine_type_id` int(10) unsigned NOT NULL,
  `description` varchar(255) NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `total_servings` int(11) DEFAULT NULL,
  `total_bookings` int(11) DEFAULT NULL,
  `created_at` bigint(24) NOT NULL,
  `updated_at` bigint(24) NOT NULL,
  `status` varchar(24) NOT NULL,
  `date_of_serving` bigint(24) NOT NULL,
  `user_id` int(10) unsigned NOT NULL,
  `image_secure_url` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `image_public_id` varchar(255) DEFAULT NULL,
  `distributor_id` int(10) unsigned DEFAULT NULL,
  `distributor_status` varchar(24) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `category_id` (`category_id`),
  KEY `cusine_type_id` (`cusine_type_id`),
  KEY `distributor_id` (`distributor_id`),
  CONSTRAINT `recipe_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `recipe_category` (`id`),
  CONSTRAINT `recipe_ibfk_2` FOREIGN KEY (`cusine_type_id`) REFERENCES `cusine_type` (`id`),
  CONSTRAINT `recipe_ibfk_3` FOREIGN KEY (`distributor_id`) REFERENCES `distributor` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recipe`
--

LOCK TABLES `recipe` WRITE;
/*!40000 ALTER TABLE `recipe` DISABLE KEYS */;
INSERT INTO `recipe` VALUES (1,'sushi',2,6,'hot and spicy',5.00,3,5,1480839688863,1480840981618,'COMPLETED',1480831200000,2,'https://res.cloudinary.com/di2fxuii1/image/upload/v1480839688/b3tfvwx7f0ic1cl3vubk.jpg','http://res.cloudinary.com/di2fxuii1/image/upload/v1480839688/b3tfvwx7f0ic1cl3vubk.jpg','b3tfvwx7f0ic1cl3vubk',NULL,'DISPATCHED'),(2,'chicken curry',2,3,'The best indian curry to taste',4.00,2,0,1480839744150,1480840725169,'PUBLISHED',1480831200000,2,'https://res.cloudinary.com/di2fxuii1/image/upload/v1480839743/fiby0kgo04qcbky1aztl.jpg','http://res.cloudinary.com/di2fxuii1/image/upload/v1480839743/fiby0kgo04qcbky1aztl.jpg','fiby0kgo04qcbky1aztl',1,'ASSIGNED'),(3,'Buritto',1,1,'tangy and saucy',8.00,4,0,1480839787152,1480845879965,'PUBLISHED',1480831200000,2,'https://res.cloudinary.com/di2fxuii1/image/upload/v1480839786/gqfswojuwjmmxwfgtamx.png','http://res.cloudinary.com/di2fxuii1/image/upload/v1480839786/gqfswojuwjmmxwfgtamx.png','gqfswojuwjmmxwfgtamx',1,'ASSIGNED'),(4,'Pizza',1,2,'Cheesy with salsa toppings',7.00,5,NULL,1480839886165,1480839886165,'PENDING',1480831200000,2,'https://res.cloudinary.com/di2fxuii1/image/upload/v1480839885/txpwznf8kwiypziyekdl.jpg','http://res.cloudinary.com/di2fxuii1/image/upload/v1480839885/txpwznf8kwiypziyekdl.jpg','txpwznf8kwiypziyekdl',NULL,'UNASSIGNED'),(5,'Noodles',1,5,'soupy and spicy',8.00,4,0,1480839962035,1480845895563,'PUBLISHED',1480831200000,2,'https://res.cloudinary.com/di2fxuii1/image/upload/v1480839961/jlp4gwfhi91lbv5bqlze.jpg','http://res.cloudinary.com/di2fxuii1/image/upload/v1480839961/jlp4gwfhi91lbv5bqlze.jpg','jlp4gwfhi91lbv5bqlze',1,'ASSIGNED'),(6,'thai rolls',1,4,'Thai special roles',6.00,10,0,1480845284998,1480845438587,'COMPLETED',1481695200000,5,'https://res.cloudinary.com/di2fxuii1/image/upload/v1480845284/dau8ctndjiicjsxb1opv.jpg','http://res.cloudinary.com/di2fxuii1/image/upload/v1480845284/dau8ctndjiicjsxb1opv.jpg','dau8ctndjiicjsxb1opv',NULL,'DISPATCHED');
/*!40000 ALTER TABLE `recipe` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `recipe_category`
--

DROP TABLE IF EXISTS `recipe_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `recipe_category` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(24) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `image_secure_url` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `image_public_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `recipe_category`
--

LOCK TABLES `recipe_category` WRITE;
/*!40000 ALTER TABLE `recipe_category` DISABLE KEYS */;
INSERT INTO `recipe_category` VALUES (1,'VEG','enjoy vegetarian food','https://res.cloudinary.com/di2fxuii1/image/upload/v1480478472/jl1ikl184iqyv8ero2ev.jpg','http://res.cloudinary.com/di2fxuii1/image/upload/v1480478472/jl1ikl184iqyv8ero2ev.jpg','jl1ikl184iqyv8ero2ev'),(2,'NON VEG','enjoy vegetarian food','https://res.cloudinary.com/di2fxuii1/image/upload/v1480478472/jl1ikl184iqyv8ero2ev.jpg','http://res.cloudinary.com/di2fxuii1/image/upload/v1480478472/jl1ikl184iqyv8ero2ev.jpg','jl1ikl184iqyv8ero2ev');
/*!40000 ALTER TABLE `recipe_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `first_name` varchar(24) NOT NULL,
  `last_name` varchar(24) NOT NULL,
  `gender` varchar(24) NOT NULL,
  `email` varchar(48) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(24) NOT NULL,
  `type` varchar(24) NOT NULL,
  `role` varchar(24) DEFAULT NULL,
  `status` varchar(24) NOT NULL,
  `created_at` bigint(24) NOT NULL,
  `updated_at` bigint(24) NOT NULL,
  `location_id` int(10) unsigned DEFAULT NULL,
  `image_secure_url` varchar(255) DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  `image_public_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  KEY `location_id` (`location_id`),
  CONSTRAINT `user_ibfk_1` FOREIGN KEY (`location_id`) REFERENCES `location` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (1,'admin','admin','MALE','admin@admin.com','$2a$10$lsOYdRBoR1Ebc8hyD.HxBetaZhWJvyoDBTK.Cm6qGXst8pZKgHBS6','+3128007014','ADMIN',NULL,'ACTIVE',1480839352906,1480839352906,1,'wwww.abcd.com','wwws.abcd.com','lsndcjwe5'),(2,'Ned','Stark','MALE','ned@gmail.com','$2a$10$IPa2NnbzxaOT39LJvHHPROGHWYeMZQ7xr8etzaZaDHjq1tZpWa4rO','3128667012','CUSTOMER',NULL,'ACTIVE',1480839428799,1480839428799,2,'https://res.cloudinary.com/di2fxuii1/image/upload/v1480480935/irkvau0fw9d0vvrp15hg.png',NULL,'irkvau0fw9d0vvrp15hg'),(3,'Christina','John','FEMALE','christina@gmail.com','$2a$10$9./SMwVCDOFk.Y3vb6QbWO0NLd4Tr2qAUI4JLGYl6XqByRDF7YAh2','3128667036','CUSTOMER',NULL,'ACTIVE',1480839507935,1480839507935,3,'http://res.cloudinary.com/di2fxuii1/image/upload/v1480489375/iq6u1h6el0v40yfzuram.jpg','https://res.cloudinary.com/di2fxuii1/image/upload/v1480489375/iq6u1h6el0v40yfzuram.jpg','iq6u1h6el0v40yfzuram'),(4,'Robert','Downey','MALE','robert@gmail.com','$2a$10$htwEqJ9PH0MvwPHBfExVK.0JBxGJkNhZEEK1mFeZM58yRiWgTyd0S','3128667056','DISTRIBUTOR','PRIMARY_DISTRIBUTOR','ACTIVE',1480840528126,1480840528126,4,'https://res.cloudinary.com/di2fxuii1/image/upload/v1480480935/irkvau0fw9d0vvrp15hg.png',NULL,'irkvau0fw9d0vvrp15hg'),(5,'Robert','Downey Jr','MALE','robertjr@gmail.com','$2a$10$KwfPMoTtY9n6gV3we4ATt.1x1GALVr2AWKETph/BgSOoI9gIOWar6','3128667055','CUSTOMER',NULL,'ACTIVE',1480845061474,1480845061474,5,'https://res.cloudinary.com/di2fxuii1/image/upload/v1480480935/irkvau0fw9d0vvrp15hg.png',NULL,'irkvau0fw9d0vvrp15hg');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_ditributor`
--

DROP TABLE IF EXISTS `user_ditributor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_ditributor` (
  `user_id` int(10) unsigned NOT NULL,
  `distributor_id` int(10) unsigned NOT NULL,
  PRIMARY KEY (`user_id`,`distributor_id`),
  KEY `distributor_id` (`distributor_id`),
  CONSTRAINT `user_ditributor_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
  CONSTRAINT `user_ditributor_ibfk_2` FOREIGN KEY (`distributor_id`) REFERENCES `distributor` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_ditributor`
--

LOCK TABLES `user_ditributor` WRITE;
/*!40000 ALTER TABLE `user_ditributor` DISABLE KEYS */;
INSERT INTO `user_ditributor` VALUES (4,1);
/*!40000 ALTER TABLE `user_ditributor` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-12-04  4:08:50
