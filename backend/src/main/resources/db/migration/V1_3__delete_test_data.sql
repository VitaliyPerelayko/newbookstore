START TRANSACTION;
USE books;

DELETE FROM book_has_author;
DELETE FROM book;
DELETE FROM author;
DELETE FROM publisher;

COMMIT;
