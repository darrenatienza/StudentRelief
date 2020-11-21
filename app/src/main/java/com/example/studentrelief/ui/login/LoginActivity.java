package com.example.studentrelief.ui.login;

import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.LoginClient;
import com.example.studentrelief.services.interfaces.StudentClient;
import com.example.studentrelief.services.interfaces.UserClient;
import com.example.studentrelief.services.interfaces.VolunteerClient;
import com.example.studentrelief.services.model.LoginModel;
import com.example.studentrelief.services.model.UserModel;
import com.example.studentrelief.services.model.VolunteerModel;
import com.example.studentrelief.ui.student.StudentPanelActivity_;
import com.example.studentrelief.ui.volunteer.VolunteerPanelActivity_;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EditorAction;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.web.client.RestClientException;

import cn.pedant.SweetAlert.SweetAlertDialog;

@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity {

    @ViewById
    EditText username;
    @ViewById
    EditText password;
    @ViewById(R.id.login)
    Button loginButton;
    @ViewById(R.id.loading)
    ProgressBar loadingProgressBar;
    @RestService
    UserClient userClient;
    @RestService
    LoginClient loginClient;
    @RestService
    StudentClient studentClient;
    @RestService
    VolunteerClient volunteerClient;
    @AfterViews
    void afterViews(){


    }
    @Click(R.id.login)
    void onLogin(){
        loadingProgressBar.setVisibility(View.VISIBLE);
        String userName = username.getText().toString();
        String password = this.password.getText().toString();
        if(userName == "" || password == ""){
            Toast.makeText(this,"Invalid UserName or Password",Toast.LENGTH_SHORT).show();
        }else{
            loginUser(userName,password);
        }

    }
    @Background
    void loginUser(String userName, String password) {
        LoginModel model = new LoginModel();
        model.setUsername(userName);
        model.setPassword(password);
        try{
            UserModel logUser = loginClient.login(model);
            onLoginSuccess(logUser);
        }catch (RestClientException e){
            String message = e.getMessage();
           if(message.contains("403 Forbidden")){
               onAuthenticationFailure();
           }else{
               onError(message);
           }

        }
    }
    @UiThread
    void onAuthenticationFailure() {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Error?")
                .setContentText("Invalid user name or password!")
                .setConfirmText("Ok")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {

                        sDialog.dismissWithAnimation();
                    }
                })

                .show();
        loadingProgressBar.setVisibility(View.INVISIBLE);
    }

    @UiThread
    void onLoginSuccess(UserModel logUser) {
        String userType = logUser.getUser_type();
        if(userType.contains("student")){
            StudentPanelActivity_.intent(this).id(logUser.getIdentity_id()).start();
        }else if (userType.contains("volunteer")){
           VolunteerPanelActivity_.intent(this).start();
        }

        loadingProgressBar.setVisibility(View.INVISIBLE);


    }
    @Background
    void processVolunteerUI(String fullName) {
        try{
            VolunteerModel model = volunteerClient.getByFullName(fullName).getRecords().get(0);
            openStudentPanel(model.getVolunteer_id());
        }catch (RestClientException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    @Background
    void processStudentUI(int identityID) {
        try{

        }catch (RestClientException e){
            Toast.makeText(this,e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
    @UiThread
    void openStudentPanel(int student_id) {

    }

    @UiThread
    void onError(String message) {
        Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
        loadingProgressBar.setVisibility(View.INVISIBLE);
    }

    @AfterTextChange({R.id.username,R.id.password})
    void afterTextChangedUserName(){

    }



    @EditorAction(R.id.password)
    void onEditorActionsOnPassword(TextView v, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {

        }
    }

}