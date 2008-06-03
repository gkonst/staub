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