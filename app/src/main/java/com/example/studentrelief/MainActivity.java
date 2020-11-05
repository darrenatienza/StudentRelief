package com.example.studentrelief;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.example.studentrelief.services.model.DonnerModel;
import com.example.studentrelief.ui.dialogs.AlertDialogFragment;
import com.example.studentrelief.ui.student.MyStudentRecyclerViewAdapter;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.springframework.web.client.RestClientException;

import java.util.List;

@EActivity
public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    @ViewById
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setSupportActionBar(toolbar);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_gallery, R.id.nav_slideshow,R.id.nav_student_list)
                .setDrawerLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }
    public void doPositiveClick(){
        Toast.makeText(this,"message",Toast.LENGTH_SHORT).show();
    }
    @Click(R.id.fab)
    void click(View view){
        loadListAsync();
        AlertDialogFragment d = new AlertDialogFragment();
        d.show(getSupportFragmentManager(),"Alert");

        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
    }
    @Background
    void loadListAsync(){
        try {




        } catch (RestClientException e){
            Log.e("Rest error",e.getMessage());

        }
    }
    @UiThread
    void loadList( List<DonnerModel> list){

        if (list.size() == 0){
            Toast.makeText(this,"No record found!",Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this,list.size(),Toast.LENGTH_SHORT).show();
        }



    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}