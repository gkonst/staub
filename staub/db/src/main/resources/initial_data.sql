SET CLIENT_ENCODING TO 'UNICODE';
INSERT INTO role (id, description) VALUES (0, 'Administrator');
INSERT INTO role (id, description) VALUES (1, 'Test Writer');
INSERT INTO "user" (id,
                    fk_role,
                    username,
                    password)
     VALUES (nextval('seq_user'),
             0,
             'admin',
             'd033e22ae348aeb5660fc2140aec35850c4da997');

INSERT INTO difficulty (id,
                        name,
                        code)
     VALUES (nextval('seq_difficulty'),
             'Низкая сложность',
             0);
INSERT INTO difficulty (id,
                        name,
                        code)
     VALUES (nextval('seq_difficulty'),
             'Средняя сложность',
             1);
INSERT INTO difficulty (id,
                        name,
                        code)
     VALUES (nextval('seq_difficulty'),
             'Высокая сложность',
             2);
             
INSERT INTO discipline (id,
                        name,
                        code)
     VALUES (nextval('seq_discipline'),
             'Математика',
             'ЕН.Ф.1');
INSERT INTO discipline (id,
                        name,
                        code)
     VALUES (nextval('seq_discipline'),
             'Информатика',
             'ЕН.Ф.2');
INSERT INTO discipline (id,
                        name,
                        code)
     VALUES (nextval('seq_discipline'),
             'Физика',
             'ЕН.Ф.3');
INSERT INTO discipline (id,
                        name,
                        code)
     VALUES (nextval('seq_discipline'),
             'Вычислительная математика',
             'ЕН.Ф.6');
INSERT INTO discipline (id,
                        name,
                        code)
     VALUES (nextval('seq_discipline'),
             'Математическая физика',
             'ЕН.Р.01');

INSERT INTO "group" (id,
                    name)
     VALUES (nextval('seq_group'),
             '1082/1');
INSERT INTO "group" (id,
                    name)
     VALUES (nextval('seq_group'),
             '1082/2');
INSERT INTO "group" (id,
                    name)
     VALUES (nextval('seq_group'),
             '1082/3');
INSERT INTO "group" (id,
                    name)
     VALUES (nextval('seq_group'),
             '2082/1');
INSERT INTO "group" (id,
                    name)
     VALUES (nextval('seq_group'),
             '2082/2');
INSERT INTO "group" (id,
                    name)
     VALUES (nextval('seq_group'),
             '2082/3');
INSERT INTO "group" (id,
                    name)
     VALUES (nextval('seq_group'),
             '3082/1');
INSERT INTO "group" (id,
                    name)
     VALUES (nextval('seq_group'),
             '3082/2');
INSERT INTO "group" (id,
                    name)
     VALUES (nextval('seq_group'),
             '3082/3');
INSERT INTO "group" (id,
                    name)
     VALUES (nextval('seq_group'),
             '4082/1');
INSERT INTO "group" (id,
                    name)
     VALUES (nextval('seq_group'),
             '4082/2');
INSERT INTO "group" (id,
                    name)
     VALUES (nextval('seq_group'),
             '4082/3');
INSERT INTO "group" (id,
                    name)
     VALUES (nextval('seq_group'),
             '5082/1');
INSERT INTO "group" (id,
                    name)
     VALUES (nextval('seq_group'),
             '5082/2');
INSERT INTO "group" (id,
                    name)
     VALUES (nextval('seq_group'),
             '5082/3');
INSERT INTO "group" (id,
                    name)
     VALUES (nextval('seq_group'),
             '6082/1');
INSERT INTO "group" (id,
                    name)
     VALUES (nextval('seq_group'),
             '6082/2');
INSERT INTO "group" (id,
                    name)
     VALUES (nextval('seq_group'),
             '6082/3');
