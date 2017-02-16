-- MySQL dump 10.13  Distrib 5.7.17, for Linux (x86_64)
--
-- Host: eu-cdbr-west-01.cleardb.com    Database: heroku_0d3bcca37c58950
-- ------------------------------------------------------
-- Server version	5.5.40-log

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

create database gtrchamp;
use gtrchamp;
--
-- Table structure for table `championship`
--

DROP TABLE IF EXISTS `championship`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `championship` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) DEFAULT NULL,
  `type` varchar(5) NOT NULL DEFAULT 'wtcc',
  `mode` int(11) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `country`
--

DROP TABLE IF EXISTS `country`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `country` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `driver`
--

DROP TABLE IF EXISTS `driver`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `driver` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `birthdate` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=32 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `race`
--

DROP TABLE IF EXISTS `race`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `race` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `championshipId` int(11) NOT NULL,
  `trackId` int(11) NOT NULL,
  `date` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `idxChampionnat_idx` (`championshipId`),
  KEY `idxCircuit_idx` (`trackId`),
  CONSTRAINT `idxChampionnat` FOREIGN KEY (`championshipId`) REFERENCES `championship` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `idxCircuit` FOREIGN KEY (`trackId`) REFERENCES `track` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=262 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `result`
--

DROP TABLE IF EXISTS `result`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `result` (
  `raceId` int(11) NOT NULL,
  `driverId` int(11) NOT NULL,
  `arrivalPosition` int(11) NOT NULL,
  `pole` bit(1) NOT NULL,
  `startingPosition` int(11) DEFAULT NULL,
  `raceNumber` int(11) NOT NULL,
  PRIMARY KEY (`raceId`,`driverId`,`raceNumber`),
  KEY `FK_csar285mo34thvim6y214fpk7` (`raceId`),
  KEY `FK_icp8oknrf8mhnbh11keyk3mpg` (`driverId`),
  KEY `FK_e1lwybh6m6ah2fkloqcffwxoq_idx` (`arrivalPosition`),
  CONSTRAINT `FK_csar285mo34thvim6y214fpk7` FOREIGN KEY (`raceId`) REFERENCES `race` (`id`),
  CONSTRAINT `FK_e1lwybh6m6ah2fkloqcffwxoq` FOREIGN KEY (`arrivalPosition`) REFERENCES `score` (`position`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `FK_icp8oknrf8mhnbh11keyk3mpg` FOREIGN KEY (`driverId`) REFERENCES `driver` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `score`
--

DROP TABLE IF EXISTS `score`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `score` (
  `position` int(11) NOT NULL,
  `points` int(11) NOT NULL,
  `type` varchar(5) NOT NULL DEFAULT 'wtcc',
  PRIMARY KEY (`position`,`type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `track`
--

DROP TABLE IF EXISTS `track`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `track` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `length` decimal(3,1) NOT NULL DEFAULT '0.0',
  `countryId` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `idxPays_idx` (`countryId`),
  CONSTRAINT `idxPays` FOREIGN KEY (`countryId`) REFERENCES `country` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=125 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-02-10 14:46:41
