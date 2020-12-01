package com.example.studentrelief.ui.student;

import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.StudentClient;
import com.example.studentrelief.services.interfaces.UserClient;
import com.example.studentrelief.services.model.StudentListModel;
import com.example.studentrelief.services.model.StudentModel;
import com.example.studentrelief.services.model.UserModel;
import com.example.studentrelief.services.model.user.ActivateUserModel;
import com.example.studentrelief.ui.adapters.StudentAdapter;
import com.example.studentrelief.ui.misc.Constants;
import com.example.studentrelief.ui.misc.ItemClickSupport;
import com.example.studentrelief.ui.misc.MyPrefs_;
import com.example.studentrelief.ui.misc.SimpleDividerItemDecoration;
import com.example.studentrelief.ui.misc.VerticalSpaceItemDecoration;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.OnActivityResult;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.web.client.RestClientException;

import java.util.List;

@EFragment(R.layout.fragment_student_list)
public class StudentListFragment extends Fragment {

    static  final int SHOW_FORM = 101;
    @RestService
    StudentClient studentClient;
    @RestService
    UserClient userClient;
    @ViewById
    RecyclerView recyclerView;
    @ViewById
    TextInputEditText tvSearch;
    @ViewById
    TextInputLayout tiSearch;
    @Bean
    StudentAdapter adapter;
    @Pref
    MyPrefs_ myPrefs;
    private int selectectStudentID;
    private int userID;

    private void initAuthCookies() {
        String session = myPrefs.session().get();
        String name = Constants.SESSION_NAME;
        userClient.setCookie(name,session);
        studentClient.setCookie(name,session);
    }
    @AfterViews
    void afterViews() {
        initAuthCookies();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        VerticalSpaceItemDecoration dividerItemDecoration = new VerticalSpaceItemDecoration(15);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        recyclerView.setHasFixedSize(true);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        loadList();
        initItemClick();
        initSearch();
    }
    @UiThread
    void showActivateDialog() {

        new MaterialAlertDialogBuilder(getActivity())
                .setTitle(getResources().getString(R.string.dialog_student_activate_title))
                .setMessage(getResources().getString(R.string.dialog_student_activate_content_text))
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        activateStudentAsync();
                        dialog.dismiss();
                    }
                })
        .setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        })
        .show();
    }
    @Background
    void activateStudentAsync() {
        try {
            // create new user account for student
            ActivateUserModel model = new ActivateUserModel();
            model.setActive(true);
            userClient.activate(userID,model);
            updateUIAfterActivate();
        }catch (RestClientException ex){
            showError(ex.getMessage());
        }catch (Exception ex){
            showError(ex.getMessage());
        }
    }
    @Background
    void checkForUserActiveStatus(){
        try{
            StudentModel studentModel = studentClient.get(selectectStudentID);
            userID = studentModel.getUser_id();
            UserModel userModel = userClient.get(userID);
            boolean active = userModel.isActive();
            if(!active){
                showActivateDialog();
            }else{
                showAlreadyActivatedDialog();
            }
        }catch (RestClientException ex){
            showError(ex.getMessage());
        }
        catch (Exception ex){
            showError(ex.getMessage());
        }

    }
    @UiThread
    void showAlreadyActivatedDialog() {
        Toast.makeText(getActivity(),"Student user account is already activated!",Toast.LENGTH_SHORT).show();
    }

    @UiThread
    void updateUIAfterActivate() {
       loadList();
    }

    @UiThread
    void showError(String message) {
        Toast.makeText(getActivity(),"Error: " + message,Toast.LENGTH_SHORT).show();
    }

    private void initSearch() {
        tiSearch.setEndIconOnClickListener(v ->{
            loadList();
        });
    }


    private void initItemClick() {
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                // do it
                // set student as active

                TextView t = v.findViewById(R.id.idView);
                selectectStudentID = Integer.parseInt(t.getText().toString());
               checkForUserActiveStatus();
            }
        });
    }



    @Background
    void loadList(){
        try {
            String criteria = tvSearch.getText().toString();
            List<StudentListModel> models = studentClient.getAll(criteria).getRecords();
            updateList(models);
        }catch (Exception e){
            Log.e("Error",e.getMessage());
        }
    }
    @UiThread
    void updateList(List<StudentListModel> models) {
        adapter.setList(models);
        adapter.notifyDataSetChanged();
    }
    @Click(R.id.btnSearch)
    void search(){
        loadList();
    }

    @Click(R.id.fab)
    void click(View view){
        StudentFormActivity_.intent(getContext()).startForResult(SHOW_FORM);
    }




    // action after save or delete click on dialog form
    @OnActivityResult(SHOW_FORM)
    void onResult(int resultCode) {
        loadList();
        Log.d("Result",String.valueOf(resultCode));
    }
}