CREATE DATABASE project_test;
USE project_test;



CREATE TABLE IF NOT EXISTS student_st(
     st_id INT AUTO_INCREMENT,
     st_fn VARCHAR(255) NOT NULL,
     st_mn VARCHAR(255) NOT NULL,
     st_ln VARCHAR(255) NOT NULL,
     st_neptun VARCHAR(255) NOT NULL,
	 PRIMARY KEY (st_id) )  ENGINE=INNODB;



CREATE TABLE IF NOT EXISTS st_auth_a(
    a_id int NOT NULL  AUTO_INCREMENT,
    a_st_id int NOT NULL,
	a_un VARCHAR(255) NOT NULL,
	a_pwd VARCHAR(255) NOT NULL,
    PRIMARY KEY (a_id),
    FOREIGN KEY (a_st_id) REFERENCES student_st(st_id)
)  ENGINE=INNODB; 



CREATE TABLE IF NOT EXISTS demonstrator_de(
     de_id INT AUTO_INCREMENT,
     de_fn VARCHAR(255) NOT NULL,
     de_mn VARCHAR(255) NOT NULL,
     de_ln VARCHAR(255) NOT NULL,
	 PRIMARY KEY (de_id) )  ENGINE=INNODB;
	 
CREATE TABLE IF NOT EXISTS de_auth_a(
    a_id int NOT NULL  AUTO_INCREMENT,
    a_de_id int NOT NULL,
	a_un VARCHAR(255) NOT NULL,
	a_pwd VARCHAR(255) NOT NULL,
    PRIMARY KEY (a_id),
    FOREIGN KEY (a_de_id) REFERENCES demonstrator_de(de_id)
)  ENGINE=INNODB; 