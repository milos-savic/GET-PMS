INSERT INTO project (id, code, name, description, project_manager, version) VALUES (1, 'x2020', 'Treadstone', 'Treadstone71 top-secret black-ops', 11, 0);
INSERT INTO project (id, code, name, description, project_manager, version) VALUES (2, 'y2020', 'Wayne Biotech', 'Gotham healthcare system', 13, 0);
INSERT INTO project (id, code, name, description, project_manager, version) VALUES (3, 'z2020', 'The Lizard', 'Dr. Connors alter-ego', 14, 0);

INSERT INTO task(id, name, task_status, progress, deadline, description, assignee, project, version) VALUES (10, 'Operation1', 'NEW', 0, parsedatetime('17-09-2018', 'dd-MM-yyyy'), 'Operation 1', 15, 1, 0);
INSERT INTO task(id, name, task_status, progress, deadline, description, assignee, project, version) VALUES (11, 'Operation2', 'IN_PROGRESS', 65, parsedatetime('17-09-2017', 'dd-MM-yyyy'), 'Operation 2', 16, 1, 0);
INSERT INTO task(id, name, task_status, progress, deadline, description, assignee, project, version) VALUES (12, 'Operation3', 'FINISHED', 100, parsedatetime('17-09-2016', 'dd-MM-yyyy'), 'Operation 3', 16, 1, 0);

INSERT INTO task(id, name, task_status, progress, deadline, description, assignee, project, version) VALUES (20, 'Task1', 'NEW', 0, parsedatetime('17-09-2018', 'dd-MM-yyyy'), 'Task 1', 12, 2, 0);
INSERT INTO task(id, name, task_status, progress, deadline, description, assignee, project, version) VALUES (21, 'Task2', 'IN_PROGRESS', 65, parsedatetime('17-09-2017', 'dd-MM-yyyy'), 'Task 2', 11, 2, 0);
INSERT INTO task(id, name, task_status, progress, deadline, description, assignee, project, version) VALUES (22, 'Task3', 'FINISHED', 100, parsedatetime('17-09-2016', 'dd-MM-yyyy'), 'Task 3', 14, 2, 0);

INSERT INTO task(id, name, task_status, progress, deadline, description, assignee, project, version) VALUES (30, 'Task1', 'NEW', 0, parsedatetime('17-09-2018', 'dd-MM-yyyy'), 'Task 1', 12, 3, 0);
INSERT INTO task(id, name, task_status, progress, deadline, description, assignee, project, version) VALUES (31, 'Task2', 'IN_PROGRESS', 65, parsedatetime('17-09-2017', 'dd-MM-yyyy'), 'Task 2', 11, 3, 0);
INSERT INTO task(id, name, task_status, progress, deadline, description, assignee, project, version) VALUES (32, 'Task3', 'FINISHED', 100, parsedatetime('17-09-2016', 'dd-MM-yyyy'), 'Task 3', 14, 3, 0);