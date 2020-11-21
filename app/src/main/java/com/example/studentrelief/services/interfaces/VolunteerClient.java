package com.example.studentrelief.services.interfaces;

import com.example.studentrelief.BuildConfig;
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
    /** excludes auto generated column */
    @Post("/records/volunteers?exclude=volunteer_id,create_time_stamp")
    Integer addNew(@Body VolunteerModel model);

    /** excludes auto generated column */
    @Put("/records/volunteers/{id}?exclude=volunteer_id,create_time_stamp")
    Integer edit(@Path int id, @Body VolunteerModel model);

    @Delete("/records/volunteers/{id}")
    Integer delete(@Path int id);

    @Get("/records/volunteers/{id}")
    VolunteerModel get(@Path int id);

    @Get("/records/volunteers??filter=full_name,cs,{full_name}")
    VolunteerContainer getByFullName(@Path String full_name);
}
