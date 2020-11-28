package com.example.studentrelief.ui.volunteer;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.UserClient;
import com.example.studentrelief.services.interfaces.VolunteerClient;
import com.example.studentrelief.services.model.UserAddEditModel;
import com.example.studentrelief.services.model.UserModel;
import com.example.studentrelief.services.model.VolunteerAddEditModel;
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
    int volunteerID;

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
    private UserAddEditModel userAddEditModel;
    private UserModel userModel;
    private VolunteerAddEditModel volunteerAddEditModel;

    private void initAuthCookies() {
        String session = myPrefs.session().get();
        String name = Constants.SESSION_NAME;
        volunteerClient.setCookie(name,session);
    }

    @CheckedChange
    void chkPassword(CompoundButton c, boolean isChecked){
        if(isChecked){
            tilPassword.setEnabled(true);

        }else{
            tilPassword.setEnabled(false);
        }
        tietPassword.setText("");
    }

    @OptionsItem(R.id.action_save)
    void btnSave(){
        try {
            boolean isPasswordChanged = chkPassword.isChecked();
            String password = tietPassword.getText().toString();
            String fullName = etFullName.getText().toString();
            String address = etAddress.getText().toString();
            String contactNumber = etContactNumber.getText().toString();
            // use fullname as code, user name and password
            String code = fullName.replace(" ","");
            volunteerModel.setCode(code);
            volunteerModel.setFull_name(fullName);
            volunteerModel.setAddress(address);
            volunteerModel.setContact_number(contactNumber);

            userModel.setFull_name(fullName);
            userModel.setActive(true);
            userModel.setUsername(code);
            userModel.setUser_type(Constants.USER_TYPE_VOLUNTEER);
            // check for password changed, for edit volunteer
            if(isPasswordChanged && password != ""){
                userModel.setPassword(code);
            }
            // for new volunteer
            if(userID == 0 && volunteerID == 0){
                userModel.setPassword(code);
            }
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
        if (volunteerID > 0){
            volunteerClient.delete(volunteerID);

        }
        updateUIAfterSave();
    }

    @Background
    void save(){

        // edit the volunteer
        if (volunteerID > 0){
            userClient.edit(userID,userModel);
            volunteerClient.edit(volunteerID, volunteerModel);
        }else{
            // add new volunteer
           userModel = userClient.register(userModel);
           if(userModel.getUser_id() > 0){
               volunteerModel.setUser_id(userModel.getUser_id());
               volunteerClient.addNew(volunteerModel);
           }

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
            if(volunteerID > 0){
                getFormData();

            }else{
                volunteerModel = new VolunteerModel();
                userAddEditModel = new UserAddEditModel();
                userModel = new UserModel();
                tilPassword.setVisibility(View.INVISIBLE);
                chkPassword.setVisibility(View.INVISIBLE);
            }
            tilPassword.setEnabled(false);
        }catch (Exception ex){
            Toast.makeText(this,ex.toString(),Toast.LENGTH_SHORT).show();
        }


    }

    @Background
    void getFormData() {
        try {
            if (volunteerID > 0){
                volunteerModel = volunteerClient.get(volunteerID);
                userID = volunteerModel.getUser_id();
                if(userID != 0){
                    userModel = userClient.get(userID);
                }
            }
            // updates ui
            updateUIFormData(volunteerModel);
        }catch (RestClientException e){
            showErrorAlert(e.getMessage());
        }
        catch (Exception e){
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

        etAddress.setText(model.getAddress());
        etContactNumber.setText(model.getContact_number());
        etFullName.setText(model.getFull_name());

    }
}