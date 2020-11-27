package com.example.studentrelief.ui.donner;


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

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
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
@EActivity(R.layout.activity_donner_form)
public class DonnerFormActivity extends AppCompatActivity {

    @RestService
    DonnerClient donnerClient;



    @Extra
    int id;


    @ViewById
    Toolbar toolbar;



    @ViewById
    TextView etFullName;

    @ViewById
    EditText etContactNumber;
    @ViewById
    EditText etAddress;
    @Pref
    MyPrefs_ myPrefs;
    private AddEditDonnerModel model;

    private void initAuthCookies() {
        String session = myPrefs.session().get();
        String name = Constants.SESSION_NAME;
        donnerClient.setCookie(name,session);
    }

    @AfterViews
    void afterViews(){

        try{
            initAuthCookies();
            setSupportActionBar(toolbar);
            if(id > 0) {
                getFormData();
            }else{
                model = new AddEditDonnerModel();
            }
        }catch (Exception ex){
            Toast.makeText(this,ex.toString(),Toast.LENGTH_SHORT).show();
        }


    }
    @OptionsItem(R.id.action_save)
    void btnSave(){
        try {
            String fullName = etFullName.getText().toString();
            String address = etAddress.getText().toString();
            String contactNumber = etContactNumber.getText().toString();


            model.setFull_name(fullName);
            model.setAddress(address);
            model.setContact_number(contactNumber);
            save(model);

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
            donnerClient.delete(id);

        }
        updateUIAfterSave();
    }

    @Background
    void save(AddEditDonnerModel model){
            if (id > 0){
                donnerClient.edit(id,model);
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
        if (id > 0){
            model   = donnerClient.getDonner(id);
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