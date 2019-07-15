

SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0;
SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0;
SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';

-- Schema books
-- -----------------------------------------------------
CREATE SCHEMA IF NOT EXISTS `books` DEFAULT CHARACTER SET utf8 ;
USE `books` ;

-- -----------------------------------------------------
-- Table `books`.`publisher`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `books`.`publisher` (
                                                    `id` BIGINT NOT NULL AUTO_INCREMENT,
                                                    `name` VARCHAR(50) NOT NULL,
                                                    PRIMARY KEY (`id`)
                                               )
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `books`.`users`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `books`.`book` (
                                                   `id` BIGINT NOT NULL AUTO_INCREMENT,
                                                   `name` VARCHAR(50) NOT NULL,
                                                   `description` VARCHAR(200) NULL,
                                                   `publishDate` DATE NOT NULL,
                                                   `publisher` BIGINT NOT NULL,
                                                   `price` DECIMAL(5,2) NOT NULL,
                                                   `category` INTEGER NOT NULL,
                                                   PRIMARY KEY (`id`),
                                                   CONSTRAINT `fk_book_publisher_id`
                                                                 FOREIGN KEY (`publisher`)
                                                                     REFERENCES `books`.`publisher` (`id`)
                                                                     ON DELETE NO ACTION
                                                                     ON UPDATE NO ACTION
                                          )
    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `books`.`author`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `books`.`author` (
                                                  `id` BIGINT NOT NULL AUTO_INCREMENT,
                                                  `name` VARCHAR(50) NOT NULL,
                                                  `bio` VARCHAR(200) NULL,
                                                  `birthDate` DATE NOT NULL,
                                                  PRIMARY KEY (`id`)
                                            )

    ENGINE = InnoDB;


-- -----------------------------------------------------
-- Table `books`.`book_has_author`
-- -----------------------------------------------------
CREATE TABLE IF NOT EXISTS `books`.`book_has_author` (
                                                        `book_id` BIGINT NOT NULL,
                                                        `author_id` BIGINT NOT NULL,
                                                        PRIMARY KEY (`book_id`, `author_id`),
                                                        INDEX `fk_users_has_role_role1_idx` (`book_id` ASC) VISIBLE,
                                                        INDEX `fk_users_has_role_users1_idx` (`author_id` ASC) VISIBLE,
                                                        CONSTRAINT `fk_book_id`
                                                            FOREIGN KEY (`book_id`)
                                                                REFERENCES `books`.`book` (`id`)
                                                                ON DELETE CASCADE
                                                                ON UPDATE NO ACTION,
                                                        CONSTRAINT `fk_author_id`
                                                            FOREIGN KEY (`author_id`)
                                                                REFERENCES `books`.`author` (`id`)
                                                                ON DELETE CASCADE
                                                                ON UPDATE NO ACTION)
    ENGINE = InnoDB;

SET SQL_MODE=@OLD_SQL_MODE;
SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS;
SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS;


