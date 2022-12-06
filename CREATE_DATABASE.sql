/*CREATE DOMAIN TO ATTRIBUTE ROLE*/ 
CREATE DOMAIN dm_role VARCHAR(15)
CHECK (VALUE IN ('ADMINISTRATOR','EMPLOYEE'));

/*CREATE DOMAIN TO ATTRIBUTE FIRST_NAME, LAST_NAME*/
CREATE DOMAIN dm_only_text AS VARCHAR(250)
CHECK (VALUE ~ '^[a-zA-Z]*$');

/*CREATE DOMAIN TO ATTRIBUTE IDENTIFICATION*/
CREATE DOMAIN dm_identification AS VARCHAR(10)
CHECK (VALUE ~ '^\d{10}$');

/*CREATE DOMAIN TO ATTRIBUTE MAIL*/
CREATE DOMAIN dm_mail AS VARCHAR(250)
CHECK (VALUE ~ '^[a-zA-Z0-9.!#$%&''*+/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?(?:\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$');

/*CREATE DOMAIN TO ATTRIBUTE VACCINATION_STATE*/
CREATE DOMAIN dm_vaccination_state AS VARCHAR(250)
CHECK (VALUE IN ('VACCINATED','NO VACCINATED'));

/*CREATE TABLES*/ 
CREATE TABLE ROLE(
	id_role INTEGER NOT NULL,
	role dm_role NOT NULL,
	/*PK*/
	CONSTRAINT pk_role PRIMARY KEY (id_role)
);
 
CREATE TABLE USERS(
	id_users SERIAL NOT NULL,
	identification dm_identification NOT NULL,
	username VARCHAR(30),
	password VARCHAR(30),
	first_name dm_only_text NOT NULL,
	last_name dm_only_text NOT NULL,
	mail dm_mail NOT NULL,
	state VARCHAR(1) NOT NULL,
	/*PK*/
	CONSTRAINT pk_users PRIMARY KEY (id_users)
);


CREATE TABLE USERS_ROLE(
	id_users_role SERIAL NOT NULL,
	id_users INTEGER NOT NULL,
	id_role INTEGER NOT NULL,
	CONSTRAINT pk_users_role PRIMARY KEY
	(id_users_role, id_users, id_role),
	/*FK*/
	CONSTRAINT fk_users
	FOREIGN KEY(id_users) REFERENCES users (id_users),
	CONSTRAINT fk_role
	FOREIGN KEY(id_role) REFERENCES role (id_role)
);
 
CREATE TABLE DATA_EMPLOYEE(
	id_data_employee SERIAL NOT NULL,
	date_birth DATE,
	address VARCHAR(200) ,
	mobile VARCHAR(30),
	vaccination_state dm_vaccination_state,
	id_users INTEGER NOT NULL,
	/*FK*/
	CONSTRAINT fk_users
	FOREIGN KEY(id_users) REFERENCES users (id_users),
	/*PK*/
	CONSTRAINT pk_data_employee PRIMARY KEY (id_data_employee)
);


CREATE TABLE VACCINE(
	id_vaccine INTEGER NOT NULL,
	type VARCHAR(200) NOT NULL,
	/*PK*/
	CONSTRAINT pk_vaccine PRIMARY KEY (id_vaccine)
);

CREATE TABLE VACCINE_DATA(
	id_vaccine_data SERIAL NOT NULL,
	vaccine_date DATE,
	number_dose INTEGER,
	id_vaccine INTEGER,
	id_data_employee INTEGER NOT NULL,
	/*FK*/
	CONSTRAINT fk_vaccine
	FOREIGN KEY(id_vaccine) REFERENCES vaccine (id_vaccine),
	CONSTRAINT fk_data_employee
	FOREIGN KEY(id_data_employee) REFERENCES data_employee (id_data_employee),
	/*PK*/
	CONSTRAINT pk_vaccine_data PRIMARY KEY (id_vaccine_data)
);


/* ADD CONSTRAINT UNIQUE TO IDENTIFICATION INTO TABLE USERS */
 alter table USERS
  add constraint ck_users_identification
  unique (identification);
  

/**INSERTO INTO TABLE ROL, VACCINE AND ONE USER ADMINISTRATOR*/
INSERT INTO role (id_role, role) VALUES (1,'ADMINISTRATOR');
INSERT INTO role (id_role, role) VALUES (2,'EMPLOYEE');

INSERT INTO users (identification,username,password,first_name,last_name,mail,state) 
VALUES ('1234567899','Adm1n1str4t0r','users','Prueba','Prueba','prueba@prueba.com','S'); 


INSERT INTO users_role (id_users,id_role) VALUES (1,1);

INSERT INTO vaccine (id_vaccine, type) VALUES (1,'Sputnik');
INSERT INTO vaccine (id_vaccine, type) VALUES (2,'AstraZeneca');
INSERT INTO vaccine (id_vaccine, type) VALUES (3,'Pfizer');
INSERT INTO vaccine (id_vaccine, type) VALUES (4,'Jhonson&Jhonson');






