ALTER TABLE volunteers ADD constraint volunteers_users_fk FOREIGN KEY if not exists (user_id)  REFERENCES users(user_id) ON DELETE CASCADE;
