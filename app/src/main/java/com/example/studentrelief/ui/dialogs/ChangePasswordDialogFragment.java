package com.example.studentrelief.ui.dialogs;

import android.app.Dialog;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.UserClient;
import com.example.studentrelief.services.model.UserModel;
import com.example.studentrelief.services.model.user.ChangePasswordModel;
import com.example.studentrelief.ui.misc.Constants;
import com.example.studentrelief.ui.misc.MyPrefs_;
import com.google.android.material.textfield.TextInputEditText;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.web.client.RestClientException;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangePasswordDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@EFragment
public class ChangePasswordDialogFragment extends DialogFragment {


    @RestService
    UserClient userClient;
    @Pref
    MyPrefs_ myPrefs;

    @ViewById(R.id.textInputEditText_new_password)
    TextInputEditText textInputEditTextNewPassword;

    @ViewById(R.id.textInputEditText_old_password)
    TextInputEditText textInputEditTextOldPassword;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_USER_ID= "userID";


    // TODO: Rename and change types of parameters
    private int mUserID;
    private UserModel mUserModel;
    private boolean validNewPassword;
    private boolean validoldPassword;

    public ChangePasswordDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param userID Parameter 1.
     * @return A new instance of fragment ChangePasswordDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangePasswordDialogFragment newInstance(int userID) {
        ChangePasswordDialogFragment fragment = new ChangePasswordDialogFragment_();
        Bundle args = new Bundle();
        args.putInt(ARG_USER_ID, userID);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUserID = getArguments().getInt(ARG_USER_ID);
        }
    }
    private void initAuthCookies() {
        String session = myPrefs.session().get();
        String name = Constants.SESSION_NAME;
        userClient.setCookie(name,session);
    }

    @AfterViews
    void afterViews(){
        try{
            initAuthCookies();
            if(mUserID > 0){
                getFormData();
            }else{
                dismiss();
                showError("No User selected!");
            }
        }catch (Exception ex){

        }
    }
    @UiThread
    void showError(String message) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
    }
    @Background
    void getFormData() {
        try {
            mUserModel = userClient.get(mUserID);
        }catch (RestClientException e){
            showError(e.getMessage());
        }
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        return super.onCreateDialog(savedInstanceState);
    }

    @Click(R.id.btn_save)
    void onSave(){
        save();
    }
    @Background
    void save() {
        try {
            if(validNewPassword && validoldPassword){
                String oldPassword = textInputEditTextOldPassword.getText().toString();
                String newPassword = textInputEditTextNewPassword.getText().toString();
                ChangePasswordModel changePasswordModel = new ChangePasswordModel();
                changePasswordModel.setUsername(mUserModel.getUsername());
                changePasswordModel.setPassword(oldPassword);
                changePasswordModel.setNewPassword(newPassword);
                userClient.changePassword(changePasswordModel);
                showSuccess("Password has been changed!");
                dismiss();
                getActivity().finish();
            }else{
                showError("Invalid password!");
            }

        }catch (RestClientException e){
            showError(e.getMessage());
        }
    }

    @AfterTextChange(R.id.textInputEditText_new_password)
    void newPasswordAfterTextChanged(TextView et){
        String value = et.getText().toString();
        validNewPassword = !value.isEmpty() ? true : false;
        et.setError(value.isEmpty() ? "Required" : null);
    }
    @AfterTextChange(R.id.textInputEditText_old_password)
    void oldPasswordAfterTextChanged(TextView et){
        String value = et.getText().toString();
        validoldPassword = !value.isEmpty() ? true : false;
        et.setError(value.isEmpty() ? "Required" : null);
    }
    @UiThread
     void showSuccess(String message) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_SHORT).show();
    }

    @Click(R.id.btn_cancel)
    void cancel(){
        dismiss();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_change_password_dialog, container, false);
    }

    @Override
    public void onResume() {
        // Store access variables for window and blank point
        Window window = getDialog().getWindow();
        Point size = new Point();
        // Store dimensions of the screen in `size`
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        // Set the width of the dialog proportional to 75% of the screen width
        window.setLayout((int) (size.x * 0.9), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        // Call super onResume after sizing
        super.onResume();
    }


}