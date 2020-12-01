package com.example.studentrelief.ui.student;

import android.text.Editable;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.StudentClient;
import com.example.studentrelief.services.interfaces.UserClient;
import com.example.studentrelief.services.model.StudentModel;
import com.example.studentrelief.services.model.UserModel;
import com.example.studentrelief.ui.misc.Constants;
import com.example.studentrelief.ui.misc.MyPrefs_;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.TextChange;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.web.client.RestClientException;

import cn.pedant.SweetAlert.SweetAlertDialog;
@OptionsMenu(R.menu.menu_with_user_form)
@EActivity(R.layout.activity_student_form)
public class StudentFormActivity extends AppCompatActivity {

    @RestService
    StudentClient studentClient;
    @RestService
    UserClient userClient;
    @Extra
    int id;
    @Extra
    int userID;
    @ViewById
    Toolbar toolbar;


    @ViewById
    TextInputEditText etSrCode;

    @ViewById
    TextInputEditText etFullName;

    @ViewById
    TextInputEditText etAddress;

    @ViewById
    TextInputEditText etContactNumber;

    @ViewById
    TextInputEditText etCampus;

    @ViewById
    TextInputEditText etCourse;

    private StudentModel studentModel;
    private UserModel userModel;
    @Pref
    MyPrefs_ myPrefs;



    @AfterTextChange(R.id.etSrCode)
    void srCodeAfterTextChange(TextView et){
       et.setError(et.getText() == "" ? "Required" : null);
    }




    @OptionsItem(R.id.action_save)
    void btnSave(){

            String srCode = etSrCode.getText().toString();
            String fullName = etFullName.getText().toString();
            String address = etAddress.getText().toString();
            String contactNumber = etContactNumber.getText().toString();
            String campus = etCampus.getText().toString();
            String course = etCourse.getText().toString();

            studentModel.setSr_code(srCode);
            studentModel.setFull_name(fullName);
            studentModel.setAddress(address);
            studentModel.setContact_number(contactNumber);
            studentModel.setCampus(campus);
            studentModel.setCourse(course);
            save();



    }

    @OptionsItem(R.id.action_delete)
    void btnDelete(){
        try {
            new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                    .setTitleText("Are you sure?")
                    .setContentText("Won't be able to recover this record!")
                    .setConfirmText("Yes,delete it!")
                    .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            delete();
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sDialog) {
                            sDialog.dismissWithAnimation();
                        }
                    })
                    .show();


        }catch (RestClientException ex){
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_SHORT).show();
        }catch (Exception ex){
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_SHORT).show();
        }

    }
    @Background
    void delete() {
        if (id > 0){
            studentClient.delete(id);

        }
        updateUIAfterSave();
    }

    @Background
    void save(){
        try {
            if (id > 0){
                studentClient.edit(id, studentModel);

            }else{
                studentClient.addNew(studentModel);
            }
            if (userID > 0){
                userClient.edit(userID,userModel);

            }else{
                // no adding new user, adding must be on student and volunteer activation
            }
            updateUIAfterSave();
        }catch (RestClientException ex){
            Log.e("Rest Error",ex.toString());
            showErrorAlert(ex.getMessage());
        }catch (Exception ex){
            showErrorAlert(ex.getMessage());
        }


    }
    @UiThread
    void updateUIAfterSave() {
        setResult(RESULT_OK);
        finish();
    }


    @AfterViews
    void afterViews(){

        try{
            setSupportActionBar(toolbar);
            initAuthCookies();
            if(id > 0){
                getFormData();

            }else{
                studentModel = new StudentModel();
                // do not show the password fields for new student
                // registration
            }
        }catch (Exception ex){
            Toast.makeText(this,ex.toString(),Toast.LENGTH_SHORT).show();
        }


    }

    private void initAuthCookies() {
        String session = myPrefs.session().get();
        String name = Constants.SESSION_NAME;
        userClient.setCookie(name,session);
        studentClient.setCookie(name,session);
    }
    // initialization
    @OptionsMenuItem(R.id.action_delete)
    void onLoadDeleteMenu(MenuItem menuItem){
        menuItem.setVisible(false);
    }
    @UiThread
    void showErrorAlert(String message) {
        new SweetAlertDialog(this, SweetAlertDialog.ERROR_TYPE)
                .setTitleText("Oops!")
                .setContentText("An error occured! \n" + message)
                .setConfirmText("OK")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sDialog) {
                        sDialog.dismissWithAnimation();
                    }
                })
                .show();
    }
    @Background
    void getFormData() {
        try{
            if (id > 0){
                studentModel = studentClient.get(id);
                updateUIFormData(studentModel);
            }
            if(userID > 0){
                userModel = userClient.get(userID);

            }
        }catch (RestClientException e){
            showErrorAlert(e.getMessage());
        }

    }

    @UiThread
    void updateUIFormData(StudentModel model) {
        etSrCode.setText(model.getSr_code());
        etFullName.setText(model.getFull_name());
        etAddress.setText(model.getAddress());
        etContactNumber.setText(model.getContact_number());
        etCampus.setText(model.getCampus());
        etCourse.setText(model.getCourse());

    }
}