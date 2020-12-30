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
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.ReliefRequestClient;
import com.example.studentrelief.services.interfaces.UserClient;
import com.example.studentrelief.services.model.ReliefRequestModel;
import com.example.studentrelief.services.model.UserModel;
import com.example.studentrelief.ui.misc.Constants;
import com.example.studentrelief.ui.misc.MyPrefs_;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;

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
 * Use the {@link ReliefRequestSuggestionDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
@EFragment
public class ReliefRequestSuggestionDialogFragment extends DialogFragment {


    @RestService
    UserClient userClient;
    @RestService
    ReliefRequestClient reliefRequestClient;
    @Pref
    MyPrefs_ myPrefs;

    @ViewById(R.id.checkBox_clothes)
    CheckBox checkBoxClothes;
    @ViewById(R.id.checkBox_food)
    CheckBox checkBoxFood;
    @ViewById(R.id.checkBox_temp_shelter)
    CheckBox checkboxTempShelter;
    @ViewById(R.id.textInputEditText_others)
    TextInputEditText textInputEditTextOthers;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_STUDENT_ID= "studentID";
    private static final String ARG_RELIEF_TASK_ID= "reliefTaskID";

    // TODO: Rename and change types of parameters
    private int mStudentID;
    private UserModel mUserModel;
    private int mReliefTaskID;


    public ReliefRequestSuggestionDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param studentID Parameter 1.
     * @return A new instance of fragment ChangePasswordDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ReliefRequestSuggestionDialogFragment newInstance(int studentID,int reliefTaskID) {
        ReliefRequestSuggestionDialogFragment fragment = new ReliefRequestSuggestionDialogFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_STUDENT_ID, studentID);
        args.putInt(ARG_RELIEF_TASK_ID, reliefTaskID);
        fragment.setArguments(args);
        return fragment;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.dialog_fragment_student_relief_suggestion, container, false);
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mStudentID = getArguments().getInt(ARG_STUDENT_ID);
            mReliefTaskID = getArguments().getInt(ARG_RELIEF_TASK_ID);
        }
    }
    private void initAuthCookies() {
        String session = myPrefs.session().get();
        String name = Constants.SESSION_NAME;
        userClient.setCookie(name,session);
        reliefRequestClient.setCookie(name,session);
    }

    @AfterViews
    void afterViews(){
        try{
            initAuthCookies();
            if(mStudentID <= 0 && mReliefTaskID <= 0){
                dismiss();
                showError("No student or relief task selected!");
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
            mUserModel = userClient.get(mStudentID);
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
            //TODO: test for saving record
            String donationRequests = "";
            boolean hasClothes = checkBoxClothes.isChecked();
            boolean hasFood = checkBoxFood.isChecked();
            boolean hasTempShelter = checkboxTempShelter.isChecked();
            boolean emptyOthers = textInputEditTextOthers.getText().toString().isEmpty();
            String others = textInputEditTextOthers.getText().toString();
            donationRequests = hasClothes ? "Clothes" + addSeparator(donationRequests) : donationRequests;
            donationRequests = hasFood ? "Food" + addSeparator(donationRequests) : donationRequests;
            donationRequests = hasTempShelter ? "Temporary Shelter" + addSeparator(donationRequests) : donationRequests;
            donationRequests = !emptyOthers ? others : donationRequests;
            ReliefRequestModel _model = new ReliefRequestModel();
            _model.setStudent_id(mStudentID);
            _model.setRelief_task_id(mReliefTaskID);
            _model.setReleased(false);
            _model.setDonation_requests(donationRequests);
            addNewReliefRequest(_model);
            dismiss();

        }catch (RestClientException e){
            showError(e.getMessage());
        }
    }
    String addSeparator(String baseString){
        return baseString.isEmpty() ? "" : ",";
    }
    @Background
    void addNewReliefRequest(ReliefRequestModel model) {
        try {
            reliefRequestClient.addNew(model);
            showReliefRequestSaveConfirmation();
        }catch (RestClientException ex){
            showError(ex.getMessage());
        }

    }
    @UiThread
    void showReliefRequestSaveConfirmation() {
        new MaterialAlertDialogBuilder(getActivity())
                .setTitle(getString(R.string.dialog_title_success))
                .setMessage(getString(R.string.dialog_message_request_donation))
                .setPositiveButton(getString(R.string.dialog_button_positive), ((dialog, which) -> {
                    // check for pending request
                    //checkForPendingReliefRequest();
                    dialog.dismiss();
                }))
                .show();
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