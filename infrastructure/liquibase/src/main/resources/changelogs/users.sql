INSERT INTO user (id, firstname, lastname, email, version) VALUES (11, 'James', 'Bond', 'james.bond@mail.com', 0);
INSERT INTO user (id, firstname, lastname, email, version) VALUES (12, 'Jason', 'Born'   ,'jason.born@mail.com', 0);
INSERT INTO user (id, firstname, lastname, email, version) VALUES (13, 'Klark', 'Kent'   ,'klark.kent@mail.com', 0);

INSERT INTO useraccount (id, user, username, creationdate, active, role, version) VALUES (100, 11, 'bond', '2016-10-13 12:43:20', 'Y', 'ADMIN', 0);
INSERT INTO useraccount (id, user, username, creationdate, active, role, version) VALUES (101, 12, 'born', '2016-10-13 12:43:20', 'Y', 'PROJECT_MANAGER', 0);
INSERT INTO useraccount (id, user, username, creationdate, active, role, version) VALUES (102, 13, 'kent', '2016-10-13 12:43:20', 'Y', 'DEV', 0);