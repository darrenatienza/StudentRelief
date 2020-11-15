package com.example.studentrelief.ui.student;


import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.ReliefTaskClient;
import com.example.studentrelief.services.interfaces.StudentClient;
import com.example.studentrelief.services.model.AddEditDonnerModel;
import com.example.studentrelief.services.model.ReliefTaskModel;
import com.example.studentrelief.services.model.StudentModel;
import com.example.studentrelief.ui.adapters.ReliefTaskAdapter;
import com.example.studentrelief.ui.adapters.StudentReliefTaskAdapter;
import com.example.studentrelief.ui.misc.ItemClickSupport;
import com.example.studentrelief.ui.misc.RecyclerViewClickListener;
import com.example.studentrelief.ui.misc.VerticalSpaceItemDecoration;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.Extra;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.rest.spring.annotations.RestService;

import java.util.List;


@EActivity(R.layout.activity_student_panel)
public class StudentPanelActivity extends AppCompatActivity implements RecyclerViewClickListener {

    @RestService
    StudentClient studentClient;
    @RestService
    ReliefTaskClient reliefTaskClient;


    @Extra
    int id = 1;


    @ViewById
    Toolbar toolbar;

    @ViewById
    TextView tvSrCode;

    @ViewById
    TextView tvFullName;
    @ViewById
    TextView tvAddress;
    @ViewById
    TextView tvContactNumber;
    @ViewById
    TextView tvCampus;

    @Bean
    StudentReliefTaskAdapter adapter;
    @ViewById
    RecyclerView recyclerView;

    @AfterViews
    void afterViews(){

        try{
            setSupportActionBar(toolbar);
            if(id > 0){
                getFormData();
                LinearLayoutManager layoutManager = new LinearLayoutManager(this);
                VerticalSpaceItemDecoration dividerItemDecoration = new VerticalSpaceItemDecoration(15);
                recyclerView.addItemDecoration(dividerItemDecoration);
                recyclerView.setHasFixedSize(true);
                recyclerView.setLayoutManager(layoutManager);
                recyclerView.setAdapter(adapter);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                loadList();
                initItemClick();
            }
        }catch (Exception ex){
            Toast.makeText(this,ex.toString(),Toast.LENGTH_SHORT).show();
        }


    }

    private void initItemClick() {
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                // do it
                TextView t = v.findViewById(R.id.tvID);


                int id = Integer.parseInt(t.getText().toString());
                validateItemForEdit(id);
            }
        });
    }
    @Background
    void getFormData() {
        if (id > 0){
            StudentModel model   = studentClient.get(id);
            updateUIFormData(model);
        }
    }
    @Background
    void validateItemForEdit(int id) {
        ReliefTaskModel reliefTaskModel = reliefTaskClient.get(id);
        if(reliefTaskModel.getActive()) {
            //showFormDialog(id);
        }else{
            //showNotApplicableForEditMessage();
        }

    }
    @UiThread
    void updateUIFormData(StudentModel model) {
        tvSrCode.setText(model.getSr_code());
        tvFullName.setText(model.getFull_name());
        tvAddress.setText(model.getAddress());
        tvContactNumber.setText(model.getContact_number());
        tvCampus.setText(model.getCampus());

    }

    @Background
    void loadList(){
        try {
            List<ReliefTaskModel> models = reliefTaskClient.getAllActive().getRecords();

            /** New models (modified model) must be pass not the original models*/
            updateList(models);
        }catch (Exception e){
            Log.e("Error",e.getMessage());
        }
    }
    @UiThread
    void updateList(List<ReliefTaskModel> models) {
        adapter.setList(models,this);
        adapter.notifyDataSetChanged();
    }


    /**Use to get on click event of button from recycler view*/
    @Override
    public void onClick(View view, int position) {


        Log.d("ID", position +"");
    }
}