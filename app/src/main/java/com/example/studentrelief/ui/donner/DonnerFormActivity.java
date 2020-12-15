package com.example.studentrelief.ui.donner;


import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.DonnerClient;
import com.example.studentrelief.services.model.AddEditDonnerModel;
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



@OptionsMenu(R.menu.menu_form)
@EActivity(R.layout.activity_donner_form)
public class DonnerFormActivity extends AppCompatActivity {

    @RestService
    DonnerClient donnerClient;



    @Extra
    int donnerID;


    @ViewById
    Toolbar toolbar;



    @ViewById
    TextInputEditText etFullName;

    @ViewById
    EditText etContactNumber;
    @ViewById
    EditText etAddress;
    @Pref
    MyPrefs_ myPrefs;
    private AddEditDonnerModel model;
    private boolean validFullName;
    private boolean validContactNumber;
    private boolean validAddress;

    private void initAuthCookies() {
        String session = myPrefs.session().get();
        String name = Constants.SESSION_NAME;
        donnerClient.setCookie(name,session);
    }

    @AfterViews
    void afterViews(){

        try{
            /**Todo: remove delete while adding*/
            initAuthCookies();
            setSupportActionBar(toolbar);
            if(donnerID > 0) {
                getFormData();
            }else{
                model = new AddEditDonnerModel();
            }
        }catch (Exception ex){
            Toast.makeText(this,ex.toString(),Toast.LENGTH_SHORT).show();
        }


    }
    @OptionsMenuItem(R.id.action_delete)
    void onShowActionDelete(MenuItem menuItem){
        if(donnerID == 0){
            menuItem.setVisible(false);
        }

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

    @OptionsItem(R.id.action_save)
    void btnSave(){
        try {

            if(validAddress && validContactNumber && validFullName){
                String fullName = etFullName.getText().toString();
                String address = etAddress.getText().toString();
                String contactNumber = etContactNumber.getText().toString();

                model.setFull_name(fullName);
                model.setAddress(address);
                model.setContact_number(contactNumber);
                save(model);
            }else{
                showError(getResources().getString(R.string.prompt_invalid_fields));
            }
        }catch (Exception ex){
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    private void showError(String message) {
        Toast.makeText(this,message,Toast.LENGTH_LONG).show();
    }

    @OptionsItem(R.id.action_delete)
    void btnDelete(){
        try {
            new MaterialAlertDialogBuilder(this)
                    .setTitle(getString(R.string.dialog_title_delete))
                    .setMessage(getString(R.string.dialog_message_delete))
                    .setPositiveButton(getString(R.string.dialog_button_yes),((dialogInterface, i) -> {
                        delete();
                        dialogInterface.dismiss();
                    }))
                    .setNegativeButton(getString(R.string.dialog_button_no),((dialogInterface, i) -> {
                        dialogInterface.dismiss();
                    }))
                    .show();

    }catch (RestClientException ex){
        Toast.makeText(this,ex.getMessage(),Toast.LENGTH_SHORT).show();
    }catch (Exception ex){
        Toast.makeText(this,ex.getMessage(),Toast.LENGTH_SHORT).show();
    }

    }
    @Background
    void delete() {
        if (donnerID > 0){
            donnerClient.delete(donnerID);

        }
        updateUIAfterSave();
    }

    @Background
    void save(AddEditDonnerModel model){
            if (donnerID > 0){
                donnerClient.edit(donnerID,model);
            }else{
                donnerClient.addNew(model);
            }
            updateUIAfterSave();

    }
    @UiThread
    void updateUIAfterSave() {
        setResult(RESULT_OK);
        finish();
    }



    @Background
    void getFormData() {
        if (donnerID > 0){
            model   = donnerClient.getDonner(donnerID);
            updateUIFormData(model);
        }
    }

    @UiThread
    void updateUIFormData(AddEditDonnerModel model) {
        etAddress.setText(model.getAddress().toString());
        etContactNumber.setText(model.getContact_number().toString());
        etFullName.setText(model.getFull_name().toString());

    }




}