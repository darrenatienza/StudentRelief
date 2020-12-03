package com.example.studentrelief.ui.employee;

import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.EmployeeClient;
import com.example.studentrelief.services.interfaces.UserClient;
import com.example.studentrelief.services.model.EmployeeModel;
import com.example.studentrelief.services.model.UserModel;
import com.example.studentrelief.ui.misc.Constants;
import com.example.studentrelief.ui.misc.MyPrefs_;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsItem;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;

@OptionsMenu(R.menu.menu_form)
@EFragment(R.layout.fragment_employee_form)
public class EmployeeFormFragment extends Fragment {


    @RestService
    EmployeeClient employeeClient;
    @RestService
    UserClient userClient;
    @ViewById
    TextInputEditText etFullName;
    @ViewById
    EditText etContactNumber;
    @ViewById
    EditText etAddress;
    @ViewById(R.id.ti_et_position)
    TextInputEditText tietPostion;

    @Pref
    MyPrefs_ myPrefs;
    private EmployeeModel employeeModel;
    private boolean validFullName;
    private boolean validContactNumber;
    private boolean validAddress;
    private int employeeID;
    private UserModel userModel;
    private Integer userID;

    private void initAuthCookies() {
        String session = myPrefs.session().get();
        String name = Constants.SESSION_NAME;
        employeeClient.setCookie(name,session);
    }



    @AfterViews
    void afterView(){

        try{
            employeeID = getArguments().getInt("employeeID");
            initAuthCookies();
            if(employeeID > 0) {
                getFormData();
            }else{
                employeeModel = new EmployeeModel();
                userModel = new UserModel();
            }
        }catch (Exception ex){
            Toast.makeText(getActivity(),ex.toString(),Toast.LENGTH_SHORT).show();
        }
    }
    /*@OptionsMenuItem(R.id.action_settings)
    void onShowActionSetting(MenuItem menuItem){
        menuItem.setVisible(false);
    }*/



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
                String position = tietPostion.getText().toString();
                // use fullname as code, user name and password
                String code = fullName.replace(" ","");
                employeeModel.setFull_name(fullName);
                employeeModel.setAddress(address);
                employeeModel.setContact_number(contactNumber);
                employeeModel.setPosition(position);
                // for new employee record
                if(employeeID == 0){
                    userModel.setUsername(code);
                    userModel.setPassword(code);
                    userModel.setActive(true);
                    userModel.setUser_type(Constants.ADMIN_TYPE_STUDENT);
                }
                save();
            }else{
                showError(getResources().getString(R.string.prompt_invalid_fields));
            }
        }catch (Exception ex){
            Toast.makeText(getActivity(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }

    }

    private void showError(String message) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
    }

    @OptionsItem(R.id.action_delete)
    void btnDelete(){

            new MaterialAlertDialogBuilder(getActivity())
                    .setTitle(getString(R.string.dialog_title_delete))
                    .setMessage(getString(R.string.dialog_message_delete))
                    .setPositiveButton("Yes", (dialog, which) -> {
                        delete();
                        dialog.dismiss();
                    })
                    .setNegativeButton("Cancel",  (sDialog, which) ->
                            sDialog.dismiss())
                    .show();

    }
    @Background
    void delete() {
        if (employeeID > 0){
            employeeClient.delete(employeeID);

        }
        updateUIAfterSave();
    }

    @Background
    void save(){
        // edit the employee
        if (employeeID > 0){
            employeeClient.edit(employeeID, employeeModel);
        }else{
            // add new user and volunteer
            userID = userClient.add(userModel);
            if(userID > 0){
                employeeModel.setUser_id(userID);
                employeeClient.addNew(employeeModel);
            }
        }
        updateUIAfterSave();

    }
    @UiThread
    void updateUIAfterSave() {
        /**Todo: add logic after save */
        NavHostFragment.findNavController(EmployeeFormFragment.this)
                .navigate(R.id.action_fragment_employee_form_to_fragment_employee_list);
    }



    @Background
    void getFormData() {
        if (employeeID > 0){
            employeeModel = employeeClient.get(employeeID);
            updateUIFormData(employeeModel);
        }
    }

    @UiThread
    void updateUIFormData(EmployeeModel model) {
        etAddress.setText(model.getAddress());
        etContactNumber.setText(model.getContact_number());
        etFullName.setText(model.getFull_name());
        tietPostion.setText(model.getPosition());
    }

}