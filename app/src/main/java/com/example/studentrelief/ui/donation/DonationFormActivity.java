package com.example.studentrelief.ui.donation;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.DonationClient;
import com.example.studentrelief.services.model.DonationModel;
import com.example.studentrelief.ui.misc.Constants;
import com.example.studentrelief.ui.misc.MyPrefs_;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

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

@OptionsMenu(R.menu.menu_form)
@EActivity(R.layout.activity_donation_form)
public class DonationFormActivity extends AppCompatActivity {


    @RestService
    DonationClient client;

    @Extra
    int id;

    @ViewById
    Toolbar toolbar;



    @ViewById
    TextView etName;

    @ViewById
    EditText etQuantity;

    private DonationModel model;
    @Pref
    MyPrefs_ myPrefs;

    private void initAuthCookies() {
        String session = myPrefs.session().get();
        String name = Constants.SESSION_NAME;
        client.setCookie(name,session);
    }

    @OptionsItem(R.id.action_save)
    void btnSave(){

            String fullName = etName.getText().toString();
            model.setName(fullName);
            // always 0 for new record
            if(id == 0){
                model.setQuantity(0);
            }

            save(model);



    }
    @OptionsItem(R.id.action_delete)
    void btnDelete(){
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

    }
    @Background
    void delete() {
        try{
            if (id > 0){
                client.delete(id);
            }
            updateUIAfterSave();
        }catch (RestClientException e){
            showError(e.getMessage());
        }

    }
    @UiThread
    void showError(String message) {
       Toast.makeText(this,message,Toast.LENGTH_SHORT).show();
    }

    @Background
    void save(DonationModel model){
        try {
        if (id > 0){
            client.edit(id,model);
        }else{
            client.addNew(model);
        }
        updateUIAfterSave();
        }catch (RestClientException ex){
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_SHORT).show();
        }catch (Exception ex){
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }
    }
    @UiThread
    void updateUIAfterSave() {
        setResult(RESULT_OK);
        finish();
    }


    @AfterViews
    void afterViews(){
        initAuthCookies();
        try{
            etQuantity.setEnabled(false);
            setSupportActionBar(toolbar);
            if(id > 0){
                getFormData();

            }else{
                model = new DonationModel();
            }
        }catch (Exception ex){
            Toast.makeText(this,ex.toString(),Toast.LENGTH_SHORT).show();
        }


    }

    @Background
    void getFormData() {
        if (id > 0){
            model   = client.get(id);
            updateUIFormData(model);
        }
    }

    @UiThread
    void updateUIFormData(DonationModel model) {
        etName.setText(model.getName());
        etQuantity.setText(String.valueOf(model.getQuantity()));
    }
}