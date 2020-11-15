package com.example.studentrelief.services.interfaces;

import com.example.studentrelief.BuildConfig;
import com.example.studentrelief.services.model.ReliefTaskModel;
import com.example.studentrelief.services.model.containers.ReliefTaskContainer;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Delete;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Put;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Rest(rootUrl = BuildConfig.BASE_URL,converters = { MappingJackson2HttpMessageConverter.class })

public interface ReliefTaskClient {
    @Get("/records/relief_tasks?filter=title,cs,{criteria}")
    ReliefTaskContainer getAll(@Path String criteria);

    @Get("/records/relief_tasks?filter=active,cs,1")
    ReliefTaskContainer getAllActive();

    /** excludes auto generated column */
    @Post("/records/relief_tasks?exclude=donation_id,create_time_stamp")
    Integer addNew(@Body ReliefTaskModel model);

    /** excludes auto generated column */
    @Put("/records/relief_tasks/{id}?exclude=donation_id,create_time_stamp")
    Integer edit(@Path int id, @Body ReliefTaskModel model);

    @Delete("/records/relief_tasks/{id}")
    Integer delete(@Path int id);

    @Get("/records/relief_tasks/{id}")
    ReliefTaskModel get(@Path int id);
}
