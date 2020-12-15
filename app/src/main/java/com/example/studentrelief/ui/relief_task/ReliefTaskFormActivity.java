package com.example.studentrelief.ui.relief_task;

import android.view.MenuItem;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.ReliefTaskClient;
import com.example.studentrelief.services.model.ReliefTaskModel;
import com.example.studentrelief.ui.misc.Constants;
import com.example.studentrelief.ui.misc.MyPrefs_;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

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
@EActivity(R.layout.activity_relief_task_form)
public class ReliefTaskFormActivity extends AppCompatActivity{

    @RestService
    ReliefTaskClient client;

    @Extra
    int reliefTaskID;

    @ViewById
    Toolbar toolbar;

    @ViewById
    EditText etCode;

    @ViewById
    EditText etTitle;

    @ViewById
    EditText etAffectedArea;

    @ViewById
    CheckBox chkActive;


    private ReliefTaskModel reliefTaskModel;

    @Pref
    MyPrefs_ myPrefs;

    private void initAuthCookies() {
        String session = myPrefs.session().get();
        String name = Constants.SESSION_NAME;
        client.setCookie(name,session);
    }

    @AfterViews
    void afterViews(){

        try{
            initAuthCookies();
            setSupportActionBar(toolbar);
            if(reliefTaskID > 0){
                getFormData();

            }else{
                chkActive.setEnabled(false);
                reliefTaskModel = new ReliefTaskModel();
            }
        }catch (Exception ex){
            Toast.makeText(this,ex.toString(),Toast.LENGTH_SHORT).show();
        }


    }
    @OptionsMenuItem(R.id.action_delete)
    void onShowActionDelete(MenuItem menuItem){
        if(reliefTaskID == 0){
            menuItem.setVisible(false);
        }

    }
    /** UI Actions */
    @OptionsItem(R.id.action_save)
    void btnSave(){
        try {
            String code = etCode.getText().toString();
            String title = etTitle.getText().toString();
            String affectedArea = etAffectedArea.getText().toString();
            boolean active = chkActive.isChecked();
            reliefTaskModel.setActive(active);
            reliefTaskModel.setAffected_areas(affectedArea);
            reliefTaskModel.setCode(code);
            reliefTaskModel.setTitle(title);
            saveAsync(reliefTaskModel);

        }catch (RestClientException ex){
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_SHORT).show();
        }catch (Exception ex){
            Toast.makeText(this,ex.getMessage(),Toast.LENGTH_LONG).show();
        }

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


    /** Background Task **/


    @Background
    void getFormData() {
        if (reliefTaskID > 0){
            reliefTaskModel = client.get(reliefTaskID);
            updateUIFormData(reliefTaskModel);
        }
    }
    @Background
    void saveAsync(ReliefTaskModel model){
        if (reliefTaskID > 0){
            client.edit(reliefTaskID,model);
        }else{
            client.addNew(model);
        }
        updateUIAfterSave();

    }
    @Background
    void delete() {
        if (reliefTaskID > 0){
            client.delete(reliefTaskID);
        }
        updateUIAfterSave();
    }

    /** UI threads */
    @UiThread
    void updateUIAfterSave() {
        // close the form
        setResult(RESULT_OK);
        finish();
    }


    @UiThread
    void updateUIFormData(ReliefTaskModel model) {
        // setting value of selected model from list
        etAffectedArea.setText(model.getAffected_areas());
        etTitle.setText(model.getTitle());
        etCode.setText(model.getCode());
        chkActive.setChecked(model.getActive());
    }

    /** miscellaneous */


}