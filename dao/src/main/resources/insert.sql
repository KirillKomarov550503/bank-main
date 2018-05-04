INSERT INTO person (name, surname, phone_number, passport_id, role)
VALUES ('Kirill', 'Komarov', 123, 4234, 'CLIENT');

INSERT INTO person (name, surname, phone_number, passport_id, role)
VALUES ('Vladislav', 'Maznya', 54, 234, 'CLIENT');

INSERT INTO person (name, surname, phone_number, passport_id, role)
VALUES ('Pavel', 'Zaretskya', 1252, 643, 'CLIENT');

INSERT INTO person (name, surname, phone_number, passport_id, role)
VALUES ('Vladimir', 'Putin', 1111111, 01, 'ADMIN');


INSERT INTO client (person_id) VALUES (1);
INSERT INTO client (person_id) VALUES (2);
INSERT INTO client (person_id) VALUES (3);


INSERT INTO account (client_id, balance, locked)
VALUES (1, 0, FALSE);

INSERT INTO account (client_id, balance, locked)
VALUES (1, 0, FALSE);

INSERT INTO account (client_id, balance, locked)
VALUES (3, 0, FALSE);


INSERT INTO card (account_id, pin, locked)
VALUES (3, 4234, FALSE);

INSERT INTO card (account_id, pin, locked)
VALUES (1, 1111, FALSE);

INSERT INTO card (account_id, pin, locked)
VALUES (1, 4004, FALSE);


INSERT INTO admin (person_id) VALUES (4);