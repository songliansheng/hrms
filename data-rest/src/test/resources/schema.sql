Drop TABLE  IF EXISTS employee;

CREATE TABLE employee
(
    id         INT AUTO_INCREMENT PRIMARY KEY,
    eName      VARCHAR(250) NOT NULL,
    sex        VARCHAR(250),
    idNumber   VARCHAR(250) NOT NULL ,
    education  VARCHAR(250),
    phone      INT,
    department VARCHAR(250),
    job        VARCHAR(250),
    speciality VARCHAR(250),
    address    VARCHAR(250),
    notes      VARCHAR(250)


)
