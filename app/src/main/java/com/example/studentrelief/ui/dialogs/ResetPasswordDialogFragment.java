package com.example.studentrelief.ui.dialogs;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.example.studentrelief.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EFragment;

@EFragment
public class ResetPasswordDialogFragment extends DialogFragment {


    @AfterViews
    void afterViews(){
        Log.d("id","" +getArguments().getInt("userID"));
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View v = getLayoutInflater().inflate(R.layout.dialog_password, null);
        return new MaterialAlertDialogBuilder(getActivity())
                .setTitle(getResources().getString(R.string.dialog_title_password_reset))
                .setMessage(getString(R.string.dialog_message_password_reset))
                .setPositiveButton("Yes", (dialog, which) -> {
                    //
                    dialog.dismiss();})
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .create();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        return super.onCreateView(inflater,container,savedInstanceState);
    }

}
