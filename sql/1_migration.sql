
/** Add request date column to students */
alter table students add column if not exists request_date datetime null;

/** Add quantity_uploaded column to donners_donations */
alter table donners_donations add column if not exists quantity_uploaded boolean not null default false;

/** drop columns that are not needed*/
alter table students drop column if exists is_requesting_relief;
alter table students drop column if exists request_date;

/** Add active column to students 
 *  use for validating newly enroll student account
 * */
alter table students add column if not exists active boolean not null default false;

/** Set default quanity of donation to 0
 * */
alter table donations modify column donations.quantity int not null default 0;

/** add code column to volunteer
 * */
alter table volunteers add column if not exists code varchar(50) not null default '';
	
alter table students add column if not exists user_id int null;

alter table volunteers add column if not exists user_id int null;

alter table students modify column if exists user_id int not null default 0;
alter table volunteers modify column if  exists user_id int not null default 0;
/**set identity_id to have default 0 value*/
alter table users modify column if  exists users.identity_id int not null default 0;
alter table users modify column if  exists users.user_type varchar(250) not null default 'student';
alter table users drop column if  exists users.full_name;
alter table users drop column if  exists users.identity_id;
alter table students drop column if exists  students.active;

ALTER TABLE relief.students ADD CONSTRAINT students_users_fk FOREIGN key if not exists (user_id) REFERENCES relief.users(user_id) ON DELETE CASCADE;
ALTER TABLE relief.relief_requests drop constraint if exists fk_students_student_id;
ALTER TABLE relief.relief_requests ADD constraint relief_requests_students_fk FOREIGN key if not exists (student_id) REFERENCES relief.students(student_id) ON DELETE CASCADE;

alter table relief_requests add column if not exists donation_requests varchar(250) not null default '';