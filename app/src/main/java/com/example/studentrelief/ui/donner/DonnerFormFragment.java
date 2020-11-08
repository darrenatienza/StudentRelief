package com.example.studentrelief.ui.donner;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Point;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.UiThread;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.DonnerClient;
import com.example.studentrelief.services.model.AddEditDonnerModel;
import com.example.studentrelief.services.model.DonnerModel;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.RootContext;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.web.client.RestClientException;

import cn.pedant.SweetAlert.SweetAlertDialog;


@EFragment
public class DonnerFormFragment extends DialogFragment {
    private DialogFragmentListener listener;
    @RestService
    DonnerClient donnerClient;

    public void setParentFragment(DonnerListFragment donnerListFragment) {
        listener = donnerListFragment;
    }

    // 1. Defines the listener interface with a method passing back data result.
    public interface DialogFragmentListener {
        /**action (save or delete) that successfully executed*/
        void onFinishAction();
    }

    int id;

    public void setId(int id) {
        this.id = id;
    }


    @ViewById
    Button btnSave;

    @ViewById
    TextView etFullName;

    @ViewById
    EditText etContactNumber;
    @ViewById
    EditText etAddress;

    @Click
    void btnSave(){
        try {
            String fullName = etFullName.getText().toString();
            String address = etAddress.getText().toString();
            String contactNumber = etContactNumber.getText().toString();

            AddEditDonnerModel model = new AddEditDonnerModel();
            model.setFull_name(fullName);
            model.setAddress(address);
            model.setContact_number(contactNumber);
            save(model);

            listener.onFinishAction();
        }catch (RestClientException ex){
            Toast.makeText(getActivity(),ex.getMessage(),Toast.LENGTH_SHORT).show();
        }catch (Exception ex){
            Toast.makeText(getActivity(),ex.getMessage(),Toast.LENGTH_LONG).show();
        }

    }
    @Click
    void btnDelete(){
        try {
            new SweetAlertDialog(getActivity(), SweetAlertDialog.WARNING_TYPE)
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
        Toast.makeText(getActivity(),ex.getMessage(),Toast.LENGTH_SHORT).show();
    }catch (Exception ex){
        Toast.makeText(getActivity(),ex.getMessage(),Toast.LENGTH_SHORT).show();
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

            id = 1;
            if (id > 0){
                donnerClient.edit(id,model);
            }else{
                donnerClient.addNew(model);
            }
            updateUIAfterSave();

    }
    @UiThread
    private void updateUIAfterSave() {
        listener.onFinishAction();
        dismiss();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        Dialog d = super.onCreateDialog(savedInstanceState);
        d.setTitle("Donner's Information");

        d.requestWindowFeature(STYLE_NORMAL);
        d.getContext().getTheme().applyStyle(R.style.dialog,true);
        return d;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_donner_form, container, false);
        return v;
    }
    @AfterViews
    void afterViews(){
        try{

            if(id > 0){
                getFormData();

            }
        }catch (Exception ex){
            Toast.makeText(getActivity(),ex.toString(),Toast.LENGTH_SHORT).show();
        }


    }

    @Background
    void getFormData() {
        if (id > 0){
            AddEditDonnerModel model   = donnerClient.getDonner(id);

            updateUIFormData(model);
        }
    }

    @UiThread
    void updateUIFormData(AddEditDonnerModel model) {
       //etAddress.setText(model.getAddress());
        //etContactNumber.setText(model.getContact_number());
        etFullName.setText(model.getFull_name());
    }

    @Override
    public void onResume() {
        //Setting additional layout for dialog fragment
        //ie. specific dialog size, layout gravity

        // Store access variables for window and blank point
        Window window = getDialog().getWindow();
        Point size = new Point();
        // Store dimensions of the screen in `size`
        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);
        // Set the width of the dialog proportional to 75% of the screen width
        window.setLayout((int) (size.x * 0.75), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        // Call super onResume after sizing
        super.onResume();
    }


}