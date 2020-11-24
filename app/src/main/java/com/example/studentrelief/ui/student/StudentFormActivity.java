package com.example.studentrelief.ui.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.StudentClient;
import com.example.studentrelief.services.model.AddEditDonnerModel;
import com.example.studentrelief.services.model.StudentModel;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
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

    @Extra
    int id;

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

            }
        }catch (Exception ex){
            Toast.makeText(this,ex.toString(),Toast.LENGTH_SHORT).show();
        }


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
        if (id > 0){
            StudentModel model   = studentClient.get(id);
            updateUIFormData(model);
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