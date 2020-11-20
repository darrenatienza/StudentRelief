package com.example.studentrelief.ui.login;

import android.app.Activity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.example.studentrelief.R;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.EditorAction;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity {

    @ViewById
    EditText username;
    @ViewById
    EditText password;
    @ViewById(R.id.login)
    Button loginButton;
    @ViewById(R.id.loading)
    ProgressBar loadingProgressBar;
    private LoginViewModel loginViewModel;

    @AfterViews
    void afterViews(){
        loginViewModel = ViewModelProviders.of(this, new LoginViewModelFactory())
                .get(LoginViewModel.class);

        loginViewModel.getLoginFormState().observe(this, new Observer<LoginFormState>() {
            @Override
            public void onChanged(@Nullable LoginFormState loginFormState) {
                if (loginFormState == null) {
                    return;
                }
                loginButton.setEnabled(loginFormState.isDataValid());
                if (loginFormState.getUsernameError() != null) {
                    username.setError(getString(loginFormState.getUsernameError()));
                }
                if (loginFormState.getPasswordError() != null) {
                    password.setError(getString(loginFormState.getPasswordError()));
                }
            }
        });

        loginViewModel.getLoginResult().observe(this, new Observer<LoginResult>() {
            @Override
            public void onChanged(@Nullable LoginResult loginResult) {
                if (loginResult == null) {
                    return;
                }
                loadingProgressBar.setVisibility(View.GONE);
                if (loginResult.getError() != null) {
                    showLoginFailed(loginResult.getError());
                }
                if (loginResult.getSuccess() != null) {
                    updateUiWithUser(loginResult.getSuccess());
                }
                setResult(Activity.RESULT_OK);

                //Complete and destroy login activity once successful
                finish();
            }
        });
    }
    @Click(R.id.login)
    void onLogin(){
        loadingProgressBar.setVisibility(View.VISIBLE);
        loginViewModel.login(username.getText().toString(),
                password.getText().toString());
    }
    @AfterTextChange({R.id.username,R.id.password})
    void afterTextChangedUserName(){
        String userName = username.getText().toString();
        String pass = password.getText().toString();
        onLoginDataChanged(userName,pass);
    }

    void onLoginDataChanged(String userName, String password){
        loginViewModel.loginDataChanged(userName,
                password);
    }
    @EditorAction(R.id.password)
    void onEditorActionsOnPassword(TextView v, int actionId, KeyEvent keyEvent) {
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            loginViewModel.login(username.getText().toString(),
                    password.getText().toString());
        }
    }
    private void updateUiWithUser(LoggedInUserView model) {
        String welcome = getString(R.string.welcome) + model.getDisplayName();
        // TODO : initiate successful logged in experience
        Toast.makeText(getApplicationContext(), welcome, Toast.LENGTH_LONG).show();
    }

    private void showLoginFailed(@StringRes Integer errorString) {
        Toast.makeText(getApplicationContext(), errorString, Toast.LENGTH_SHORT).show();
    }
}