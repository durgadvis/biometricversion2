-- MySQL Workbench Forward Engineering

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='TRADITIONAL,ALLOW_INVALID_DATES';

-- -----------------------------------------------------
-- Schema mydb
-- -----------------------------------------------------
-- -----------------------------------------------------
-- Schema biometric
-- -----------------------------------------------------
DROP SCHEMA IF EXISTS `biometric` ;

-- -----------------------------------------------------
-- Schema biometric
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `biometric` DEFAULT CHARACTER SET utf8 ;
USE `biometric` ;

-- -----------------------------------------------------
-- Table `biometric`.`userdetails`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `biometric`.`userdetails` ;

CREATE TABLE IF NOT EXISTS `biometric`.`userdetails` (
  `pk` INT(11) NOT NULL AUTO_INCREMENT,
  `name` VARCHAR(45) NOT NULL,
  `phoneNumber` BIGINT(10) NOT NULL,
  `emailId` VARCHAR(45) NULL DEFAULT NULL,
  `address` VARCHAR(45) NULL DEFAULT NULL,
  `age` INT(100) NULL DEFAULT NULL,
  `fpIso` BLOB NOT NULL,
  `fbBmpImage` LONGBLOB NOT NULL,
  PRIMARY KEY (`pk`),
  UNIQUE INDEX `pk_UNIQUE` (`pk` ASC))
ENGINE = InnoDB
AUTO_INCREMENT = 58
DEFAULT CHARACTER SET = utf8;


-- -----------------------------------------------------
-- Table `biometric`.`carddetails`
-- -----------------------------------------------------
DROP TABLE IF EXISTS `biometric`.`carddetails` ;

CREATE TABLE IF NOT EXISTS `biometric`.`carddetails` (
  `pk` INT(11) NOT NULL AUTO_INCREMENT,
  `nameOnCard` VARCHAR(45) NOT NULL,
  `cardNumber` VARCHAR(16) NOT NULL,
  `cvv` INT(3) NOT NULL,
  `expiryDate` VARCHAR(6) NULL DEFAULT NULL,
  `fk` INT(11) NOT NULL,
  `bankName` VARCHAR(30) NOT NULL,
  PRIMARY KEY (`pk`),
  UNIQUE INDEX `pk_UNIQUE` (`pk` ASC),
  INDEX `userPk_idx` (`fk` ASC),
  CONSTRAINT `userPk`
    FOREIGN KEY (`fk`)
    REFERENCES `biometric`.`userdetails` (`pk`)
    ON DELETE CASCADE
    ON UPDATE CASCADE)
ENGINE = InnoDB
AUTO_INCREMENT = 40
DEFAULT CHARACTER SET = utf8;


SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


-- Model queires--

select * from biometric.userdetails1;
select * from biometric.carddetails1;
select * from biometric.userdetails1_carddetails1;


show variables like '%general%';
SET GLOBAL general_log_file = 'C:/Project_bio/Project/logs/mysql.log'
SET GLOBAL general_log = 'ON';

select * from (biometric.userdetails1 as user, biometric.carddetails1) inner join biometric.carddetails1 as card on user.pk = card.USER_ID left outer join user on card.USER_ID=user.pk  where user.pk=1 and card.bankName="CORPORATION";

select biometric.userdetails1.pk as pk1_1_2_, biometric.userdetails1.address as address2_1_2_, biometric.userdetails1.age as age3_1_2_, biometric.userdetails1.emailId as emailId4_1_2_,  biometric.userdetails1.fgBmp as fgBmp5_1_2_, biometric.userdetails1.fgIso as fgIso6_1_2_, biometric.userdetails1.name as name7_1_2_, biometric.userdetails1.phonenumber as phonenum8_1_2_,  biometric.carddetails1.pk as pk1_0_0_, biometric.carddetails1.bankName as bankName2_0_0_, biometric.carddetails1.cardNumber as cardNumb3_0_0_, biometric.carddetails1.cvv as cvv4_0_0_,  biometric.carddetails1.expiryDate as expiryDa5_0_0_, biometric.carddetails1.nameOnCard as nameOnCa6_0_0_, biometric.carddetails1.USER_ID as USER_ID7_0_0_, user4_.pk as pk1_1_1_,  user4_.address as address2_1_1_, user4_.age as age3_1_1_, user4_.emailId as emailId4_1_1_, user4_.fgBmp as fgBmp5_1_1_,   user4_.fgIso as fgIso6_1_1_, user4_.name as name7_1_1_, user4_.phonenumber as phonenum8_1_1_ from biometric.userdetails1 inner join biometric.carddetails1 on biometric.userdetails1.pk=biometric.carddetails1.USER_ID  left outer join userdetails1 user4_ on biometric.carddetails1.USER_ID=user4_.pk where biometric.userdetails1.pk=1 and biometric.carddetails1.bankName='SBI';

