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


INSERT INTO account (client_id, balance, locked, account_id)
VALUES (1, 0, FALSE, 423);

INSERT INTO account (client_id, balance, locked, account_id)
VALUES (1, 0, FALSE, 100);

INSERT INTO account (client_id, balance, locked, account_id)
VALUES (3, 0, FALSE, 123);


INSERT INTO card (account_id, card_id, pin, locked)
VALUES (3, 100, 4234, FALSE);

INSERT INTO card (account_id, card_id, pin, locked)
VALUES (1, 128989, 1111, FALSE);

INSERT INTO card (account_id, card_id, pin, locked)
VALUES (1, 101, 4004, FALSE);


INSERT INTO admin (person_id) VALUES (4);