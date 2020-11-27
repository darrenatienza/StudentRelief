package com.example.studentrelief.ui.volunteer;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.UserClient;
import com.example.studentrelief.services.interfaces.VolunteerClient;
import com.example.studentrelief.services.model.UserAddModel;
import com.example.studentrelief.services.model.VolunteerModel;
import com.example.studentrelief.ui.misc.Constants;
import com.example.studentrelief.ui.misc.MyPrefs_;
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
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.web.client.RestClientException;

import cn.pedant.SweetAlert.SweetAlertDialog;
@OptionsMenu(R.menu.menu_form)
@EActivity(R.layout.activity_volunteer_form)
public class VolunteerFormActivity extends AppCompatActivity {

    @RestService
    VolunteerClient volunteerClient;
    @RestService
    UserClient userClient;
    @Extra
    int id;
    @Extra
    int userID;
    @ViewById
    Toolbar toolbar;


    @ViewById
    TextInputEditText etFullName;

    @ViewById
    TextInputEditText etContactNumber;
    @ViewById
    TextInputEditText etAddress;
    @ViewById
    TextInputEditText tietPassword;
    @ViewById
    MaterialCheckBox chkPassword;
    @ViewById
    TextInputLayout tilPassword;

    private VolunteerModel volunteerModel;

    @Pref
    MyPrefs_ myPrefs;
    private UserAddModel userAddModel;

    private void initAuthCookies() {
        String session = myPrefs.session().get();
        String name = Constants.SESSION_NAME;
        volunteerClient.setCookie(name,session);
    }

    @CheckedChange
    void chkPassword(CompoundButton c, boolean isChecked){
        if(isChecked){
            tietPassword.setEnabled(true);

        }else{
            tietPassword.setEnabled(false);
        }
        tietPassword.setText("");
    }

    @OptionsItem(R.id.action_save)
    void btnSave(){
        try {
            String fullName = etFullName.getText().toString();
            String address = etAddress.getText().toString();
            String contactNumber = etContactNumber.getText().toString();

            volunteerModel.setFull_name(fullName);
            volunteerModel.setAddress(address);
            volunteerModel.setContact_number(contactNumber);
            save();

        }catch (RestClientException ex){
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_SHORT).show();
        }catch (Exception ex){
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_LONG).show();
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
        if (id > 0){
            volunteerClient.delete(id);

        }
        updateUIAfterSave();
    }

    @Background
    void save(){
        if (id > 0){
            volunteerClient.edit(id, volunteerModel);
        }else{
            volunteerClient.addNew(volunteerModel);
        }
        if (userID > 0){
            userClient.edit(userID,userModel);

        }else{
           userClient.addNew(userModel);
        }
        updateUIAfterSave();

    }
    @UiThread
    void updateUIAfterSave() {
        setResult(RESULT_OK);
        finish();
    }


    @AfterViews
    void afterViews(){

        try{
            initAuthCookies();
            setSupportActionBar(toolbar);
            if(id > 0){
                getFormData();

            }else{
                volunteerModel = new VolunteerModel();
                tilPassword.setVisibility(View.INVISIBLE);
                chkPassword.setVisibility(View.INVISIBLE);
            }
        }catch (Exception ex){
            Toast.makeText(this,ex.toString(),Toast.LENGTH_SHORT).show();
        }


    }

    @Background
    void getFormData() {
        try {
            if (id > 0){
                volunteerModel = volunteerClient.getVolunteerView(id).getRecords().get(0);

            }
            updateUIFormData(volunteerModel);
        }catch (RestClientException e){
            showErrorAlert(e.getMessage());
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
    @UiThread
    void updateUIFormData(VolunteerModel model) {
        userID =model.getUser_id();

        etAddress.setText(model.getAddress());
        etContactNumber.setText(model.getContact_number());
        etFullName.setText(model.getFull_name());

    }
}