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
