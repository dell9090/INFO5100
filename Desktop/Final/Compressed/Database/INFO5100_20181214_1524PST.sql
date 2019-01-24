#
# SQL Export
# Created by Querious (201026)
# Created: December 14, 2018 at 3:24:55 PM PST
# Encoding: Unicode (UTF-8)
#


SET @PREVIOUS_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS;
SET FOREIGN_KEY_CHECKS = 0;


DROP TABLE IF EXISTS `Users`;
DROP TABLE IF EXISTS `Notifications`;
DROP TABLE IF EXISTS `Homeworks`;
DROP TABLE IF EXISTS `Coursewares`;
DROP TABLE IF EXISTS `Courses`;
DROP TABLE IF EXISTS `Comments`;
DROP TABLE IF EXISTS `Attachments`;


CREATE TABLE `Attachments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `original_name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `path` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `Comments` (
  `id` int(20) NOT NULL AUTO_INCREMENT,
  `userid` int(20) DEFAULT '0',
  `agreecount` int(128) DEFAULT '0',
  `ifagree` int(1) DEFAULT '0',
  `courseid` int(20) DEFAULT '0',
  `post_type` int(11) DEFAULT '0',
  `post_id` int(10) DEFAULT '0',
  `page` int(10) DEFAULT '0',
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `top` int(1) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `Courses` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `studentid` int(11) DEFAULT '0',
  `courseid` int(11) DEFAULT '0',
  `teacherid` int(11) DEFAULT '0',
  `title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6206 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `Coursewares` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `courseid` int(11) DEFAULT '0',
  `dir` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `info` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `Homeworks` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `description` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `attachment_id` int(11) DEFAULT '0',
  `courseid` int(11) DEFAULT '0',
  `open_at` timestamp NULL DEFAULT NULL,
  `close_at` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `Notifications` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `title` text CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci,
  `courseid` int(11) DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE `Users` (
  `password` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '0',
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2080866523 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;




SET FOREIGN_KEY_CHECKS = @PREVIOUS_FOREIGN_KEY_CHECKS;


SET @PREVIOUS_FOREIGN_KEY_CHECKS = @@FOREIGN_KEY_CHECKS;
SET FOREIGN_KEY_CHECKS = 0;


LOCK TABLES `Attachments` WRITE;
ALTER TABLE `Attachments` DISABLE KEYS;
ALTER TABLE `Attachments` ENABLE KEYS;
UNLOCK TABLES;


LOCK TABLES `Comments` WRITE;
ALTER TABLE `Comments` DISABLE KEYS;
INSERT INTO `Comments` (`id`, `userid`, `agreecount`, `ifagree`, `courseid`, `post_type`, `post_id`, `page`, `content`, `top`) VALUES 
	(1,278,2,1,5100,0,1,1,'this is a test',0),
	(2,277,1,1,5100,0,1,1,'test',0),
	(3,277,1,0,5100,0,1,2,'this is a test',0),
	(4,277,0,0,5100,0,1,2,'test',0),
	(6,277,1,0,5100,0,1,2,'a',0),
	(7,277,0,0,5100,0,1,1,'b',0),
	(8,277,0,0,5100,0,1,2,'hahaha',0);
ALTER TABLE `Comments` ENABLE KEYS;
UNLOCK TABLES;


LOCK TABLES `Courses` WRITE;
ALTER TABLE `Courses` DISABLE KEYS;
INSERT INTO `Courses` (`id`, `studentid`, `courseid`, `teacherid`, `title`) VALUES 
	(1,277,5100,1,'Java'),
	(2,277,6000,2,'ENCP'),
	(3,277,6205,3,'Data Structure'),
	(4,279,5100,1,'Java'),
	(5,279,6205,3,'Data Structure');
ALTER TABLE `Courses` ENABLE KEYS;
UNLOCK TABLES;


LOCK TABLES `Coursewares` WRITE;
ALTER TABLE `Coursewares` DISABLE KEYS;
INSERT INTO `Coursewares` (`id`, `courseid`, `dir`, `name`, `info`) VALUES 
	(1,5100,'/Users/elias/Desktop/slide-week1.pdf','slide1','Course1'),
	(2,6000,NULL,'slide2','Course2'),
	(3,5100,'/Users/elias/Desktop/slide-week2.pdf','slide2','Course2');
ALTER TABLE `Coursewares` ENABLE KEYS;
UNLOCK TABLES;


LOCK TABLES `Homeworks` WRITE;
ALTER TABLE `Homeworks` DISABLE KEYS;
INSERT INTO `Homeworks` (`id`, `title`, `description`, `attachment_id`, `courseid`, `open_at`, `close_at`) VALUES 
	(1,'assignment1','write code',0,5100,'2018-09-13 00:00:00','2018-09-19 00:00:00'),
	(2,'assignment2','write again',1,5100,'2018-10-20 00:00:00','2018-10-27 00:00:00'),
	(3,'assignment1','write paper',2,6000,'2018-09-20 00:00:00','2018-10-01 00:00:00'),
	(4,'assignment1','code',3,6205,'2018-11-01 00:00:00','2018-11-11 00:00:00');
ALTER TABLE `Homeworks` ENABLE KEYS;
UNLOCK TABLES;


LOCK TABLES `Notifications` WRITE;
ALTER TABLE `Notifications` DISABLE KEYS;
INSERT INTO `Notifications` (`id`, `content`, `title`, `courseid`) VALUES 
	(1,'Remember submit your final project','Final',5100),
	(2,'Learn how to program','Assignment1',5100);
ALTER TABLE `Notifications` ENABLE KEYS;
UNLOCK TABLES;


LOCK TABLES `Users` WRITE;
ALTER TABLE `Users` DISABLE KEYS;
INSERT INTO `Users` (`password`, `id`, `name`) VALUES 
	('^[[',277,'Xin'),
	('^[T',278,'Roc'),
	('^[U',279,'Zhu');
ALTER TABLE `Users` ENABLE KEYS;
UNLOCK TABLES;




SET FOREIGN_KEY_CHECKS = @PREVIOUS_FOREIGN_KEY_CHECKS;


