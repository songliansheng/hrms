
INSERT INTO USER(username,password,label) VALUES('admin123','{bcrypt}$2a$12$90LubQ.zZaaEv.Y93OSFfOGc3sqC4QBwXW.Xcd25xm4Z8/vj27Jam','管理员')
INSERT INTO role(rolename) VALUES('ROLE_ADMIN')
INSERT INTO role(rolename) VALUES('ROLE_USER')
INSERT INTO user_roles VALUES(1,1)

INSERT INTO dept(dname) VALUES ('技术部')
INSERT INTO dept(dname) VALUES ('产品部')

INSERT INTO job(jname) VALUES('java工程师')

INSERT INTO empl(e_Name,sex,id_Number,education,phone,speciality,address,description) VALUES('张三','男',142115199602114061,'本科',15812345641,'计算机','山西太原','无')
INSERT INTO employee_job VALUES (1,1)

INSERT INTO job_department VALUES (1,1)


