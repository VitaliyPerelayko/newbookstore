-- -----------------------------------------------------
-- Data for table `books`.`publisher`
-- -----------------------------------------------------
START TRANSACTION;
USE `books`;
INSERT INTO `books`.`publisher` (`id`, `name`) VALUES (1, 'AST');
INSERT INTO `books`.`publisher` (`id`, `name`) VALUES (2, 'ABS');
INSERT INTO `books`.`publisher` (`id`, `name`) VALUES (3, 'Piter');
INSERT INTO `books`.`publisher` (`id`, `name`) VALUES (4, 'Aversav');
INSERT INTO `books`.`publisher` (`id`, `name`) VALUES (5, 'RTS');
INSERT INTO `books`.`publisher` (`id`, `name`) VALUES (6, 'JSP');


COMMIT;


-- -----------------------------------------------------
-- Data for table `books`.`book`
-- -----------------------------------------------------
START TRANSACTION;
USE `books`;
INSERT INTO `books`.`book` (`id`, `name`, `description`, `publishDate`, `price`, `category`, publisher) VALUES (1, 'Iosif', 'Stalin', '1953-10-10', 6.66, 0, 1);
INSERT INTO `books`.`book` (`id`, `name`, `description`, `publishDate`, `price`, `category`, publisher) VALUES (2, 'Lev', 'Trozki', '1957-10-10', 5.55, 1, 2);
INSERT INTO `books`.`book` (`id`, `name`, `description`, `publishDate`, `price`, `category`, publisher) VALUES (3, 'Nikita', 'Khrushchev', '1978-10-10', 3.33, 2, 3);
INSERT INTO `books`.`book` (`id`, `name`, `description`, `publishDate`, `price`, `category`, publisher) VALUES (4, 'Leonid', 'Breznev', '1999-10-02', 2.22, 0, 4);
INSERT INTO `books`.`book` (`id`, `name`, `description`, `publishDate`, `price`, `category`, publisher) VALUES (5, 'Leonid', 'Lenin', '2003-09-09', 1.11, 1, 5);
INSERT INTO `books`.`book` (`id`, `name`, `description`, `publishDate`, `price`, `category`, publisher) VALUES (6, 'Mikhail', 'Gorbachev', '2007-02-14', 4.44, 2, 6);

COMMIT;


-- -----------------------------------------------------
-- Data for table `books`.`author`
-- -----------------------------------------------------
START TRANSACTION;
USE `books`;
INSERT INTO `books`.`author` (`id`, `name`, `bio`, `birthDate`) VALUES (1, 'Pelevin', 'foo', '1979-10-10');
INSERT INTO `books`.`author` (`id`, `name`, `bio`, `birthDate`) VALUES (2, 'Hemingway', 'foo', '1968-10-10');
INSERT INTO `books`.`author` (`id`, `name`, `bio`, `birthDate`) VALUES (3, 'Gogol', 'foo', '1977-10-10');
INSERT INTO `books`.`author` (`id`, `name`, `bio`, `birthDate`) VALUES (4, 'Chehov', 'foo', '1978-10-10');
INSERT INTO `books`.`author` (`id`, `name`, `bio`, `birthDate`) VALUES (5, 'Bradbery', 'foo', '1999-10-10');


COMMIT;

-- -----------------------------------------------------
-- Data for table `logistics`.`book_has_author`
-- -----------------------------------------------------
START TRANSACTION;
USE `books`;
INSERT INTO `books`.`book_has_author` (`book_id`, `author_id`) VALUES (1, 1);
INSERT INTO `books`.`book_has_author` (`book_id`, `author_id`) VALUES (1, 2);
INSERT INTO `books`.`book_has_author` (`book_id`, `author_id`) VALUES (1, 3);
INSERT INTO `books`.`book_has_author` (`book_id`, `author_id`) VALUES (2, 4);
INSERT INTO `books`.`book_has_author` (`book_id`, `author_id`) VALUES (2, 5);
INSERT INTO `books`.`book_has_author` (`book_id`, `author_id`) VALUES (3, 1);
INSERT INTO `books`.`book_has_author` (`book_id`, `author_id`) VALUES (4, 1);
INSERT INTO `books`.`book_has_author` (`book_id`, `author_id`) VALUES (5, 5);
INSERT INTO `books`.`book_has_author` (`book_id`, `author_id`) VALUES (6, 2);


COMMIT;
