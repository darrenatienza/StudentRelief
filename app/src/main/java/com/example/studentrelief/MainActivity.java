package com.example.studentrelief;

import android.app.FragmentManager;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;
import org.androidannotations.annotations.ViewById;

@OptionsMenu(R.menu.main)
@EActivity
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    @ViewById
    Toolbar toolbar;

    @ViewById(R.id.nav_view)
    NavigationView navigationView;

    @ViewById(R.id.drawer_layout)

    DrawerLayout drawer;
    private FragmentManager fm;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // cannot include on annotation
        setContentView(R.layout.activity_main);


    }

    @AfterViews
    void afterViews(){
        setSupportActionBar(toolbar);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_student_list,R.id.nav_donners_list,R.id.nav_volunteer_list,
                R.id.nav_donation_list, R.id.nav_donners_donation_list, R.id.nav_relief_task_list,R.id.nav_employee)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);


    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

}