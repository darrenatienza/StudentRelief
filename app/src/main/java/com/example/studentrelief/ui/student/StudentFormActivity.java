package com.example.studentrelief.ui.student;

import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
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
    int studentID;
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
    @ViewById
    ProgressBar progressBar;

    private StudentModel studentModel;
    private UserModel userModel;
    @Pref
    MyPrefs_ myPrefs;
    private boolean validCourse;
    private boolean validSrCode;
    private boolean validFullName;
    private boolean validAddress;
    private boolean validContactNumber;
    private boolean validCampus;


    @AfterTextChange(R.id.etSrCode)
    void srCodeAfterTextChange(TextView et){
            String value = et.getText().toString();
            progressBar.setVisibility(studentID == 0? View.VISIBLE : View.GONE);
            validSrCode = !value.isEmpty() ? true : false;
            et.setError(value.isEmpty() ? "Required" : null);
            // validation for registration of new student
            if(userID == 0){
                checkSrCodeIfExists(value);
            }
        }
    @Background
    void checkSrCodeIfExists(String value) {
        try{
            boolean isEmpty = studentClient.getBySrCode(value).isEmpty();
            validSrCode = !isEmpty;
            updateUIAfterSrCodeValidated();
        }catch (RestClientException ex){
            Log.e("Error",ex.getMessage());
            showErrorAlert(ex.getMessage());
        }catch (Exception ex){
            Log.e("Error",ex.getMessage());
            showErrorAlert(ex.getMessage());
        }
    }
    @UiThread
    void updateUIAfterSrCodeValidated() {
        etSrCode.setError(validSrCode ? "Already Exists" : null);
        progressBar.setVisibility(View.GONE);
    }

    @AfterTextChange(R.id.etFullName)
    void fullNameAfterTextChange(TextView et){
        String value = et.getText().toString();
        validFullName = !value.isEmpty() ? true : false;
        et.setError(value.isEmpty() ? "Required" : null);

    }
    @AfterTextChange(R.id.etAddress)
    void addressAfterTextChange(TextView et){
        String value = et.getText().toString();
        validAddress = !value.isEmpty() ? true : false;
        et.setError(value.isEmpty() ? "Required" : null);
    }
    @AfterTextChange(R.id.etContactNumber)
    void contactNumberAfterTextChange(TextView et){
        String value = et.getText().toString();
        validContactNumber = !value.isEmpty() ? true : false;
        et.setError(value.isEmpty() ? "Required" : null);
    }
    @AfterTextChange(R.id.etCampus)
    void campuseAfterTextChange(TextView et){
        String value = et.getText().toString();
        validCampus = !value.isEmpty() ? true : false;
        et.setError(value.isEmpty() ? "Required" : null);
    }
    @AfterTextChange(R.id.etCourse)
    void courseAfterTextChange(TextView et){
        String value = et.getText().toString();
        validCourse = !value.isEmpty() ? true : false;
        et.setError(value.isEmpty() ? "Required" : null);
    }




    @OptionsItem(R.id.action_save)
    void btnSave(){

            String srCode = etSrCode.getText().toString();
            String fullName = etFullName.getText().toString();
            String address = etAddress.getText().toString();
            String contactNumber = etContactNumber.getText().toString();
            String campus = etCampus.getText().toString();
            String course = etCourse.getText().toString();

            if(validAddress && validCampus && validContactNumber && validCourse && validFullName && validSrCode){
                studentModel.setSr_code(srCode);
                studentModel.setFull_name(fullName);
                studentModel.setAddress(address);
                studentModel.setContact_number(contactNumber);
                studentModel.setCampus(campus);
                studentModel.setCourse(course);
                if(studentID == 0){
                    userModel.setUsername(srCode);
                    userModel.setPassword(srCode);
                    userModel.setActive(false);
                    userModel.setUser_type(Constants.USER_TYPE_STUDENT);
                }
                save();
            }else{
                showErrorAlert("Invalid Student Registration!");
            }




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
        if (studentID > 0){
            studentClient.delete(studentID);

        }
        updateUIAfterSave();
    }

    @Background
    void save(){
        try {
            if (studentID > 0){
                studentClient.edit(studentID, studentModel);

            }else{
                userID = userClient.add(userModel);
                if(userID > 0){
                    studentModel.setUser_id(userID);
                    studentClient.addNew(studentModel);
                }
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
        showNewStudentRegisterDialog();

    }

    private void showNewStudentRegisterDialog() {
        new MaterialAlertDialogBuilder(this)
                .setTitle("New Student Register")
                .setMessage(getString(R.string.dialog_message_new_student_register))
                .setPositiveButton("OK", (dialog, which) ->{
                    // close the activity
                    setResult(RESULT_OK);
                    finish();
                    dialog.dismiss();
                }).show();
    }


    @AfterViews
    void afterViews(){

        try{
            setSupportActionBar(toolbar);
            initAuthCookies();
            if(studentID > 0){
                etSrCode.setEnabled(false);

                getFormData();

            }else{
                studentModel = new StudentModel();
                userModel = new UserModel();
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
    @Background(serial = "sequence1")
    void getFormData() {
        try{
            if (studentID > 0){
                studentModel = studentClient.get(studentID);
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