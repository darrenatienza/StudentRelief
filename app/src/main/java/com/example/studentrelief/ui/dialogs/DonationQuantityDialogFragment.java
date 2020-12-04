package com.example.studentrelief.ui.dialogs;

import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.DonationClient;
import com.example.studentrelief.services.interfaces.ReliefRequestDonationClient;
import com.example.studentrelief.services.model.DonationModel;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.web.client.RestClientException;

import java.util.List;

@EFragment
public class DonationQuantityDialogFragment extends DialogFragment {

    @RestService
    ReliefRequestDonationClient reliefRequestDonationClient;
    @RestService
    DonationClient donationClient;
    @ViewById(R.id.m_ac_tv_donation)
    MaterialAutoCompleteTextView donations;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String RELIEF_REQUEST_ID = "0";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private boolean validDonation;
    private List<DonationModel> donationModels;
    private ArrayAdapter donationArrayAdapter;



    public DonationQuantityDialogFragment() {
        // Required empty public constructor
    }

    @AfterViews
    void afterViews(){
        loadDonationListAsync();
    }
    @Background
    void loadDonationListAsync() {
        try{
            donationModels = donationClient.getAll("").getRecords();
            UpdateDonationSpinnerUI(donationModels);
        }catch (RestClientException ex){

        }catch (Exception ex){

        }

    }
    @UiThread
    void UpdateDonationSpinnerUI(List<DonationModel> models) {
        donationArrayAdapter = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_dropdown_item_1line,models);
        donations.setAdapter(donationArrayAdapter);
        donationArrayAdapter.setNotifyOnChange(true);

    }
    @Click(R.id.btn_save)
    void save(){
        dismiss();
    }
    @Click(R.id.btn_cancel)
    void cancel(){
        dismiss();
    }
    public  static DonationQuantityDialogFragment newInstance(Integer reliefRequestID){
        DonationQuantityDialogFragment fragment = new DonationQuantityDialogFragment_();
        Bundle args = new Bundle();
        args.putInt(RELIEF_REQUEST_ID, reliefRequestID);
        fragment.setArguments(args);
        return fragment;

    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog d = super.onCreateDialog(savedInstanceState);
        d.getContext().getTheme().applyStyle(R.style.MyAlertDialog,true);
        return d;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.dialog_donation_quantity, null);
        return v;
    }

    @AfterTextChange(R.id.m_ac_tv_donation)
    void donationAfterTextChange(TextView et){
        String value = et.getText().toString();
        validDonation = !value.isEmpty() ? true : false;
        et.setError(value.isEmpty() ? "Required" : null);
    }
    @AfterTextChange(R.id.ti_et_quantity)
    void quantityAfterTextChange(TextView et){
        String value = et.getText().toString();
        validDonation = !value.isEmpty() ? true : false;
        et.setError(value.isEmpty() ? "Required" : null);
    }
    @Background
    void addNewReliefRequestDonation(DialogInterface dialog) {
        try{
            reliefRequestDonationClient.getAll(1);
            updateUiAfterAddNew(dialog);
        }catch (RestClientException ex){
            showError(ex.getMessage());
        }
    }
    @UiThread
    void updateUiAfterAddNew(DialogInterface dialog) {
        dialog.dismiss();
    }

    @UiThread
    void showError(String message) {
        Toast.makeText(getActivity(),message,Toast.LENGTH_LONG).show();
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
