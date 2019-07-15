USE books;

CREATE TABLE IF NOT EXISTS reviews
(
    id   BIGINT      NOT NULL AUTO_INCREMENT,
    rating TINYINT   NOT NULL,
    comment VARCHAR(300) NULL,
    time DATETIME NOT NULL,
    user_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_user_id
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT fk_book_id
        FOREIGN KEY (book_id)
            REFERENCES book (id)
            ON DELETE CASCADE
            ON UPDATE NO ACTION
);

ALTER TABLE book ADD number SMALLINT NULL;
