-- MySQL dump 10.13  Distrib 5.7.12, for Linux (x86_64)
--
-- Host: localhost    Database: tia_irene_ht
-- ------------------------------------------------------
-- Server version	5.7.12-0ubuntu1

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
-- Table structure for table `tbl_clientes`
--

DROP TABLE IF EXISTS `tbl_clientes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_clientes` (
  `id_cliente` varchar(25) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `apellido` varchar(45) NOT NULL,
  `sexo` set('m','f') NOT NULL,
  `nacionalidad` varchar(45) DEFAULT NULL,
  `direccion` varchar(100) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`id_cliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_clientes`
--

LOCK TABLES `tbl_clientes` WRITE;
/*!40000 ALTER TABLE `tbl_clientes` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_clientes` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_clientes_has_tbl_habitaciones`
--

DROP TABLE IF EXISTS `tbl_clientes_has_tbl_habitaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_clientes_has_tbl_habitaciones` (
  `id_clientes_has_tbl_habitaciones` int(11) NOT NULL AUTO_INCREMENT,
  `id_cliente` varchar(25) NOT NULL,
  `id_habitacion` int(11) NOT NULL,
  `fecha_hora_entrada` datetime NOT NULL,
  `fecha_hora_salida` datetime DEFAULT NULL,
  `id_usuario` varchar(15) NOT NULL,
  `reservacion` tinyint(1) DEFAULT NULL,
  `activo` tinyint(1) DEFAULT NULL,
  `cancelado` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id_clientes_has_tbl_habitaciones`),
  KEY `fk_tbl_clientes_has_tbl_habitaciones_tbl_clientes1_idx` (`id_cliente`),
  KEY `fk_tbl_clientes_has_tbl_habitaciones_tbl_habitaciones1_idx` (`id_habitacion`),
  KEY `fk_tbl_clientes_has_tbl_habitaciones_tbl_usuario1_idx` (`id_usuario`),
  CONSTRAINT `fk_tbl_clientes_has_tbl_habitaciones_tbl_clientes1` FOREIGN KEY (`id_cliente`) REFERENCES `tbl_clientes` (`id_cliente`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tbl_clientes_has_tbl_habitaciones_tbl_habitaciones1` FOREIGN KEY (`id_habitacion`) REFERENCES `tbl_habitaciones` (`id_habitacion`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tbl_clientes_has_tbl_habitaciones_tbl_usuario1` FOREIGN KEY (`id_usuario`) REFERENCES `tbl_usuarios` (`id_usuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_clientes_has_tbl_habitaciones`
--

LOCK TABLES `tbl_clientes_has_tbl_habitaciones` WRITE;
/*!40000 ALTER TABLE `tbl_clientes_has_tbl_habitaciones` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_clientes_has_tbl_habitaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_galeria`
--

DROP TABLE IF EXISTS `tbl_galeria`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_galeria` (
  `id_foto` int(11) NOT NULL AUTO_INCREMENT,
  `id_habitacion` int(11) NOT NULL,
  `imagen` varchar(45) NOT NULL,
  `descripcion` varchar(200) NOT NULL,
  `promovido` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id_foto`),
  KEY `fk_tbl_galeria_tbl_habitaciones1_idx` (`id_habitacion`),
  CONSTRAINT `fk_tbl_galeria_tbl_habitaciones1` FOREIGN KEY (`id_habitacion`) REFERENCES `tbl_habitaciones` (`id_habitacion`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_galeria`
--

LOCK TABLES `tbl_galeria` WRITE;
/*!40000 ALTER TABLE `tbl_galeria` DISABLE KEYS */;
INSERT INTO `tbl_galeria` VALUES (1,1,'gal2.jpg','Cómoda habitación sencilla con aire acondicionado, televisión por cable y baño con agua caliente.',1),(3,2,'gal3.jpg','Cómoda habitación doble con aire acondicionado, televisión por cable y baño con agua caliente.',1);
/*!40000 ALTER TABLE `tbl_galeria` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_habitaciones`
--

DROP TABLE IF EXISTS `tbl_habitaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_habitaciones` (
  `id_habitacion` int(11) NOT NULL,
  `tipo_habitacion` int(11) NOT NULL,
  `costo` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id_habitacion`),
  KEY `fk_tbl_habitaciones_tbl_tipo_habitacion_idx` (`tipo_habitacion`),
  CONSTRAINT `fk_tbl_habitaciones_tbl_tipo_habitacion` FOREIGN KEY (`tipo_habitacion`) REFERENCES `tbl_tipos_habitacion` (`id_tipo_habitacion`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_habitaciones`
--

LOCK TABLES `tbl_habitaciones` WRITE;
/*!40000 ALTER TABLE `tbl_habitaciones` DISABLE KEYS */;
INSERT INTO `tbl_habitaciones` VALUES (1,1,16.00),(2,1,16.00),(3,2,25.00),(4,2,25.00);
/*!40000 ALTER TABLE `tbl_habitaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_servicios_habitaciones`
--

DROP TABLE IF EXISTS `tbl_servicios_habitaciones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_servicios_habitaciones` (
  `id_servicio_habitacion` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(45) NOT NULL,
  `costo` decimal(10,2) NOT NULL,
  PRIMARY KEY (`id_servicio_habitacion`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_servicios_habitaciones`
--

LOCK TABLES `tbl_servicios_habitaciones` WRITE;
/*!40000 ALTER TABLE `tbl_servicios_habitaciones` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_servicios_habitaciones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_solicitudes_servicio_habitacion`
--

DROP TABLE IF EXISTS `tbl_solicitudes_servicio_habitacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_solicitudes_servicio_habitacion` (
  `id_solicitud_servicio_habitacion` int(11) NOT NULL AUTO_INCREMENT,
  `id_clientes_has_tbl_habitaciones` int(11) NOT NULL,
  `id_servicio_habitacion` int(11) NOT NULL,
  `fecha_hora` datetime NOT NULL,
  `id_usuario` varchar(15) DEFAULT NULL,
  PRIMARY KEY (`id_solicitud_servicio_habitacion`),
  KEY `fk_tbl_solicitud_servicio_habitacion_tbl_clientes_has_tbl_h_idx` (`id_clientes_has_tbl_habitaciones`),
  KEY `fk_tbl_solicitud_servicio_habitacion_tbl_usuario1_idx` (`id_usuario`),
  KEY `fk_tbl_solicitud_servicio_habitacion_tbl_servicios_habitaci_idx` (`id_servicio_habitacion`),
  CONSTRAINT `fk_tbl_solicitud_servicio_habitacion_tbl_clientes_has_tbl_hab1` FOREIGN KEY (`id_clientes_has_tbl_habitaciones`) REFERENCES `tbl_clientes_has_tbl_habitaciones` (`id_clientes_has_tbl_habitaciones`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tbl_solicitud_servicio_habitacion_tbl_servicios_habitacion1` FOREIGN KEY (`id_servicio_habitacion`) REFERENCES `tbl_servicios_habitaciones` (`id_servicio_habitacion`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_tbl_solicitud_servicio_habitacion_tbl_usuario1` FOREIGN KEY (`id_usuario`) REFERENCES `tbl_usuarios` (`id_usuario`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_solicitudes_servicio_habitacion`
--

LOCK TABLES `tbl_solicitudes_servicio_habitacion` WRITE;
/*!40000 ALTER TABLE `tbl_solicitudes_servicio_habitacion` DISABLE KEYS */;
/*!40000 ALTER TABLE `tbl_solicitudes_servicio_habitacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_tipos_habitacion`
--

DROP TABLE IF EXISTS `tbl_tipos_habitacion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_tipos_habitacion` (
  `id_tipo_habitacion` int(11) NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(45) NOT NULL,
  PRIMARY KEY (`id_tipo_habitacion`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_tipos_habitacion`
--

LOCK TABLES `tbl_tipos_habitacion` WRITE;
/*!40000 ALTER TABLE `tbl_tipos_habitacion` DISABLE KEYS */;
INSERT INTO `tbl_tipos_habitacion` VALUES (1,'Sensilla con aire acondicionado'),(2,'Doble con aire acondicionado'),(3,'Sensilla con abanico'),(4,'Doble con abanico');
/*!40000 ALTER TABLE `tbl_tipos_habitacion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tbl_usuarios`
--

DROP TABLE IF EXISTS `tbl_usuarios`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tbl_usuarios` (
  `id_usuario` varchar(15) NOT NULL,
  `contrasena` varchar(60) NOT NULL,
  `nombre` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `cargo` enum('admin','operador') DEFAULT NULL,
  `activo` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tbl_usuarios`
--

LOCK TABLES `tbl_usuarios` WRITE;
/*!40000 ALTER TABLE `tbl_usuarios` DISABLE KEYS */;
INSERT INTO `tbl_usuarios` VALUES ('admin','$2a$08$t4sqQT77zkQXfuopyMPZe.x33ZRJ/nQzkTXGj9MSYQM.fVQpLHxda','administrador','sanchezxiomara35@yahoo.com','admin',1);
/*!40000 ALTER TABLE `tbl_usuarios` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2016-06-28 15:46:38
