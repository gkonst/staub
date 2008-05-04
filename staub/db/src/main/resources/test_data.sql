INSERT INTO role (id, description) VALUES (0, 'Administrator');
INSERT INTO role (id, description) VALUES (1, 'Test Writer');

INSERT INTO "user" (id,
                    fk_role,
                    username,
                    password)
     VALUES (nextval('seq_user'),
             0,
             'admin',
             'admin');

INSERT INTO "user" (id,
                    fk_role,
                    username,
                    password)
     VALUES (nextval('seq_user'),
             1,
             'test',
             'test');

INSERT INTO "group" (id,
                    name)
     VALUES (nextval('seq_group'),
             '6082/1');

INSERT INTO student (id,
                     fk_group,
                     name,
                     code,
                     active)
     VALUES (nextval('seq_student'),
             currval('seq_group'),
             'Иванов Иван Иванович',
             '87654351',
             true);

INSERT INTO student (id,
                     fk_group,
                     name,
                     code,
                     active)
     VALUES (nextval('seq_student'),
             currval('seq_group'),
             'Петров Петр Петрович',
             '87654352',
             true);

INSERT INTO "group" (id,
                    name)
     VALUES (nextval('seq_group'),
             '6082/2');

INSERT INTO student (id,
                     fk_group,
                     name,
                     code,
                     active)
     VALUES (nextval('seq_student'),
             currval('seq_group'),
             'Сидоров Сидор Сидорович',
             '87654353',
             true);

INSERT INTO discipline (id,
                        name,
                        code)
     VALUES (nextval('seq_discipline'), 
             'Математика',
             'D0');

INSERT INTO category (id,
                      fk_discipline,
                      name,
                      code)
     VALUES (nextval('seq_category'), 
	     currval('seq_discipline'),
             'Дифференциальные уравнения',
             'D1');

INSERT INTO test (id,
                  name,
                  description,
                  time_limit,
                  fk_category,
                  active,
                  created,
                  created_by)
     VALUES (nextval('seq_test'),
             'Simple test',
             'There is nothing easier, than this test.',
             50,
             currval('seq_category'),
             true,
             current_date,
             'Generation Script');

INSERT INTO assignment (id,
                        fk_student,
                        fk_test)
     VALUES (nextval('seq_assignment'),
             currval('seq_student'),
             currval('seq_test'));

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
             'Стандартная сложность',
             1);

INSERT INTO test_difficulty (fk_test,
                             fk_difficulty,
                             questions_count,
                             pass_score)
     VALUES (currval('seq_test'),
             currval('seq_difficulty'),
             5,
             100);

INSERT INTO topic (id,
                   fk_category,
                   name,
                   code)
     VALUES (nextval('seq_topic'), 
	     currval('seq_category'),
             'Линейные дифференциальные уравнения',
             'T0');

INSERT INTO question (id,
                      fk_topic,
                      fk_difficulty,
                      name,
                      definition,
                      active,
                      created,
                      created_by)
     VALUES (nextval('seq_question'), 
             currval('seq_topic'),
             currval('seq_difficulty'),
             'Question #1',
             '<?xml version="1.0" encoding="UTF-8"?><question xmlns="http://staub.spbspu.ru/Question"><description>How many fingers does the average homo sapiens have? (Choose one.)</description><single-choice><answer id="1" correct="true">5</answer><answer id="2">10</answer><answer id="3">15</answer><answer id="4">20</answer></single-choice></question>',
             true,
             current_date,
             'Generation Script');

INSERT INTO difficulty (id,
                        name,
                        code)
     VALUES (nextval('seq_difficulty'),
             'Высокая сложность',
             2);

INSERT INTO test_difficulty (fk_test,
                             fk_difficulty,
                             questions_count,
                             pass_score)
     VALUES (currval('seq_test'),
             currval('seq_difficulty'),
             10,
             75);

INSERT INTO topic (id,
                   fk_category,
                   name,
                   code)
     VALUES (nextval('seq_topic'), 
	     currval('seq_category'),
             'Системы дифференциальных уравнений',
             'T1');

INSERT INTO question (id,
                      fk_topic,
                      fk_difficulty,
                      name,
                      definition,
                      active,
                      created,
                      created_by)
     VALUES (nextval('seq_question'), 
             currval('seq_topic'),
             currval('seq_difficulty'),
             'Question #2',
             '<?xml version="1.0" encoding="UTF-8"?><ques:question xmlns:ques="http://staub.spbspu.ru/Question"><ques:description>How many days are in February? (Choose multiple.)</ques:description><ques:multiple-choice><ques:answer id="1" correct="true">28</ques:answer><ques:answer id="2" correct="true">29</ques:answer><ques:answer id="3">30</ques:answer><ques:answer id="4">31</ques:answer></ques:multiple-choice></ques:question>',
             true,
             current_date,
             'Generation Script');
