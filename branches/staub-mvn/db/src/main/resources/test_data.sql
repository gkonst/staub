INSERT INTO "user" (id,
                    username,
                    password)
     VALUES (nextval('seq_user'),
             'test',
             'test');

INSERT INTO "user" (id,
                    username,
                    password)
     VALUES (nextval('seq_user'),
             'user',
             'user');

INSERT INTO test (id,
                  name,
                  description,
                  time_limit,
                  pass_score,
                  questions_count)
     VALUES (nextval('seq_test'),
             'Simple test',
             'There is nothing easier, than this test.',
             5,
             50,
             2);

INSERT INTO question (id,
                      fk_test,
                      name,
                      definition)
     VALUES (nextval('seq_question'), 
             currval('seq_test'),
             'Question #1',
             '<?xml version="1.0" encoding="UTF-8"?><question xmlns="http://staub.spbspu.ru/Question"><description>How many fingers does the average homo sapiens have? (Choose one.)</description><single-choice><answer correct="true">5</answer><answer>10</answer><answer>15</answer><answer>20</answer></single-choice></question>');

INSERT INTO question (id,
                      fk_test,
                      name,
                      definition)
     VALUES (nextval('seq_question'), 
             currval('seq_test'),
             'Question #2',
             '<?xml version="1.0" encoding="UTF-8"?><ques:question xmlns:ques="http://staub.spbspu.ru/Question"><ques:description>How many days are in February? (Choose multiple.)</ques:description><ques:multiple-choice><ques:answer correct="string">28</ques:answer><ques:answer correct="string">29</ques:answer><ques:answer>30</ques:answer><ques:answe>31</ques:answer></ques:multiple-choice></ques:question>');