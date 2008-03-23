INSERT INTO role (id, description) VALUES (0, 'Administrator');
INSERT INTO role (id, description) VALUES (1, 'Test Writer');
INSERT INTO role (id, description) VALUES (2, 'User');

INSERT INTO "user" (id,
                    fk_role,
                    username,
                    password)
     VALUES (nextval('seq_user'),
             0,
             'test',
             'test');

INSERT INTO "user" (id,
                    fk_role,
                    username,
                    password)
     VALUES (nextval('seq_user'),
             2,
             'user',
             'user');

INSERT INTO test (id,
                  name,
                  description,
                  time_limit,
                  pass_score,
                  questions_count,
                  selector_type,
                  created,
                  created_by)
     VALUES (nextval('seq_test'),
             'Simple test',
             'There is nothing easier, than this test.',
             5,
             50,
             2,
             0,
             current_date,
             'Generation Script');
             
INSERT INTO assignment (id,
                        fk_user,
                        fk_test,
                        test_begin,
                        test_end)
     VALUES (nextval('seq_assignment'),
             currval('seq_user'),
             currval('seq_test'),
             'now',
             'tomorrow');

INSERT INTO discipline (id,
                        name,
                        code)
     VALUES (nextval('seq_discipline'), 
             'Various',
             12);

INSERT INTO category (id,
                      name,
                      code)
     VALUES (nextval('seq_category'), 
             'Misc',
             34);

INSERT INTO difficulty (id,
                        name,
                        code)
     VALUES (nextval('seq_difficulty'), 
             'Very difficult',
             56);

INSERT INTO question (id,
                      fk_discipline,
                      fk_category,
                      fk_difficulty,
                      name,
                      definition,
                      created,
                      created_by)
     VALUES (nextval('seq_question'), 
             currval('seq_discipline'),
             currval('seq_category'),
             currval('seq_difficulty'),
             'Question #1',
             '<?xml version="1.0" encoding="UTF-8"?><question xmlns="http://staub.spbspu.ru/Question"><description>How many fingers does the average homo sapiens have? (Choose one.)</description><single-choice><answer id="1" correct="true">5</answer><answer id="2">10</answer><answer id="3">15</answer><answer id="4">20</answer></single-choice></question>',
             current_date,
             'Generation Script');

INSERT INTO test_question (fk_test,
                           fk_question)
     VALUES (currval('seq_test'),
             currval('seq_question'));

INSERT INTO question (id,
                      fk_discipline,
                      fk_category,
                      fk_difficulty,
                      name,
                      definition,
                      created,
                      created_by)
     VALUES (nextval('seq_question'), 
             currval('seq_discipline'),
             currval('seq_category'),
             currval('seq_difficulty'),
             'Question #2',
             '<?xml version="1.0" encoding="UTF-8"?><ques:question xmlns:ques="http://staub.spbspu.ru/Question"><ques:description>How many days are in February? (Choose multiple.)</ques:description><ques:multiple-choice><ques:answer id="1" correct="true">28</ques:answer><ques:answer id="2" correct="true">29</ques:answer><ques:answer id="3">30</ques:answer><ques:answer id="4">31</ques:answer></ques:multiple-choice></ques:question>',
             current_date,
             'Generation Script');

INSERT INTO test_question (fk_test,
                           fk_question)
     VALUES (currval('seq_test'),
             currval('seq_question'));
