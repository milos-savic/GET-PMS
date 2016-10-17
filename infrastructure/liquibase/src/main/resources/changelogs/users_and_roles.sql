INSERT INTO roletype (id, code, name, description, version) VALUES (1, 'dev', 'developer', 'role user type', 0);
INSERT INTO roletype (id, code, name, description, version) VALUES (2, 'pm', 'project_manager', 'role project manager type', 0);
INSERT INTO roletype (id, code, name, description, version) VALUES (3, 'admin', 'administrator','role administrator type', 0);

INSERT INTO role (id, code, roletype, name, description, version) VALUES (1, 'dev', 1, 'developer', 'developer', 0);
INSERT INTO role (id, code, roletype, name, description, version) VALUES (2, 'pm', 2, 'project_manager', 'project_manager', 0);
INSERT INTO role (id, code, roletype, name, description, version) VALUES (3, 'admin', 3, 'administrator',  'administrator', 0);

INSERT INTO user (id, firstname, lastname, email, version) VALUES (11, 'James', 'Bond', 'james.bond@mail.com', 0);
INSERT INTO user (id, firstname, lastname, email, version) VALUES (12, 'Jason', 'Born'   ,'jason.born@mail.com', 0);
INSERT INTO user (id, firstname, lastname, email, version) VALUES (13, 'Klark', 'Kent'   ,'klark.kent@mail.com', 0);

INSERT INTO useraccount (id, user, username, creationdate, active, role, version) VALUES (100, 11, 'bond', '2016-10-13 12:43:20', 'Y', 1, 0);
INSERT INTO useraccount (id, user, username, creationdate, active, role, version) VALUES (101, 12, 'born', '2016-10-13 12:43:20', 'Y', 2, 0);
INSERT INTO useraccount (id, user, username, creationdate, active, role, version) VALUES (102, 13, 'kent', '2016-10-13 12:43:20', 'Y', 3, 0);