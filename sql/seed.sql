/**Default admin user*/
INSERT INTO relief.users (user_id, username, password, user_type, active, create_time_stamp) 
VALUES(1,'admin', 'admin', 'admin' , 1, current_timestamp()) ON DUPLICATE KEY UPDATE    
username='admin', password='$2y$10$6W8O2Lxsv0U59/IqhdHkwurTgqNZw1ODbhIYweQSVWEmg0KgJm6sW', user_type='admin', active=1;

/**Default admin employee user */
INSERT INTO relief.employees
(employee_id, full_name, address, contact_number, create_time_stamp, `position`, user_id)
VALUES(1,'admin', '', '', current_timestamp(), 'admin', 1)on duplicate key update
full_name= 'admin', address='', contact_number='',`position`='admin', user_id=1;


/**Test student user*/
INSERT INTO relief.users 
	(user_id, username, password, user_type, active, create_time_stamp) 
values
	(2,'s', '$2y$10$K2tEQxN5vPDLRyL3g.7TsOgjjcKRPkQXrIklsh7Pk8LYQ5g/Zc3RW', 'student' , 1, current_timestamp()) 
ON DUPLICATE KEY UPDATE    
	username='1', 
	password='$2y$10$292lUQ.wuAFaeAHUv28.GugtA9wBKFyHJrzciA/dOPMvTFk.JsJKO', 
	user_type='student', 
	active=1;
	
/**Test student*/
INSERT INTO relief.students
	(student_id, sr_code, full_name, address, course, contact_number, create_time_stamp, campus, user_id)
values
	(1,'1', 's', '', '', '', current_timestamp(), '', 2)
ON DUPLICATE KEY update
	sr_code = '1', 
	full_name = 'Juan Tamad', 
	address = 'Batangas', 
	course = 'BSIT', 
	contact_number ='09501743177',  
	campus = 'Main', 
	user_id = 2;

	
	/**Test Donations*/
INSERT INTO relief.donations
	(donation_id,name, quantity, create_time_stamp)
VALUES(1,'Food', 1000, current_timestamp())
ON DUPLICATE KEY update
	name='Food', quantity=1000;
	
INSERT INTO relief.donations
	(donation_id,name, quantity, create_time_stamp)
VALUES(2,'Dress', 1000, current_timestamp())
ON DUPLICATE KEY update
	name='Dress', quantity=1000;
	
	

	/**Test volunter user*/
INSERT INTO relief.users 
	(user_id, username, password, user_type, active, create_time_stamp) 
values
	(3,'v', '$2y$10$mn4Ba.euyb6qdlnSaZz1MeQlwT/tEeCae9fPdZxsLLwODOuiV/9vG', 'volunteer' , 1, current_timestamp()) 
ON DUPLICATE KEY UPDATE    
	username='1', 
	password='$2y$10$mn4Ba.euyb6qdlnSaZz1MeQlwT/tEeCae9fPdZxsLLwODOuiV/9vG', 
	user_type='volunteer', 
	active=1;
	
INSERT INTO relief.volunteers
	(volunteer_id,full_name, address, contact_number, create_time_stamp, code, user_id)
VALUES(1,'v', '', '', current_timestamp(), '', 3);

