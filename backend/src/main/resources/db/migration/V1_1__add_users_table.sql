USE books;
CREATE TABLE IF NOT EXISTS users(
                                    `id` BIGINT NOT NULL AUTO_INCREMENT,
                                    `name` VARCHAR(50) NULL,
                                    `surname` VARCHAR(50) NULL,
                                    `phone` VARCHAR(15) NULL,
                                    `e_mail` VARCHAR(50) NULL,
                                    `userName` VARCHAR(50) NOT NULL,
                                    `password` VARCHAR(100) NOT NULL,
                                    PRIMARY KEY (`id`),
                                    UNIQUE INDEX `username_UNIQUE` (`userName` ASC) VISIBLE)
    ENGINE = InnoDB;
