package com.example.studentrelief.services.interfaces;

import com.example.studentrelief.BuildConfig;
import com.example.studentrelief.services.model.StudentContainer;
import com.example.studentrelief.services.model.StudentModel;
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
public interface StudentClient  {

    @Get("/records/students?filter=full_name,cs,{criteria}")
    @RequiresCookie(Constants.SESSION_NAME)
    StudentContainer getAll(@Path String criteria);

    @Post("/records/students?exclude=student_id,create_time_stamp")
    @RequiresCookie(Constants.SESSION_NAME)
    Integer addNew(@Body StudentModel model);

    @Put("/records/students/{id}?exclude=student_id,create_time_stamp")
    @RequiresCookie(Constants.SESSION_NAME)
    Integer edit(@Path int id, @Body StudentModel model);

    @Delete("/records/students/{id}")
    @RequiresCookie(Constants.SESSION_NAME)
    Integer delete(@Path int id);

    @Get("/records/students/{id}")
    @RequiresCookie(Constants.SESSION_NAME)
    StudentModel get(@Path int id);

    @Get("/records/students?filter=full_name,eq,{full_name}")
    @RequiresCookie(Constants.SESSION_NAME)
    StudentContainer getByFullName(@Path String full_name);


    void setCookie(String name, String value);
    String getCookie(String name);
}
