INSERT INTO user (id, firstname, lastname, email, version) VALUES (10, 'James', 'Bond',   'james.bond@mail.com',      0);
INSERT INTO user (id, firstname, lastname, email, version) VALUES (11, 'Jason', 'Born',   'jason.born@mail.com',      0);
INSERT INTO user (id, firstname, lastname, email, version) VALUES (12, 'Klark', 'Kent',   'klark.kent@mail.com',      0);
INSERT INTO user (id, firstname, lastname, email, version) VALUES (13, 'Brus',  'Wein',   'brus.wein@mail.com',       0);
INSERT INTO user (id, firstname, lastname, email, version) VALUES (14, 'Piter', 'Parker', 'piter.parker@mail.com',    0);
INSERT INTO user (id, firstname, lastname, email, version) VALUES (15, 'Steve', 'Roger',  'steve.roger@mail.com',     0);
INSERT INTO user (id, firstname, lastname, email, version) VALUES (16, 'Tim',   'Drake',    'tim.drake@mail.com',     0);

INSERT INTO useraccount (id, user, username, creationdate, active, role, version) VALUES (100, 10, 'bond',            '2016-10-13 12:43:20', true, 'ADMIN',           0);
INSERT INTO useraccount (id, user, username, creationdate, active, role, version) VALUES (101, 11, 'born',            '2016-10-13 12:43:20', true, 'PROJECT_MANAGER', 0);
INSERT INTO useraccount (id, user, username, creationdate, active, role, version) VALUES (102, 12, 'superman',        '2016-10-13 12:43:20', true, 'DEV',             0);
INSERT INTO useraccount (id, user, username, creationdate, active, role, version) VALUES (103, 13, 'batman',          '2016-10-13 12:43:20', true, 'PROJECT_MANAGER', 0);
INSERT INTO useraccount (id, user, username, creationdate, active, role, version) VALUES (104, 14, 'spiderman',       '2016-10-13 12:43:20', true, 'PROJECT_MANAGER', 0);
INSERT INTO useraccount (id, user, username, creationdate, active, role, version) VALUES (105, 15, 'captain_america', '2016-10-13 12:43:20', true, 'DEV', 0);
INSERT INTO useraccount (id, user, username, creationdate, active, role, version) VALUES (106, 16, 'robin',           '2016-10-13 12:43:20', true, 'DEV', 0);