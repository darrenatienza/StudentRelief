package com.example.studentrelief.ui.student;

import android.view.MenuItem;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.StudentClient;
import com.example.studentrelief.services.interfaces.UserClient;
import com.example.studentrelief.services.model.StudentModel;
import com.example.studentrelief.services.model.UserModel;
import com.google.android.material.checkbox.MaterialCheckBox;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.CheckedChange;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.web.client.RestClientException;

import cn.pedant.SweetAlert.SweetAlertDialog;
@OptionsMenu(R.menu.menu_form)
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
    EditText etSrCode;

    @ViewById
    EditText etFullName;

    @ViewById
    EditText etAddress;

    @ViewById
    EditText etContactNumber;

    @ViewById
    EditText etCampus;

    @ViewById
    EditText etCourse;
    @ViewById
    MaterialCheckBox chkPassword;
    @ViewById
    TextInputEditText etPassword;

    @ViewById
    TextInputLayout tiPassword;
    private StudentModel model;
    private UserModel userModel;

    @OptionsItem(R.id.action_save)
    void btnSave(){

            String srCode = etSrCode.getText().toString();
            String fullName = etFullName.getText().toString();
            String address = etAddress.getText().toString();
            String contactNumber = etContactNumber.getText().toString();
            String campus = etCampus.getText().toString();
            String course = etCourse.getText().toString();

            StudentModel model = new StudentModel();
            model.setSr_code(srCode);
            model.setFull_name(fullName);
            model.setAddress(address);
            model.setContact_number(contactNumber);
            model.setCampus(campus);
            model.setCourse(course);
            save(model);



    }

    @CheckedChange
    void chkPassword(CompoundButton c, boolean isChecked){
        if(isChecked){
            tiPassword.setEnabled(true);

        }else{
            tiPassword.setEnabled(false);
        }
        etPassword.setText("");
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
    void save(StudentModel model){
        try {
        if (id > 0){
            studentClient.edit(id,model);
            if(chkPassword.isChecked() && etPassword.getText().toString() != ""){
                userModel.setPassword(etPassword.getText().toString());
                userClient.edit(userID,userModel);
            }
        }else{
            studentClient.addNew(model);
        }
            updateUIAfterSave();
        }catch (RestClientException ex){
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
            if(id > 0){
                getFormData();

            }else{
                model = new StudentModel();
            }
        }catch (Exception ex){
            Toast.makeText(this,ex.toString(),Toast.LENGTH_SHORT).show();
        }


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
                model   = studentClient.get(id);
                updateUIFormData(model);
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