CREATE TABLE `donations` (
  `donation_id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(250) NOT NULL,
  `quantity` int(11) NOT NULL DEFAULT 0,
  `create_time_stamp` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`donation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;


CREATE TABLE `donners` (
  `donner_id` int(11) NOT NULL AUTO_INCREMENT,
  `full_name` varchar(250) NOT NULL,
  `address` varchar(250) NOT NULL,
  `contact_number` varchar(50) NOT NULL,
  `create_time_stamp` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`donner_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `users` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(250) NOT NULL,
  `password` varchar(250) NOT NULL,
  `user_type` varchar(250) NOT NULL DEFAULT 'student',
  `active` tinyint(1) NOT NULL DEFAULT 0,
  `create_time_stamp` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `employees` (
  `employee_id` int(11) NOT NULL AUTO_INCREMENT,
  `full_name` varchar(250) NOT NULL,
  `address` varchar(250) NOT NULL,
  `contact_number` varchar(250) NOT NULL,
  `create_time_stamp` datetime NOT NULL DEFAULT current_timestamp(),
  `position` varchar(250) NOT NULL DEFAULT '',
  `user_id` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`employee_id`),
  KEY `employees_users_fk` (`user_id`),
  CONSTRAINT `employees_users_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `relief_tasks` (
  `relief_task_id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(250) NOT NULL,
  `title` varchar(250) NOT NULL,
  `affected_areas` varchar(250) NOT NULL,
  `active` tinyint(1) NOT NULL,
  `create_time_stamp` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`relief_task_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `students` (
  `student_id` int(11) NOT NULL AUTO_INCREMENT,
  `sr_code` varchar(50) NOT NULL,
  `full_name` varchar(250) NOT NULL,
  `address` varchar(250) NOT NULL,
  `course` varchar(250) NOT NULL,
  `contact_number` varchar(250) NOT NULL,
  `create_time_stamp` datetime NOT NULL DEFAULT current_timestamp(),
  `campus` varchar(250) NOT NULL DEFAULT '',
  `user_id` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`student_id`),
  KEY `students_users_fk` (`user_id`),
  CONSTRAINT `students_users_fk` FOREIGN KEY (`user_id`) REFERENCES `users` (`user_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `relief_requests` (
  `relief_request_id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) NOT NULL,
  `relief_task_id` int(11) NOT NULL,
  `released` tinyint(1) NOT NULL,
  `date_release` datetime NOT NULL DEFAULT current_timestamp(),
  `create_time_stamp` datetime NOT NULL DEFAULT current_timestamp(),
  `donation_requests` varchar(250) NOT NULL DEFAULT '',
  PRIMARY KEY (`relief_request_id`),
  KEY `fk_students_student_id` (`student_id`),
  KEY `fk_relief_tasks_relief_task_id` (`relief_task_id`),
  CONSTRAINT `fk_relief_tasks_relief_task_id` FOREIGN KEY (`relief_task_id`) REFERENCES `relief_tasks` (`relief_task_id`),
  CONSTRAINT `relief_requests_students_fk` FOREIGN KEY (`student_id`) REFERENCES `students` (`student_id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;








CREATE TABLE `volunteers` (
  `volunteer_id` int(11) NOT NULL AUTO_INCREMENT,
  `full_name` varchar(250) NOT NULL,
  `address` varchar(250) NOT NULL,
  `contact_number` varchar(50) NOT NULL,
  `create_time_stamp` datetime NOT NULL DEFAULT current_timestamp(),
  `code` varchar(50) NOT NULL DEFAULT '',
  `user_id` int(11) NOT NULL DEFAULT 0,
  PRIMARY KEY (`volunteer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;


CREATE TABLE `donners_donations` (
  `donners_donations_id` int(11) NOT NULL AUTO_INCREMENT,
  `donation_id` int(11) NOT NULL,
  `donner_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL DEFAULT 0,
  `donation_date` datetime NOT NULL DEFAULT current_timestamp(),
  `create_time_stamp` datetime NOT NULL DEFAULT current_timestamp(),
  `quantity_uploaded` tinyint(1) NOT NULL DEFAULT 0,
  PRIMARY KEY (`donners_donations_id`),
  KEY `fk_donations_donation_id` (`donation_id`),
  KEY `fk_donners_donner_id` (`donner_id`),
  CONSTRAINT `fk_donations_donation_id` FOREIGN KEY (`donation_id`) REFERENCES `donations` (`donation_id`),
  CONSTRAINT `fk_donners_donner_id` FOREIGN KEY (`donner_id`) REFERENCES `donners` (`donner_id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;


CREATE TABLE `relief_request_donations` (
  `relief_request_donation_id` int(11) NOT NULL AUTO_INCREMENT,
  `relief_request_id` int(11) NOT NULL,
  `donation_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL DEFAULT 0,
  `create_time_stamp` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`relief_request_donation_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4;



/** not table */
CREATE TABLE `student_reliefs` (
  `student_relief_id` int(11) NOT NULL AUTO_INCREMENT,
  `student_id` int(11) NOT NULL,
  `donation_id` int(11) NOT NULL,
  `quantity` int(11) NOT NULL DEFAULT 0,
  `is_release` tinyint(1) NOT NULL,
  `date_release` datetime NOT NULL DEFAULT current_timestamp(),
  `request_date` datetime NOT NULL DEFAULT current_timestamp(),
  `code` varchar(50) NOT NULL DEFAULT '',
  `create_time_stamp` datetime NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`student_relief_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
