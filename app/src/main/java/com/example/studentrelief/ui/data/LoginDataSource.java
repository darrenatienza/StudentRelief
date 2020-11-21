package com.example.studentrelief.ui.data;

import com.example.studentrelief.services.interfaces.StudentClient;
import com.example.studentrelief.ui.data.model.LoggedInUser;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EBean;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.rest.spring.annotations.RestService;

import java.io.IOException;

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
@EBean
public class LoginDataSource {

    @RestService
    StudentClient studentClient;
    private String student;
    private LoggedInUser fakeUser;


    public Result<LoggedInUser> login(String username, String password) {

        try {
            // TODO: handle loggedInUser authentication
            getUser();

            return new Result.Success<>(fakeUser);
        } catch (Exception e) {
            return new Result.Error(new IOException("Error logging in", e));
        }
    }

    @Background
    void getUser(){
        student = studentClient.get(2).getSr_code();
        validate(student);
    }
    @UiThread(delay = 5000)
   void validate(String student) {
        fakeUser =
                new LoggedInUser(
                        java.util.UUID.randomUUID().toString(),
                        student);
    }


    public void logout() {
        // TODO: revoke authentication
    }
}