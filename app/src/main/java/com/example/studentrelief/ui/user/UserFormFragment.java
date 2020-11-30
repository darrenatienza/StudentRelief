package com.example.studentrelief.ui.user;

import android.util.Log;
import android.view.MenuItem;

import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.studentrelief.R;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.OptionsMenuItem;

@OptionsMenu(R.menu.menu_form)
@EFragment(R.layout.fragment_user_form)
public class UserFormFragment extends Fragment {



    @AfterViews
    void afterView(){
        int val = getArguments().getInt("myArg");
        Log.d("value","" +val);
    }
    @OptionsMenuItem(R.id.action_settings)
    void onShowActionSetting(MenuItem menuItem){
        menuItem.setVisible(false);
    }

    @Click
    void button_first(){
        NavHostFragment.findNavController(UserFormFragment.this)
                .navigate(R.id.action_First2Fragment_to_Second2Fragment);
    }
}