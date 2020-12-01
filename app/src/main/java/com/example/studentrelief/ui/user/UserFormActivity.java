package com.example.studentrelief.ui.user;

import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.UserClient;
import com.example.studentrelief.services.model.UserModel;
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
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

@OptionsMenu(R.menu.menu_form)
@EActivity(R.layout.activity_user_form)
public class UserFormActivity extends AppCompatActivity {

    @RestService
    UserClient client;

    @Extra
    int userID;
    @ViewById
    TextInputEditText usernameView;
    @ViewById
    TextInputEditText oldPasswordView;

    @ViewById
    CheckBox activeCheck;
    @ViewById
    Toolbar toolbar;

    @Pref
    MyPrefs_ myPrefs;
    private UserModel model;
    private boolean validOldPassword;
    private boolean validNewPassword;

    @AfterViews
    void afterViews() {
        try {
            initAuthCookies();
            setSupportActionBar(toolbar);
            if (userID > 0) {
                getFormData();
            }
        } catch (Exception ex) {
            Toast.makeText(this, ex.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    private void initAuthCookies() {
        String session = myPrefs.session().get();
        String name = Constants.SESSION_NAME;
        client.setCookie(name, session);
    }

    @Background
    void getFormData() {
        if (userID > 0) {
            model = client.get(userID);
            updateUIFormData();
        }
    }

    @UiThread
    void updateUIFormData() {
        usernameView.setText(model.getUsername());
        activeCheck.setChecked(model.isActive());
    }

    @AfterTextChange(R.id.oldPasswordView)
    void newPasswordAfterTextChange(TextView et) {
        String value = et.getText().toString();
        validNewPassword = !value.isEmpty() ? true : false;
        et.setError(value.isEmpty() ? "Required" : null);
    }


    @OptionsItem(R.id.action_save)
    void onSave() {
        try {

            if (validOldPassword && validNewPassword) {

                String password = oldPasswordView.getText().toString();
                boolean active = activeCheck.isChecked();

                model.setPassword(password);
                model.setActive(active);
                save();
            } else {
                showError(getResources().getString(R.string.prompt_invalid_fields));
            }
        } catch (Exception ex) {
            Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    @Background
    void save() {
        if (userID > 0) {
            client.edit(userID, model);
        }
        updateUIAfterSave();

    }

    private void showError(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @UiThread
    void updateUIAfterSave() {
        setResult(RESULT_OK);
        finish();

    }
}