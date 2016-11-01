# Phonebook

Create MySQL DB

ALTER SCHEMA `phonebook`  DEFAULT CHARACTER SET utf8 DEFAULT COLLATE utf8_general_ci;
USE `phonebook`;
CREATE  TABLE `phonebook`.`users` (
  `id` BIGINT(20) NOT NULL,
  `login` VARCHAR(45) NOT NULL ,
  `passwd` VARCHAR(45) NOT NULL ,
  `fio` VARCHAR(100) NOT NULL ,
  PRIMARY KEY (`id`) ) 
ENGINE = InnoDB
DEFAULT CHARACTER SET = utf8
COLLATE = utf8_general_ci; 

CREATE  TABLE `phonebook`.`phones` (
  `id` BIGINT(20) NOT NULL ,
  `name` VARCHAR(45) NOT NULL ,
  `surname` VARCHAR(45) NOT NULL ,
  `middlename` VARCHAR(45) NOT NULL ,
  `phonemob` VARCHAR(45) NOT NULL ,
  `phonehome` VARCHAR(45) NULL ,
  `address` VARCHAR(100) NULL ,
  `email` VARCHAR(45) NULL ,
  `users_id` BIGINT(20) NOT NULL ,
  PRIMARY KEY (`id`, `users_id`) ,
  INDEX `fk_phones_users` (`users_id` ASC) ,
  CONSTRAINT `fk_phones_users`
    FOREIGN KEY (`users_id` )
    REFERENCES `phonebook`.`users` (`id` )
    ON DELETE NO ACTION
    ON UPDATE NO ACTION);
