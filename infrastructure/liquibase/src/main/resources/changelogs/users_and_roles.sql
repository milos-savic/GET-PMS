INSERT INTO role (id, code, name, description, version) VALUES (1, 'DEV', 'developer', 'developer user role', 0);
INSERT INTO role (id, code, name, description, version) VALUES (2, 'PROJECT_MANAGER', 'project_manager', 'project_manager user role', 0);
INSERT INTO role (id, code, name, description, version) VALUES (3, 'ADMIN', 'administrator',  'administrator user role', 0);

INSERT INTO user (id, firstname, lastname, email, version) VALUES (11, 'James', 'Bond', 'james.bond@mail.com', 0);
INSERT INTO user (id, firstname, lastname, email, version) VALUES (12, 'Jason', 'Born'   ,'jason.born@mail.com', 0);
INSERT INTO user (id, firstname, lastname, email, version) VALUES (13, 'Klark', 'Kent'   ,'klark.kent@mail.com', 0);

INSERT INTO useraccount (id, user, username, creationdate, active, role, version) VALUES (100, 11, 'bond', '2016-10-13 12:43:20', 'Y', 1, 0);
INSERT INTO useraccount (id, user, username, creationdate, active, role, version) VALUES (101, 12, 'born', '2016-10-13 12:43:20', 'Y', 2, 0);
INSERT INTO useraccount (id, user, username, creationdate, active, role, version) VALUES (102, 13, 'kent', '2016-10-13 12:43:20', 'Y', 3, 0);