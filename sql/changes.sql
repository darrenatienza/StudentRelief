/**05-20-2021*/
ALTER TABLE relief.relief_requests ADD next_follow_up_date DATETIME NULL;
ALTER TABLE relief.relief_requests ADD isfollowup BOOL DEFAULT 0 NULL;
ALTER TABLE relief.relief_requests CHANGE isfollowup is_followup tinyint(1) DEFAULT 0 NULL;
ALTER TABLE relief.relief_requests CHANGE isfollowup followup tinyint(1) DEFAULT 0 NULL;


