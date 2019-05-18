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
         

CREATE TABLE IF NOT EXISTS subject_sj(
    sj_id int AUTO_INCREMENT,
    sj_name VARCHAR(255) NOT NULL,
    sj_d_id int NOT NULL,
    PRIMARY KEY(sj_id),
    FOREIGN KEY (sj_d_id) REFERENCES demonstrator_de(de_id)
) ENGINE = INNODB;

CREATE TABLE IF NOT EXISTS sj_st_attend_at(
    at_id int AUTO_INCREMENT,
    at_sj_id int NOT NULL,
    at_st_id int NOT NULL,
    PRIMARY KEY(at_id),
    FOREIGN KEY (at_sj_id) REFERENCES subject_sj(sj_id),
    FOREIGN KEY (at_st_id) REFERENCES student_st(st_id)
) ENGINE=INNODB;

CREATE TABLE IF NOT EXISTS lecture_le(
    le_id int AUTO_INCREMENT,
    le_sj_id int NOT NULL,
    le_st_id int NOT NULL,
    le_date DATE NOT NULL,
    PRIMARY KEY(le_id),
    FOREIGN KEY (le_sj_id) REFERENCES subject_sj(sj_id),
    FOREIGN KEY (le_st_id) REFERENCES student_st(st_id)
) ENGINE=INNODB;