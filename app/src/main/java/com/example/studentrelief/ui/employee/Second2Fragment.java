package com.example.studentrelief.ui.employee;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.studentrelief.R;

import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;

@EFragment(R.layout.fragment_second2)
public class Second2Fragment extends Fragment {


    @Click
    void button_second(){
        Bundle bundle = new Bundle();
        bundle.putInt("myArg", 3);

        NavHostFragment.findNavController(Second2Fragment.this)
                .navigate(R.id.action_Second2Fragment_to_First2Fragment,bundle);
    }
}