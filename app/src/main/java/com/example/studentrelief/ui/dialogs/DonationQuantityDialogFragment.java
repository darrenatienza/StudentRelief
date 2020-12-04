package com.example.studentrelief.ui.dialogs;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.ReliefRequestDonationClient;

import org.androidannotations.annotations.AfterTextChange;
import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.web.client.RestClientException;

@EFragment
public class DonationQuantityDialogFragment extends DialogFragment {

    @RestService
    ReliefRequestDonationClient reliefRequestDonationClient;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String RELIEF_REQUEST_ID = "0";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private boolean validDonation;

    public DonationQuantityDialogFragment() {
        // Required empty public constructor
    }

    @AfterViews
    void afterViews(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = requireActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_donation_quantity, null))
                .setTitle(getResources().getString(R.string.dialog_title_password_reset))
                .setPositiveButton("Yes", (dialog, which) -> {
                    addNewReliefRequestDonation(dialog);
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .create();
        builder.create();
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


}
