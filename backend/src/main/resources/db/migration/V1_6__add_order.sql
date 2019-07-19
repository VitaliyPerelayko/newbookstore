USE books;

CREATE TABLE IF NOT EXISTS books_order
(
    id              BIGINT        NOT NULL AUTO_INCREMENT,
    comment         VARCHAR(150)  NULL,
    deliver_address VARCHAR(100)  NOT NULL,
    date_time       DATETIME      NOT NULL,
    total_price     DECIMAL(6, 2) NOT NULL,
    user_id         BIGINT        NOT NULL,
    is_paid         BOOLEAN       NOT NULL,
    is_closed       BOOLEAN       NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_order_user_id
        FOREIGN KEY (user_id)
            REFERENCES users (id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE IF NOT EXISTS order_has_books
(
    order_id BIGINT   NOT NULL,
    book_id  BIGINT   NOT NULL,
    number   SMALLINT NOT NULL,
    PRIMARY KEY (order_id, book_id),
    CONSTRAINT fk_ohb_order_id
        FOREIGN KEY (order_id)
            REFERENCES books_order (id)
            ON DELETE CASCADE
            ON UPDATE CASCADE,
    CONSTRAINT fk_ohb_book_id
        FOREIGN KEY (book_id)
            REFERENCES book (id)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);
