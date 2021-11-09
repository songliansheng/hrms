--The password below is 123456 after being encrypted
INSERT INTO USER(username,password,label) VALUES('admin123','{bcrypt}$2a$12$7nBBhWeoGoIaEGLm5cHKSO3BJxDZSqTVNmWkzG3b0RLCzSlYOKMeG','管理员')
INSERT INTO role(rolename) VALUES('ROLE_ADMIN')
INSERT INTO role(rolename) VALUES('ROLE_USER')
INSERT INTO user_roles VALUES(1,1)

INSERT INTO dept(dname) VALUES ('技术部')
INSERT INTO dept(dname) VALUES ('产品部')

INSERT INTO job(jname) VALUES('java工程师')

INSERT INTO empl(e_Name,sex,id_Number,education,phone,speciality,address,description) VALUES('张三','男',142115199602114061,'本科',15812345641,'计算机','山西太原','无')
INSERT INTO employee_job VALUES (1,1)

INSERT INTO job_department VALUES (1,1)


