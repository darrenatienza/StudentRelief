package com.example.studentrelief.ui.volunteer;

import android.view.MenuItem;
import android.widget.TextView;
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
import com.example.studentrelief.services.model.user.RegisterUserModel;
import com.example.studentrelief.ui.misc.Constants;
import com.example.studentrelief.ui.misc.MyPrefs_;
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
@EActivity(R.layout.activity_volunteer_form)
public class VolunteerFormActivity extends AppCompatActivity {

    @RestService
    VolunteerClient volunteerClient;
    @RestService
    UserClient userClient;
    @Extra
    int volunteerID;
    @Extra
    String userType;
    int userID;

    @ViewById
    Toolbar toolbar;


    @ViewById
    TextInputEditText etFullName;

    @ViewById
    TextInputEditText etContactNumber;
    @ViewById
    TextInputEditText etAddress;


    private VolunteerModel volunteerModel;

    @Pref
    MyPrefs_ myPrefs;
    private UserAddEditModel userAddEditModel;
    private UserModel userModel;
    private VolunteerAddEditModel volunteerAddEditModel;
    private boolean isPasswordChanged;
    private RegisterUserModel registerUserModel;
    private boolean validFullName;
    private boolean validAddress;
    private boolean validContactNumber;

    private void initAuthCookies() {
        String session = myPrefs.session().get();
        String name = Constants.SESSION_NAME;
        volunteerClient.setCookie(name,session);
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
    boolean validForm(){
        return  validFullName && validContactNumber && validAddress;
    }
    @OptionsItem(R.id.action_save)
    void btnSave(){
        try {
            if(validForm()){
                String fullName = etFullName.getText().toString();
                String address = etAddress.getText().toString();
                String contactNumber = etContactNumber.getText().toString();
                // use fullname as code, user name and password
                String code = fullName.replace(" ","");

                volunteerModel.setCode(code);
                volunteerModel.setFull_name(fullName);
                volunteerModel.setAddress(address);
                volunteerModel.setContact_number(contactNumber);

                if(volunteerID == 0){
                    userModel.setUsername(code);
                    userModel.setPassword(code);
                    userModel.setActive(true);
                    userModel.setUser_type(Constants.USER_TYPE_VOLUNTEER);
                }
                save();
            }else{
                showErrorAlert(getString(R.string.prompt_invalid_fields));
            }


        }catch (Exception ex){
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }

    }


    @OptionsMenuItem(R.id.action_delete)
    void deletOptionMenuItem(MenuItem item){
        item.setVisible(userType.contentEquals(Constants.USER_TYPE_ADMIN) ? true : false);

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

        try {
            // edit the volunteer
            if (volunteerID > 0){
                volunteerClient.edit(volunteerID, volunteerModel);
            }else{
                // add new user and volunteer
                userID = userClient.add(userModel);
                if(userID > 0){
                    volunteerModel.setUser_id(userID);
                    volunteerClient.addNew(volunteerModel);
                }
            }
            updateUIAfterSave();
        }catch (RestClientException e){
            showErrorAlert(e.getMessage());
        }catch (Exception e){
            showErrorAlert(e.getMessage());
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
            initAuthCookies();
            setSupportActionBar(toolbar);
            if(volunteerID > 0){
                getFormData();
                checkUserType();

            }else{
                volunteerModel = new VolunteerModel();
                userAddEditModel = new UserAddEditModel();
                userModel = new UserModel();

            }
        }catch (Exception ex){
            Toast.makeText(this,ex.toString(),Toast.LENGTH_SHORT).show();
        }


    }
    @Background()
    void checkUserType() {
        try{

        }catch (RestClientException ex){

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
       Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }
    @UiThread
    void updateUIFormData(VolunteerModel model) {

        etAddress.setText(model.getAddress());
        etContactNumber.setText(model.getContact_number());
        etFullName.setText(model.getFull_name());
        /**Todo: Create model for volunteer view (active status included)*/

    }

    @OptionsMenuItem(R.id.action_delete)
    void openDeleteMenu(MenuItem menuItem){
        if(volunteerID == 0){
            menuItem.setVisible(false);
        }
    }
}