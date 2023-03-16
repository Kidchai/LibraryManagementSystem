INSERT INTO people (name, birth_year)
VALUES ('Ivanov Ivan Ivanovich', 1976);
INSERT INTO people (name, birth_year)
VALUES ('Petrov Petr Petrovich', 1986);
INSERT INTO people (name, birth_year)
VALUES ('Markov Mark Markovich', 1996);

INSERT INTO books (person_id, title, author, year)
VALUES (2, 'To Kill a Mockingbird', 'Harper Lee', 1960);
INSERT INTO books (person_id, title, author, year)
VALUES (NULL, 'Atomic Habits', 'James Clear', 2018);
INSERT INTO books (person_id, title, author, year)
VALUES (2, 'A Brief History of Time', 'Stephen Hawking', 1988);
INSERT INTO books (person_id, title, author, year)
VALUES (3, 'Crime and Punishment', 'Fyodor Dostoyevsky', 1866);
INSERT INTO books (person_id, title, author, year)
VALUES (NULL, 'Lullaby', 'Chuck Palahniuk', 2002);
INSERT INTO books (person_id, title, author, year)
VALUES (NULL, 'The Lord of the Rings Novel', ' J. R. R. Tolkien', 1954);

-- SELECT person.*, CONCAT(title, ', ', author, ', ', year) AS book
-- FROM person
--          LEFT OUTER JOIN book ON person.id = book.person_id
-- WHERE person.id = 1