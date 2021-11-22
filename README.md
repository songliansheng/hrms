## About   
This project is the back-end part of my self-study project - hrms - a simple CRUD web application with front-end and back-end separated , for the purpose of learning spring framework and vue.js.  the front-end is [here](https://github.com/songliansheng/hrms-front-end).  

This isn't a step by step tutorial , this is source code for my personal use. 

After the [front-end](https://github.com/songliansheng/hrms-front-end) part is bulit , copy the created files into folder src/main/resources/static ，then this project is complete.You can run it next step.

## Run the Application
This project can be run either directly from an IDE , or as a packaged application . Since this project uses maven as build tool ，runing command below in a terminal window will package this project into a JAR file .
```bash
mvn package
```

To run it as a packaged application , execute java command below in a command window(Note : jar-file-name.jar here is the name of the packaged project). 
```bash
java -jar jar-file-name.jar
```

In order to facilitate displaying this project , data.sql file is created in folder "src/main/resources" and it will be automatically executed on startup to initialize the database .

 
