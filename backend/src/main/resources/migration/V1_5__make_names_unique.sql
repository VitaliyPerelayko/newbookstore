USE books;

ALTER TABLE author ADD UNIQUE (name);
ALTER TABLE publisher ADD UNIQUE (name);
