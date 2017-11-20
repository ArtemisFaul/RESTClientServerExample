-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: testdb
-- ------------------------------------------------------
-- Server version	5.7.17-log

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
-- Table structure for table `billing`
--

DROP TABLE IF EXISTS `billing`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `billing` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `OperationFromID` int(10) NOT NULL,
  `UserID` int(10) NOT NULL,
  `bankAccountFromID` int(10) NOT NULL,
  `bankAccountToID` int(10) NOT NULL,
  `Description` varchar(255) DEFAULT NULL,
  `OperationToID` int(10) NOT NULL,
  `Summ` int(11) DEFAULT '0',
  PRIMARY KEY (`ID`),
  UNIQUE KEY `ID` (`ID`),
  KEY `FKBilling260693` (`bankAccountToID`),
  KEY `FKBilling322584` (`bankAccountFromID`),
  KEY `FKBilling81539` (`OperationFromID`),
  KEY `FKBilling311823` (`UserID`),
  KEY `FKBilling311824` (`OperationToID`),
  CONSTRAINT `FKBilling260693` FOREIGN KEY (`bankAccountToID`) REFERENCES `bankaccount` (`ID`),
  CONSTRAINT `FKBilling311823` FOREIGN KEY (`UserID`) REFERENCES `users` (`ID`),
  CONSTRAINT `FKBilling322584` FOREIGN KEY (`bankAccountFromID`) REFERENCES `bankaccount` (`ID`),
  CONSTRAINT `FKBilling81539` FOREIGN KEY (`OperationFromID`) REFERENCES `operations` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `billing`
--

LOCK TABLES `billing` WRITE;
/*!40000 ALTER TABLE `billing` DISABLE KEYS */;
INSERT INTO `billing` VALUES (1,3,1,1,3,'Описание 1',2,150),(2,3,3,2,1,'Описание 2',2,100),(3,3,1,1,3,'Описание 3',2,100),(4,3,1,5,1,'Описание 4',2,58);
/*!40000 ALTER TABLE `billing` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-19  6:51:30
