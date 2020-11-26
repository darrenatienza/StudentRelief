

/** Add request date column to students */
alter table students add column if not exists request_date datetime null;


/** Add quantity_uploaded column to donners_donations */
alter table donners_donations add column if not exists quantity_uploaded boolean not null default false;

/**add donation task table where will be the basis of all request by the student */
create table if not exists relief_tasks (
	relief_task_id int not null auto_increment,
	code varchar(250) not null,
	title varchar(250) not null,
	affected_areas varchar(250) not null,
	active boolean not null,
	create_time_stamp datetime not null default current_timestamp(),
	primary key (relief_task_id)
);

/** student request base on relief task */
create table if not exists relief_requests (
	relief_request_id int not null auto_increment,
	student_id int not null,
	relief_task_id int not null,
	released tinyint(1) NOT NULL,
	date_release datetime NOT NULL DEFAULT current_timestamp(),
	create_time_stamp datetime not null default current_timestamp(),
	primary key (relief_request_id),
	KEY `fk_students_student_id` (`student_id`),
  KEY `fk_relief_tasks_relief_task_id` (`relief_task_id`),
  CONSTRAINT `fk_students_student_id` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`),
  CONSTRAINT `fk_relief_tasks_relief_task_id` FOREIGN KEY (`relief_task_id`) REFERENCES `relief_tasks` (`relief_task_id`)
);


/** donations base on relief request */
CREATE table if not exists  `relief_request_donations` (
  `relief_request_donation_id` int(11) NOT NULL AUTO_INCREMENT,
  `relief_request_id` int(11) NOT NULL,
  `donation_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL DEFAULT 0,
  `create_time_stamp` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`relief_request_donation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/** drop columns that are not needed*/
alter table students drop column if exists is_requesting_relief;
alter table students drop column if exists request_date;


/** view for relief_requests_view*/
create or replace view  relief_request_donation_view as
	select 
		rrd.relief_request_donation_id,
		rrd.create_time_stamp,
		d.name donation_name,
		rrd.donation_id,
		rrd.quantity,
		rrd.relief_request_id
	from relief_request_donations rrd
	inner join donations d
		on d.donation_id = rrd.donation_id;

create table if not exists users(
	user_id int not null auto_increment,
	username varchar(250) not null,
	password VARCHAR(250) not null,
	user_type varchar(250) not null,
	full_name varchar(250) not null,
	identity_id int not null default 0,
	active boolean not null default false,
	create_time_stamp datetime default  current_timestamp(),
	primary key(user_id)
);

/** Add active column to students 
 *  use for validating newly enroll student account
 * */
alter table students add column if not exists active boolean not null default false;
