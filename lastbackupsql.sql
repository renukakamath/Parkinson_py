/*
SQLyog Ultimate v11.11 (64 bit)
MySQL - 5.5.5-10.4.24-MariaDB : Database - by_stander
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`by_stander` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `by_stander`;

/*Table structure for table `bookings` */

DROP TABLE IF EXISTS `bookings`;

CREATE TABLE `bookings` (
  `book_id` int(11) NOT NULL AUTO_INCREMENT,
  `schedule_id` int(11) DEFAULT NULL,
  `patient_id` int(11) DEFAULT NULL,
  `date_time` varchar(50) DEFAULT NULL,
  `status` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`book_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `bookings` */

LOCK TABLES `bookings` WRITE;

insert  into `bookings`(`book_id`,`schedule_id`,`patient_id`,`date_time`,`status`) values (1,1,1,'07/04/2023','booked'),(2,2,4,'2023-04-08','pending'),(3,2,4,'2023-04-10','pending'),(4,2,6,'2023-04-10','pending');

UNLOCK TABLES;

/*Table structure for table `chat` */

DROP TABLE IF EXISTS `chat`;

CREATE TABLE `chat` (
  `chat_id` int(11) NOT NULL AUTO_INCREMENT,
  `sender_id` int(11) DEFAULT NULL,
  `sender_type` varchar(50) DEFAULT NULL,
  `receiver_id` int(11) DEFAULT NULL,
  `receiver_type` varchar(50) DEFAULT NULL,
  `message` varchar(500) DEFAULT NULL,
  `date_time` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`chat_id`)
) ENGINE=MyISAM AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;

/*Data for the table `chat` */

LOCK TABLES `chat` WRITE;

insert  into `chat`(`chat_id`,`sender_id`,`sender_type`,`receiver_id`,`receiver_type`,`message`,`date_time`) values (1,5,'doctor',6,'user','hai','2023-04-08 00:35:45'),(2,11,'user',4,'doctor','Hhshxh','2023-04-08 17:43:14'),(3,11,'user',4,'doctor','Haloii','2023-04-08 17:44:24'),(4,4,'doctor',11,'user','hiiii','2023-04-08 17:49:19');

UNLOCK TABLES;

/*Table structure for table `department` */

DROP TABLE IF EXISTS `department`;

CREATE TABLE `department` (
  `department_id` int(11) NOT NULL AUTO_INCREMENT,
  `department` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`department_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

/*Data for the table `department` */

LOCK TABLES `department` WRITE;

insert  into `department`(`department_id`,`department`) values (2,'ENT'),(3,'Dental'),(4,'Gynaecology'),(5,'Neurology');

UNLOCK TABLES;

/*Table structure for table `doctors` */

DROP TABLE IF EXISTS `doctors`;

CREATE TABLE `doctors` (
  `doctor_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `department_id` int(11) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `place` varchar(50) DEFAULT NULL,
  `qualification` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`doctor_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `doctors` */

LOCK TABLES `doctors` WRITE;

insert  into `doctors`(`doctor_id`,`login_id`,`department_id`,`first_name`,`last_name`,`phone`,`email`,`place`,`qualification`) values (1,4,2,'ammu','anu','6235326868','ammu1111@gmail.com','kochi','MBBS'),(2,5,3,'Aleena','Tresa','8787676767','aleena@gmail.com','kochi','MBBS');

UNLOCK TABLES;

/*Table structure for table `login` */

DROP TABLE IF EXISTS `login`;

CREATE TABLE `login` (
  `login_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) DEFAULT NULL,
  `password` varchar(50) DEFAULT NULL,
  `usertype` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`login_id`)
) ENGINE=MyISAM AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

/*Data for the table `login` */

LOCK TABLES `login` WRITE;

insert  into `login`(`login_id`,`username`,`password`,`usertype`) values (1,'admin','admin','admin'),(2,'pharmacy1@gmail.com','8976787678','pharmacy'),(6,'anu@gmail.com','9898787878','user'),(4,'ammu1111@gmail.com','6235326868','doctor'),(5,'aleena@gmail.com','8787676767','doctor'),(12,'Ammu','ammu123','user'),(11,'appu','appu123','user');

UNLOCK TABLES;

/*Table structure for table `medical_notes` */

DROP TABLE IF EXISTS `medical_notes`;

CREATE TABLE `medical_notes` (
  `medical_note_id` int(11) NOT NULL AUTO_INCREMENT,
  `booking_id` int(11) DEFAULT NULL,
  `description` varchar(200) DEFAULT NULL,
  `date_time` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`medical_note_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `medical_notes` */

LOCK TABLES `medical_notes` WRITE;

insert  into `medical_notes`(`medical_note_id`,`booking_id`,`description`,`date_time`) values (1,2,'fdsgfdg','2023-04-07 22:42:24'),(2,4,'fsdfsdf','2023-04-10 16:04:32');

UNLOCK TABLES;

/*Table structure for table `medicine_prescriptions` */

DROP TABLE IF EXISTS `medicine_prescriptions`;

CREATE TABLE `medicine_prescriptions` (
  `med_pres_id` int(11) NOT NULL AUTO_INCREMENT,
  `book_id` int(11) DEFAULT NULL,
  `medicine_id` int(11) DEFAULT NULL,
  `date_time` varchar(50) DEFAULT NULL,
  `med_pres_description` varchar(500) DEFAULT NULL,
  `status` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`med_pres_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `medicine_prescriptions` */

LOCK TABLES `medicine_prescriptions` WRITE;

insert  into `medicine_prescriptions`(`med_pres_id`,`book_id`,`medicine_id`,`date_time`,`med_pres_description`,`status`) values (1,1,1,'2023-04-07 22:35:17','fgfg','Forward'),(2,4,1,'2023-04-10 16:03:53','hjhgjg','Accept');

UNLOCK TABLES;

/*Table structure for table `medicines` */

DROP TABLE IF EXISTS `medicines`;

CREATE TABLE `medicines` (
  `medicine_id` int(11) NOT NULL AUTO_INCREMENT,
  `pharmacy_id` int(11) DEFAULT NULL,
  `medicine_name` varchar(50) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `expiry_date` varchar(50) DEFAULT NULL,
  `rate` decimal(10,0) DEFAULT NULL,
  `available_qty` decimal(10,0) DEFAULT NULL,
  PRIMARY KEY (`medicine_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `medicines` */

LOCK TABLES `medicines` WRITE;

insert  into `medicines`(`medicine_id`,`pharmacy_id`,`medicine_name`,`description`,`expiry_date`,`rate`,`available_qty`) values (1,1,'Dollo','fdgfdg gvfdgfdg','2023-04-28',100,99);

UNLOCK TABLES;

/*Table structure for table `patients` */

DROP TABLE IF EXISTS `patients`;

CREATE TABLE `patients` (
  `patient_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `first_name` varchar(50) DEFAULT NULL,
  `last_name` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `house_name` varchar(50) DEFAULT NULL,
  `place` varchar(50) DEFAULT NULL,
  `gender` varchar(50) DEFAULT NULL,
  `blood_group` varchar(50) DEFAULT NULL,
  `dob` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`patient_id`)
) ENGINE=MyISAM AUTO_INCREMENT=7 DEFAULT CHARSET=latin1;

/*Data for the table `patients` */

LOCK TABLES `patients` WRITE;

insert  into `patients`(`patient_id`,`user_id`,`first_name`,`last_name`,`phone`,`email`,`house_name`,`place`,`gender`,`blood_group`,`dob`) values (1,1,'anna','rose','9998898989','anna@gmail.com','kalarikkal','kochi','female','O+ve','12/03/1998'),(4,3,'Wggg','Fgg','9890989098','aaa@gmail.com','Hgfc','Kochi','Female ','O ve','12/2/1998'),(5,3,'Yaffy','Thomas','8909878909','yaffy@gmail.com','Hhh','Kochi','Male','O ve','12/12/98'),(6,4,'Anu','Mary','7890098909','anumary@gmail.com','Kalarikkal','Kochi','Female','O ve','29/02/99');

UNLOCK TABLES;

/*Table structure for table `payments` */

DROP TABLE IF EXISTS `payments`;

CREATE TABLE `payments` (
  `pay_id` int(11) NOT NULL AUTO_INCREMENT,
  `book_id` int(11) DEFAULT NULL,
  `amount` decimal(20,0) DEFAULT NULL,
  `payment_date` varchar(50) DEFAULT NULL,
  `payment_type` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`pay_id`)
) ENGINE=MyISAM AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;

/*Data for the table `payments` */

LOCK TABLES `payments` WRITE;

insert  into `payments`(`pay_id`,`book_id`,`amount`,`payment_date`,`payment_type`) values (5,4,100,'2023-04-10','Medicne'),(4,4,100,'2023-04-10','Schedule');

UNLOCK TABLES;

/*Table structure for table `pharmacy` */

DROP TABLE IF EXISTS `pharmacy`;

CREATE TABLE `pharmacy` (
  `pharmacy_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `pharmacy_name` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`pharmacy_id`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

/*Data for the table `pharmacy` */

LOCK TABLES `pharmacy` WRITE;

insert  into `pharmacy`(`pharmacy_id`,`login_id`,`pharmacy_name`,`email`,`phone`) values (1,2,'Pharmacy1','pharmacy1@gmail.com','8976787678');

UNLOCK TABLES;

/*Table structure for table `physical_conditions` */

DROP TABLE IF EXISTS `physical_conditions`;

CREATE TABLE `physical_conditions` (
  `physical_condition_id` int(11) NOT NULL AUTO_INCREMENT,
  `patient_id` int(11) DEFAULT NULL,
  `blood_pressure` varchar(50) DEFAULT NULL,
  `sugar` varchar(50) DEFAULT NULL,
  `cholesterol` varchar(50) DEFAULT NULL,
  `height` varchar(50) DEFAULT NULL,
  `weight` varchar(50) DEFAULT NULL,
  `date_time` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`physical_condition_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `physical_conditions` */

LOCK TABLES `physical_conditions` WRITE;

insert  into `physical_conditions`(`physical_condition_id`,`patient_id`,`blood_pressure`,`sugar`,`cholesterol`,`height`,`weight`,`date_time`) values (1,1,'100','210','100','160','200','12/2/23'),(2,6,'44','55','555','123','45','2023-04-10');

UNLOCK TABLES;

/*Table structure for table `schedule` */

DROP TABLE IF EXISTS `schedule`;

CREATE TABLE `schedule` (
  `schedule_id` int(11) NOT NULL AUTO_INCREMENT,
  `doctor_id` int(11) DEFAULT NULL,
  `start_time` varchar(50) DEFAULT NULL,
  `end_time` varchar(50) DEFAULT NULL,
  `date` varchar(50) DEFAULT NULL,
  `fee_amount` decimal(20,0) DEFAULT NULL,
  PRIMARY KEY (`schedule_id`)
) ENGINE=MyISAM AUTO_INCREMENT=3 DEFAULT CHARSET=latin1;

/*Data for the table `schedule` */

LOCK TABLES `schedule` WRITE;

insert  into `schedule`(`schedule_id`,`doctor_id`,`start_time`,`end_time`,`date`,`fee_amount`) values (1,2,'09:30','14:30','2023-04-18',250),(2,1,'09:00','14:00','2023-04-19',100);

UNLOCK TABLES;

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `login_id` int(11) DEFAULT NULL,
  `fname` varchar(50) DEFAULT NULL,
  `lname` varchar(50) DEFAULT NULL,
  `place` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `email` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;

/*Data for the table `user` */

LOCK TABLES `user` WRITE;

insert  into `user`(`user_id`,`login_id`,`fname`,`lname`,`place`,`phone`,`email`) values (1,6,'anu','mary','kochi','9898989898','anu@gmail.com'),(3,11,'Appu','Mk','Kochi','6789098789','appu@gmail.com'),(4,12,'Ammu','Ko','Kochi','5678909890','ammu@gmail.com');

UNLOCK TABLES;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
