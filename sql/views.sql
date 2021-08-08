/** view for volunteer list with user active status */
create or replace view employee_list_view as
	select
	e.employee_id,
	e.address,
	e.contact_number,
	e.full_name,
	e.`position`,
	u.active,
	u.user_id
	from employees e
	inner join users u
	on e.user_id = u.user_id
	;
	
/** view for dashboard */
create or replace view view_dashboard as
	select
	(select count(student_id) from students) student_count,
	(select count(donner_id) from donners) donner_count,
	(select count(employee_id) from employees) employee_count,
	(select count(relief_request_id) from relief_requests) relief_request_count,
	(select count(relief_task_id) from relief_tasks) relief_task_count,
	(select count(user_id) from users) user_count
	;
	

	
/** view for student list with user active status*/
create or replace view  students_view as
	select 
		s.student_id,
		s.sr_code,
		s.full_name,
		s.address,
		s.course,
		s.contact_number,
		s.campus,
		u.active
	from students s
	inner join users u
		on s.user_id = u.user_id;
		
/** view for volunteer list with user active status */
create or replace view volunteer_list_view as
	select
	v.volunteer_id,
	v.address,
	v.code,
	v.contact_number,
	v.full_name,
	u.active,
	u.user_id
	from volunteers v
	inner join users u
	on v.user_id = u.user_id;
	
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


/** View for Donners donation */
create or replace view  donners_donations_view
	as
		select 
			dd.donners_donations_id,
			dd.donation_date,
			dd.donation_id,
			dd.donner_id,
			dd.create_time_stamp,
			dd.quantity,
			dnr.full_name donner_full_name,
			dnt.name donation_name,
			dd.quantity_uploaded
		from 
			donners_donations dd 
		inner join donations dnt on dd.donation_id = dnt.donation_id
		inner join donners dnr on dd.donner_id = dnr.donner_id;
		
		
		/** view for relief_requests_view*/
create or replace view  relief_requests_view as
	select 
		rr.relief_request_id,
		s.student_id,
		s.full_name student_full_name,
		s.address student_address,
		s.contact_number student_contact_number,
		s.campus student_campus,
		s.course student_course,
		rr.relief_task_id,
		rt.title request_task_title,
		rr.released,
		rr.date_release,
		rr.create_time_stamp,
		rr.donation_requests,
		rr.followup
	from relief_requests rr
	inner join students s
		on rr.student_id = s.student_id
	inner join relief_tasks rt
		on rt.relief_task_id = rr.relief_task_id;
		
create or replace view view_student_active_relief_request_count as
	select 
		student_id,
		released,
		/**counts the number of active relief request by the student*/
		(
			select count(rr2.relief_request_id) 
			from relief_requests rr2 
	 		where rr1.student_id =  rr2.student_id
	 		and rr1.released = rr2.released
	 	) 
	 	as relief_request_count 
	from
	relief_requests rr1;
	
create or replace view view_relief_task_list as
	select
		rt.relief_task_id,
		rt.code,
		rt.active,
		rt.affected_areas,
		rt.title,
		(
			select
				count(rr.relief_request_id)
			from
				relief_requests rr
			where
				rr.relief_task_id = rt.relief_task_id and
				rr.released = 0
		) as not_released,
		(
			select
				count(rr.relief_request_id)
			from
				relief_requests rr
			where
				rr.relief_task_id = rt.relief_task_id and
				rr.followup = 0
		) as followup_count
		
	from 
		relief_tasks rt;
	
	
	