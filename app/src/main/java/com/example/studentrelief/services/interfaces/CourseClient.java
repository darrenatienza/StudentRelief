package com.example.studentrelief.services.interfaces;

import com.example.studentrelief.BuildConfig;
import com.example.studentrelief.services.model.AddEditDonnerModel;
import com.example.studentrelief.services.model.CourseModel;
import com.example.studentrelief.services.model.DashboardModel;
import com.example.studentrelief.services.model.DonationModel;
import com.example.studentrelief.services.model.JsonArrayHolder;
import com.example.studentrelief.ui.misc.Constants;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Delete;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Put;
import org.androidannotations.rest.spring.annotations.RequiresCookie;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Rest(rootUrl = BuildConfig.BASE_URL,converters = { MappingJackson2HttpMessageConverter.class })
public interface CourseClient {

    @Get("/records/courses/{criteria}")
    @RequiresCookie(Constants.SESSION_NAME)
    JsonArrayHolder<CourseModel> getAll(@Path String criteria);

    @Get("/records/courses/{id}")
    @RequiresCookie(Constants.SESSION_NAME)
    CourseModel get(@Path int id);

    @Post("/records/courses?exclude=course_id,create_time_stamp")
    @RequiresCookie(Constants.SESSION_NAME)
    Integer addNew(@Body CourseModel model);

    /** excludes auto generated column */
    @Put("/records/courses/{id}?exclude=course_id,create_time_stamp")
    @RequiresCookie(Constants.SESSION_NAME)
    Integer edit(@Path int id, @Body CourseModel model);

    @Delete("/records/courses/{id}")
    @RequiresCookie(Constants.SESSION_NAME)
    Integer delete(@Path int id);

    void setCookie(String name, String value);
    String getCookie(String name);
}
