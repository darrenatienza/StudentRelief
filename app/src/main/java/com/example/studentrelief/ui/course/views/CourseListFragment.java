package com.example.studentrelief.ui.course.views;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.widget.TextView;

import com.example.studentrelief.MainActivityViewModel;
import com.example.studentrelief.R;
import com.example.studentrelief.services.interfaces.CourseClient;
import com.example.studentrelief.services.model.CourseModel;
import com.example.studentrelief.ui.adapters.CourseAdapter;
import com.example.studentrelief.ui.course.CourseViewModel;
import com.example.studentrelief.ui.misc.Constants;
import com.example.studentrelief.ui.misc.ItemClickSupport;
import com.example.studentrelief.ui.misc.MyPrefs_;
import com.example.studentrelief.ui.misc.SimpleDividerItemDecoration;
import com.example.studentrelief.ui.misc.VerticalSpaceItemDecoration;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Background;
import org.androidannotations.annotations.Bean;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EFragment;
import org.androidannotations.annotations.UiThread;
import org.androidannotations.annotations.ViewById;
import org.androidannotations.annotations.sharedpreferences.Pref;
import org.androidannotations.rest.spring.annotations.RestService;
import org.springframework.web.client.RestClientException;

import java.util.List;

@EFragment(R.layout.fragment_course_list)
public class CourseListFragment extends Fragment {

    private static final String COURSE_ID = "courseID";
    @Pref
    MyPrefs_ myPrefs;

    @RestService
    CourseClient courseClient;
    //private CourseListViewModel mViewModel;
    private CourseViewModel mViewModel;
    @ViewById
    RecyclerView recyclerView;
    @Bean
    CourseAdapter adapter;
    @ViewById
    TextInputLayout tiSearch;
    @ViewById
    TextInputEditText searchInput;
    private MainActivityViewModel mMainActivityViewModel;

    public void initAuthCookies() {
        String session = myPrefs.session().get();
        String name = Constants.SESSION_NAME;
        courseClient.setCookie(name,session);
    }


    @AfterViews
    void afterViews(){
        initAuthCookies();

        initRecyclerView();
        initItemClick();

        initSearch();
        initObservers();

    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setHasFixedSize(true);
        VerticalSpaceItemDecoration dividerItemDecoration = new VerticalSpaceItemDecoration(15);
        recyclerView.addItemDecoration(new SimpleDividerItemDecoration(getContext()));
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
    }
    private void initSearch() {
        tiSearch.setEndIconOnClickListener(v ->{
           mViewModel.willReloadCourses().setValue(true);
        });
    }
    private void initItemClick() {
        ItemClickSupport.addTo(recyclerView).setOnItemClickListener((recyclerView, position, v) -> {
            // do it
            TextView t = v.findViewById(R.id.textView_ID);
            int id = Integer.parseInt(t.getText().toString());
            //Todo: show form after click
            Bundle bundle = new Bundle();
            bundle.putInt(COURSE_ID, id);
            NavHostFragment.findNavController(CourseListFragment.this)
                    .navigate(R.id.action_fragment_course_list_to_fragment_course_form,bundle);
        });
    }
    @UiThread
    void updateCoursesLiveData(List<CourseModel> modelList) {
      mViewModel.setCourses(modelList);
    }

    private void initObservers() {
        //mViewModel = new ViewModelProvider(getParentFragment()).get(CourseListViewModel.class);
        mViewModel = new ViewModelProvider(getParentFragment()).get(CourseViewModel.class);
        mMainActivityViewModel = new ViewModelProvider(getActivity()).get(MainActivityViewModel.class);
        mMainActivityViewModel.getAppTitle().setValue("Courses");

        mViewModel.willReloadCourses().observe(getViewLifecycleOwner(), willReload ->{
            if(willReload) {
                reloadCourseData();
            }
        } );
        mViewModel.getCourses().observe(getViewLifecycleOwner(), courses ->{
                Log.d("record count ", String.valueOf(courses.size()));
                adapter.setList(courses);
                adapter.notifyDataSetChanged();
        } );
    }

    @Click
    void fab_Add(){
        Bundle bundle = new Bundle();
        bundle.putInt(COURSE_ID, 0);
        NavHostFragment.findNavController(CourseListFragment.this)
                .navigate(R.id.action_fragment_course_list_to_fragment_course_form,bundle);
    }

    @Background
    void reloadCourseData() {
        try{
            String search = searchInput.getText().toString();
            List<CourseModel> modelList = courseClient.getAll(search).getRecords();
            if(modelList.size() > 0){
                updateCoursesLiveData(modelList);
            }
        }catch (RestClientException e){
            Log.e("Rest Client Error", e.toString());
        }

    }




}