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
		rr.create_time_stamp
	from relief_requests rr
	inner join students s
		on rr.student_id = s.student_id
	inner join relief_tasks rt
		on rt.relief_task_id = rr.relief_task_id;