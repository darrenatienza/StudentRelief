package com.example.studentrelief.services.interfaces;

import com.example.studentrelief.BuildConfig;
import com.example.studentrelief.services.model.StudentContainer;
import com.example.studentrelief.services.model.StudentModel;
import com.example.studentrelief.services.model.VolunteerContainer;
import com.example.studentrelief.services.model.VolunteerModel;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Delete;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Put;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Rest(rootUrl = BuildConfig.BASE_URL,converters = { MappingJackson2HttpMessageConverter.class })
public interface VolunteerClient {

    @Get("/records/volunteers?filter=full_name,cs,{criteria}")
    VolunteerContainer getAll(@Path String criteria);

    @Post("/records/volunteers")
    Integer addNew(@Body VolunteerModel model);

    @Put("/records/volunteers/{id}")
    Integer edit(@Path int id, @Body VolunteerModel model);

    @Delete("/records/volunteers/{id}")
    Integer delete(@Path int id);

    @Get("/records/volunteers/{id}")
    VolunteerModel get(@Path int id);
}
