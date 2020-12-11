-- MySQL dump 10.13  Distrib 5.5.62, for Win64 (AMD64)
--
-- Host: localhost    Database: relief
-- ------------------------------------------------------
-- Server version	5.5.5-10.4.13-MariaDB

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
-- Table structure for table `donations`
--

DROP TABLE IF EXISTS `donations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `donations` (
  `donation_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(250) NOT NULL,
  `quantity` int(11) NOT NULL DEFAULT 0,
  `create_time_stamp` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`donation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donations`
--

LOCK TABLES `donations` WRITE;
/*!40000 ALTER TABLE `donations` DISABLE KEYS */;
INSERT INTO `donations` VALUES (1,'Food',1000,'2020-12-04 15:20:53'),(2,'Dress',1000,'2020-11-10 22:56:06'),(3,'donation 2',0,'2020-11-24 13:58:29'),(4,'Donation 3',0,'2020-11-24 14:13:21');
/*!40000 ALTER TABLE `donations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `donner_donation_view`
--

DROP TABLE IF EXISTS `donner_donation_view`;
/*!50001 DROP VIEW IF EXISTS `donner_donation_view`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `donner_donation_view` (
  `donners_donations_id` tinyint NOT NULL,
  `donation_date` tinyint NOT NULL,
  `donation_id` tinyint NOT NULL,
  `donner_id` tinyint NOT NULL,
  `create_time_stamp` tinyint NOT NULL,
  `quantity` tinyint NOT NULL,
  `donner_full_name` tinyint NOT NULL,
  `donation_name` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `donners`
--

DROP TABLE IF EXISTS `donners`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `donners` (
  `donner_id` int(11) NOT NULL AUTO_INCREMENT,
  `full_name` varchar(250) NOT NULL,
  `address` varchar(250) NOT NULL,
  `contact_number` varchar(50) NOT NULL,
  `create_time_stamp` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`donner_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donners`
--

LOCK TABLES `donners` WRITE;
/*!40000 ALTER TABLE `donners` DISABLE KEYS */;
INSERT INTO `donners` VALUES (1,'Donner\'s 2','Rosario Batangas','722-2252','2020-11-03 20:34:18'),(2,'donner 2s','address','09xxxxxxxxx','2020-11-05 16:55:14'),(3,'asdfere','ewrwe','asdfsadf','2020-11-09 14:30:29'),(4,'darren','comia','atienza','2020-11-09 14:40:18'),(5,'ererd','werer','werwer','2020-11-09 14:41:26'),(6,'xxdfdfs','xdfdfsfs','gfgfgf','2020-11-09 14:41:45'),(7,'asdf','asdf','asdf','2020-11-10 21:02:39'),(8,'asdf','asdf','asdf','2020-11-10 21:06:44');
/*!40000 ALTER TABLE `donners` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `donners_donations`
--

DROP TABLE IF EXISTS `donners_donations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `donners_donations` (
  `donners_donations_id` int(11) NOT NULL AUTO_INCREMENT,
  `donation_id` int(11) NOT NULL,
  `donner_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL DEFAULT 0,
  `donation_date` datetime NOT NULL DEFAULT current_timestamp(),
  `create_time_stamp` datetime NOT NULL DEFAULT current_timestamp(),
  `quantity_uploaded` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`donners_donations_id`),
  KEY `fk_donations_donation_id` (`donation_id`),
  KEY `fk_donners_donner_id` (`donner_id`),
  CONSTRAINT `fk_donations_donation_id` FOREIGN KEY (`donation_id`) REFERENCES `donations` (`donation_id`),
  CONSTRAINT `fk_donners_donner_id` FOREIGN KEY (`donner_id`) REFERENCES `donners` (`donner_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `donners_donations`
--

LOCK TABLES `donners_donations` WRITE;
/*!40000 ALTER TABLE `donners_donations` DISABLE KEYS */;
INSERT INTO `donners_donations` VALUES (1,2,1,5,'2020-11-03 21:31:47','2020-11-03 21:02:54',1),(5,2,1,5,'2020-11-25 12:00:00','2020-11-25 14:53:26',1),(6,4,3,10,'2020-11-27 12:00:00','2020-11-25 14:55:17',0);
/*!40000 ALTER TABLE `donners_donations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `donners_donations_view`
--

DROP TABLE IF EXISTS `donners_donations_view`;
/*!50001 DROP VIEW IF EXISTS `donners_donations_view`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `donners_donations_view` (
  `donners_donations_id` tinyint NOT NULL,
  `donation_date` tinyint NOT NULL,
  `donation_id` tinyint NOT NULL,
  `donner_id` tinyint NOT NULL,
  `create_time_stamp` tinyint NOT NULL,
  `quantity` tinyint NOT NULL,
  `donner_full_name` tinyint NOT NULL,
  `donation_name` tinyint NOT NULL,
  `quantity_uploaded` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `employee_list_view`
--

DROP TABLE IF EXISTS `employee_list_view`;
/*!50001 DROP VIEW IF EXISTS `employee_list_view`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `employee_list_view` (
  `employee_id` tinyint NOT NULL,
  `address` tinyint NOT NULL,
  `contact_number` tinyint NOT NULL,
  `full_name` tinyint NOT NULL,
  `position` tinyint NOT NULL,
  `active` tinyint NOT NULL,
  `user_id` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `employees`
--

DROP TABLE IF EXISTS `employees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `employees` (
  `employee_id` int(11) NOT NULL AUTO_INCREMENT,
  `full_name` varchar(250) NOT NULL,
  `address` varchar(250) NOT NULL,
  `contact_number` varchar(250) NOT NULL,
  `create_time_stamp` datetime NOT NULL DEFAULT current_timestamp(),
  `position` varchar(250) NOT NULL DEFAULT '',
  `user_id` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`employee_id`),
  KEY `employees_users_fk` (`user_id`),
  CONSTRAINT `employees_users_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employees`
--

LOCK TABLES `employees` WRITE;
/*!40000 ALTER TABLE `employees` DISABLE KEYS */;
INSERT INTO `employees` VALUES (1,'admin','','','2020-12-04 14:17:24','admin',1);
/*!40000 ALTER TABLE `employees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `relief_request_donation_view`
--

DROP TABLE IF EXISTS `relief_request_donation_view`;
/*!50001 DROP VIEW IF EXISTS `relief_request_donation_view`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `relief_request_donation_view` (
  `relief_request_donation_id` tinyint NOT NULL,
  `create_time_stamp` tinyint NOT NULL,
  `donation_name` tinyint NOT NULL,
  `donation_id` tinyint NOT NULL,
  `quantity` tinyint NOT NULL,
  `relief_request_id` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `relief_request_donations`
--

DROP TABLE IF EXISTS `relief_request_donations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `relief_request_donations` (
  `relief_request_donation_id` int(11) NOT NULL AUTO_INCREMENT,
  `relief_request_id` int(11) NOT NULL,
  `donation_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL DEFAULT 0,
  `create_time_stamp` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`relief_request_donation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `relief_request_donations`
--

LOCK TABLES `relief_request_donations` WRITE;
/*!40000 ALTER TABLE `relief_request_donations` DISABLE KEYS */;
INSERT INTO `relief_request_donations` VALUES (2,1,2,1,'2020-12-04 15:17:28');
/*!40000 ALTER TABLE `relief_request_donations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `relief_requests`
--

DROP TABLE IF EXISTS `relief_requests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `relief_requests` (
  `relief_request_id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) NOT NULL,
  `relief_task_id` int(11) NOT NULL,
  `released` tinyint(1) NOT NULL,
  `date_release` datetime NOT NULL DEFAULT current_timestamp(),
  `create_time_stamp` datetime NOT NULL DEFAULT current_timestamp(),
  `donation_requests` varchar(250) NOT NULL DEFAULT '',
  PRIMARY KEY (`relief_request_id`),
  KEY `fk_students_student_id` (`student_id`),
  KEY `fk_relief_tasks_relief_task_id` (`relief_task_id`),
  CONSTRAINT `fk_relief_tasks_relief_task_id` FOREIGN KEY (`relief_task_id`) REFERENCES `relief_tasks` (`relief_task_id`),
  CONSTRAINT `relief_requests_students_fk` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `relief_requests`
--

LOCK TABLES `relief_requests` WRITE;
/*!40000 ALTER TABLE `relief_requests` DISABLE KEYS */;
INSERT INTO `relief_requests` VALUES (1,1,1,0,'2020-12-04 15:10:07','2020-12-04 15:10:07',''),(2,1,1,0,'2020-12-07 16:54:34','2020-12-07 16:54:34',''),(3,1,1,0,'2020-12-07 17:00:31','2020-12-07 17:00:31','Food,Dress,Temporary Shelter');
/*!40000 ALTER TABLE `relief_requests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `relief_requests_view`
--

DROP TABLE IF EXISTS `relief_requests_view`;
/*!50001 DROP VIEW IF EXISTS `relief_requests_view`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `relief_requests_view` (
  `relief_request_id` tinyint NOT NULL,
  `student_id` tinyint NOT NULL,
  `student_full_name` tinyint NOT NULL,
  `student_address` tinyint NOT NULL,
  `student_contact_number` tinyint NOT NULL,
  `student_campus` tinyint NOT NULL,
  `student_course` tinyint NOT NULL,
  `relief_task_id` tinyint NOT NULL,
  `request_task_title` tinyint NOT NULL,
  `released` tinyint NOT NULL,
  `date_release` tinyint NOT NULL,
  `create_time_stamp` tinyint NOT NULL,
  `donation_requests` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `relief_tasks`
--

DROP TABLE IF EXISTS `relief_tasks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `relief_tasks` (
  `relief_task_id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(250) NOT NULL,
  `title` varchar(250) NOT NULL,
  `affected_areas` varchar(250) NOT NULL,
  `active` tinyint(1) NOT NULL,
  `create_time_stamp` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`relief_task_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `relief_tasks`
--

LOCK TABLES `relief_tasks` WRITE;
/*!40000 ALTER TABLE `relief_tasks` DISABLE KEYS */;
INSERT INTO `relief_tasks` VALUES (1,'1','rt','bats',1,'2020-12-04 14:40:28');
/*!40000 ALTER TABLE `relief_tasks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `student_reliefs`
--

DROP TABLE IF EXISTS `student_reliefs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `student_reliefs` (
  `student_relief_id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) NOT NULL,
  `donation_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL DEFAULT 0,
  `is_release` tinyint(1) NOT NULL,
  `date_release` datetime NOT NULL DEFAULT current_timestamp(),
  `request_date` datetime NOT NULL DEFAULT current_timestamp(),
  `code` varchar(50) NOT NULL DEFAULT '',
  `create_time_stamp` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`student_relief_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `student_reliefs`
--

LOCK TABLES `student_reliefs` WRITE;
/*!40000 ALTER TABLE `student_reliefs` DISABLE KEYS */;
/*!40000 ALTER TABLE `student_reliefs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `students`
--

DROP TABLE IF EXISTS `students`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `students` (
  `student_id` int(11) NOT NULL AUTO_INCREMENT,
  `sr_code` varchar(50) NOT NULL,
  `full_name` varchar(250) NOT NULL,
  `address` varchar(250) NOT NULL,
  `course` varchar(250) NOT NULL,
  `contact_number` varchar(250) NOT NULL,
  `create_time_stamp` datetime NOT NULL DEFAULT current_timestamp(),
  `campus` varchar(250) NOT NULL DEFAULT '',
  `user_id` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`student_id`),
  KEY `students_users_fk` (`user_id`),
  CONSTRAINT `students_users_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `students`
--

LOCK TABLES `students` WRITE;
/*!40000 ALTER TABLE `students` DISABLE KEYS */;
INSERT INTO `students` VALUES (1,'1','Juan Tamad','Batangas','BSIT','09501743177','2020-12-04 15:07:01','Main',2),(5,'22-22222','Juan tamad','Address of Juan','BSIT','123456','2020-12-09 15:16:05','Main',17);
/*!40000 ALTER TABLE `students` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `students_view`
--

DROP TABLE IF EXISTS `students_view`;
/*!50001 DROP VIEW IF EXISTS `students_view`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `students_view` (
  `student_id` tinyint NOT NULL,
  `sr_code` tinyint NOT NULL,
  `full_name` tinyint NOT NULL,
  `address` tinyint NOT NULL,
  `course` tinyint NOT NULL,
  `contact_number` tinyint NOT NULL,
  `campus` tinyint NOT NULL,
  `active` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(250) NOT NULL,
  `password` varchar(250) NOT NULL,
  `user_type` varchar(250) NOT NULL DEFAULT 'student',
  `active` tinyint(1) NOT NULL DEFAULT 0,
  `create_time_stamp` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'admin','$2y$10$6W8O2Lxsv0U59/IqhdHkwurTgqNZw1ODbhIYweQSVWEmg0KgJm6sW','admin',1,'2020-12-04 13:39:27'),(2,'1','$2y$10$292lUQ.wuAFaeAHUv28.GugtA9wBKFyHJrzciA/dOPMvTFk.JsJKO','student',1,'2020-12-04 15:06:00'),(3,'v','$2y$10$mn4Ba.euyb6qdlnSaZz1MeQlwT/tEeCae9fPdZxsLLwODOuiV/9vG','volunteer',1,'2020-12-10 16:55:05'),(12,'a','$2y$10$EPhBSNorvl23G/CKhJrG8uDfZ7hZ3Yq9qiEP6gudf97tEgrFffqbK','student',1,'2020-12-01 12:10:09'),(13,'volunteer1','$2y$10$saTKJzJZiplBAUQNkYzC5O7zququ5Q9hkZP5hMdGpOhB7s3HvcUjW','volunteer',1,'2020-12-01 15:04:33'),(14,'volunteer2','$2y$10$oTFD95i.8vy39mPS06lx7OO5ZEluGHmdPAMW.TQ4EPeFD/tGgwfU.','volunteer',1,'2020-12-02 15:56:07'),(15,'x','$2y$10$YqoEcttwSgDhFdRGSGTlvu1Ay42jTJWgHA74GV1TwY7IFRt8icO4K','admin',1,'2020-12-03 16:34:59'),(16,'v','$2y$10$JAnqXwASntuSVYQUMJFCAuDCoEqcw/JFE8hlvgTS4xU/qwUfNKuHe','volunteer',1,'2020-12-04 14:26:37'),(17,'22-22222','$2y$10$QvTtM89BabsftVySNIZEE.RK4InwOpIGwJNi94b1d7ggVhtEGSIYS','student',0,'2020-12-09 15:16:05');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary table structure for view `view_dashboard`
--

DROP TABLE IF EXISTS `view_dashboard`;
/*!50001 DROP VIEW IF EXISTS `view_dashboard`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `view_dashboard` (
  `student_count` tinyint NOT NULL,
  `donner_count` tinyint NOT NULL,
  `employee_count` tinyint NOT NULL,
  `relief_request_count` tinyint NOT NULL,
  `relief_task_count` tinyint NOT NULL,
  `user_count` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `view_student_active_relief_request_count`
--

DROP TABLE IF EXISTS `view_student_active_relief_request_count`;
/*!50001 DROP VIEW IF EXISTS `view_student_active_relief_request_count`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `view_student_active_relief_request_count` (
  `relief_request_count` tinyint NOT NULL,
  `student_id` tinyint NOT NULL,
  `released` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Temporary table structure for view `volunteer_list_view`
--

DROP TABLE IF EXISTS `volunteer_list_view`;
/*!50001 DROP VIEW IF EXISTS `volunteer_list_view`*/;
SET @saved_cs_client     = @@character_set_client;
SET character_set_client = utf8;
/*!50001 CREATE TABLE `volunteer_list_view` (
  `volunteer_id` tinyint NOT NULL,
  `address` tinyint NOT NULL,
  `code` tinyint NOT NULL,
  `contact_number` tinyint NOT NULL,
  `full_name` tinyint NOT NULL,
  `active` tinyint NOT NULL,
  `user_id` tinyint NOT NULL
) ENGINE=MyISAM */;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `volunteers`
--

DROP TABLE IF EXISTS `volunteers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `volunteers` (
  `volunteer_id` int(11) NOT NULL AUTO_INCREMENT,
  `full_name` varchar(250) NOT NULL,
  `address` varchar(250) NOT NULL,
  `contact_number` varchar(50) NOT NULL,
  `create_time_stamp` datetime NOT NULL DEFAULT current_timestamp(),
  `code` varchar(50) NOT NULL DEFAULT '',
  `user_id` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`volunteer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `volunteers`
--

LOCK TABLES `volunteers` WRITE;
/*!40000 ALTER TABLE `volunteers` DISABLE KEYS */;
INSERT INTO `volunteers` VALUES (1,'v','v','23432','2020-12-10 16:52:39','v',3),(11,'v','v','123','2020-12-04 14:26:38','v',16);
/*!40000 ALTER TABLE `volunteers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping routines for database 'relief'
--

--
-- Final view structure for view `donner_donation_view`
--

/*!50001 DROP TABLE IF EXISTS `donner_donation_view`*/;
/*!50001 DROP VIEW IF EXISTS `donner_donation_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `donner_donation_view` AS select `dd`.`donners_donations_id` AS `donners_donations_id`,`dd`.`donation_date` AS `donation_date`,`dd`.`donation_id` AS `donation_id`,`dd`.`donner_id` AS `donner_id`,`dd`.`create_time_stamp` AS `create_time_stamp`,`dd`.`quantity` AS `quantity`,`dnr`.`full_name` AS `donner_full_name`,`dnt`.`name` AS `donation_name` from ((`donners_donations` `dd` join `donations` `dnt` on(`dd`.`donation_id` = `dnt`.`donation_id`)) join `donners` `dnr` on(`dd`.`donner_id` = `dnr`.`donner_id`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `donners_donations_view`
--

/*!50001 DROP TABLE IF EXISTS `donners_donations_view`*/;
/*!50001 DROP VIEW IF EXISTS `donners_donations_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `donners_donations_view` AS select `dd`.`donners_donations_id` AS `donners_donations_id`,`dd`.`donation_date` AS `donation_date`,`dd`.`donation_id` AS `donation_id`,`dd`.`donner_id` AS `donner_id`,`dd`.`create_time_stamp` AS `create_time_stamp`,`dd`.`quantity` AS `quantity`,`dnr`.`full_name` AS `donner_full_name`,`dnt`.`name` AS `donation_name`,`dd`.`quantity_uploaded` AS `quantity_uploaded` from ((`donners_donations` `dd` join `donations` `dnt` on(`dd`.`donation_id` = `dnt`.`donation_id`)) join `donners` `dnr` on(`dd`.`donner_id` = `dnr`.`donner_id`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `employee_list_view`
--

/*!50001 DROP TABLE IF EXISTS `employee_list_view`*/;
/*!50001 DROP VIEW IF EXISTS `employee_list_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `employee_list_view` AS select `e`.`employee_id` AS `employee_id`,`e`.`address` AS `address`,`e`.`contact_number` AS `contact_number`,`e`.`full_name` AS `full_name`,`e`.`position` AS `position`,`u`.`active` AS `active`,`u`.`user_id` AS `user_id` from (`employees` `e` join `users` `u` on(`e`.`user_id` = `u`.`user_id`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `relief_request_donation_view`
--

/*!50001 DROP TABLE IF EXISTS `relief_request_donation_view`*/;
/*!50001 DROP VIEW IF EXISTS `relief_request_donation_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `relief_request_donation_view` AS select `rrd`.`relief_request_donation_id` AS `relief_request_donation_id`,`rrd`.`create_time_stamp` AS `create_time_stamp`,`d`.`name` AS `donation_name`,`rrd`.`donation_id` AS `donation_id`,`rrd`.`quantity` AS `quantity`,`rrd`.`relief_request_id` AS `relief_request_id` from (`relief_request_donations` `rrd` join `donations` `d` on(`d`.`donation_id` = `rrd`.`donation_id`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `relief_requests_view`
--

/*!50001 DROP TABLE IF EXISTS `relief_requests_view`*/;
/*!50001 DROP VIEW IF EXISTS `relief_requests_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `relief_requests_view` AS select `rr`.`relief_request_id` AS `relief_request_id`,`s`.`student_id` AS `student_id`,`s`.`full_name` AS `student_full_name`,`s`.`address` AS `student_address`,`s`.`contact_number` AS `student_contact_number`,`s`.`campus` AS `student_campus`,`s`.`course` AS `student_course`,`rr`.`relief_task_id` AS `relief_task_id`,`rt`.`title` AS `request_task_title`,`rr`.`released` AS `released`,`rr`.`date_release` AS `date_release`,`rr`.`create_time_stamp` AS `create_time_stamp`,`rr`.`donation_requests` AS `donation_requests` from ((`relief_requests` `rr` join `students` `s` on(`rr`.`student_id` = `s`.`student_id`)) join `relief_tasks` `rt` on(`rt`.`relief_task_id` = `rr`.`relief_task_id`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `students_view`
--

/*!50001 DROP TABLE IF EXISTS `students_view`*/;
/*!50001 DROP VIEW IF EXISTS `students_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `students_view` AS select `s`.`student_id` AS `student_id`,`s`.`sr_code` AS `sr_code`,`s`.`full_name` AS `full_name`,`s`.`address` AS `address`,`s`.`course` AS `course`,`s`.`contact_number` AS `contact_number`,`s`.`campus` AS `campus`,`u`.`active` AS `active` from (`students` `s` join `users` `u` on(`s`.`user_id` = `u`.`user_id`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_dashboard`
--

/*!50001 DROP TABLE IF EXISTS `view_dashboard`*/;
/*!50001 DROP VIEW IF EXISTS `view_dashboard`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `view_dashboard` AS select (select count(`students`.`student_id`) from `students`) AS `student_count`,(select count(`donners`.`donner_id`) from `donners`) AS `donner_count`,(select count(`employees`.`employee_id`) from `employees`) AS `employee_count`,(select count(`relief_requests`.`relief_request_id`) from `relief_requests`) AS `relief_request_count`,(select count(`relief_tasks`.`relief_task_id`) from `relief_tasks`) AS `relief_task_count`,(select count(`users`.`user_id`) from `users`) AS `user_count` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `view_student_active_relief_request_count`
--

/*!50001 DROP TABLE IF EXISTS `view_student_active_relief_request_count`*/;
/*!50001 DROP VIEW IF EXISTS `view_student_active_relief_request_count`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `view_student_active_relief_request_count` AS select count(`relief_requests`.`relief_request_id`) AS `relief_request_count`,`relief_requests`.`student_id` AS `student_id`,`relief_requests`.`released` AS `released` from `relief_requests` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `volunteer_list_view`
--

/*!50001 DROP TABLE IF EXISTS `volunteer_list_view`*/;
/*!50001 DROP VIEW IF EXISTS `volunteer_list_view`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_general_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `volunteer_list_view` AS select `v`.`volunteer_id` AS `volunteer_id`,`v`.`address` AS `address`,`v`.`code` AS `code`,`v`.`contact_number` AS `contact_number`,`v`.`full_name` AS `full_name`,`u`.`active` AS `active`,`u`.`user_id` AS `user_id` from (`volunteers` `v` join `users` `u` on(`v`.`user_id` = `u`.`user_id`)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-12-11  9:43:39
