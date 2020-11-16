package com.example.studentrelief.services.interfaces;

import com.example.studentrelief.BuildConfig;
import com.example.studentrelief.services.model.AddEditDonnerModel;
import com.example.studentrelief.services.model.DonnerContainer;
import com.example.studentrelief.services.model.StudentContainer;
import com.example.studentrelief.services.model.StudentModel;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Delete;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Put;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Rest(rootUrl = BuildConfig.BASE_URL,converters = { MappingJackson2HttpMessageConverter.class })
public interface StudentClient  {

    @Get("/records/students?filter=full_name,cs,{criteria}")
    StudentContainer getAll(@Path String criteria);

    @Post("/records/students?exclude=student_id,create_time_stamp")
    Integer addNew(@Body StudentModel model);

    @Put("/records/students/{id}?exclude=student_id,create_time_stamp")
    Integer edit(@Path int id, @Body StudentModel model);

    @Delete("/records/students/{id}")
    Integer delete(@Path int id);

    @Get("/records/students/{id}")
    StudentModel get(@Path int id);
}
