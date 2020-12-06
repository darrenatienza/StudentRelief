package com.example.studentrelief.services.interfaces;

import com.example.studentrelief.BuildConfig;
import com.example.studentrelief.services.model.JsonArrayHolder;
import com.example.studentrelief.services.model.ReliefTaskModel;
import com.example.studentrelief.services.model.containers.ReliefTaskContainer;
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

public interface ReliefTaskClient {



    @Get("/records/relief_tasks?filter=title,cs,{criteria}")
    @RequiresCookie(Constants.SESSION_NAME)
    ReliefTaskContainer getAll(@Path String criteria);


    @Get("/records/relief_tasks?filter=active,cs,{activeValue}")
    @RequiresCookie(Constants.SESSION_NAME)
    JsonArrayHolder<ReliefTaskModel> getAllActive(@Path int activeValue);

    /** excludes auto generated column */
    @Post("/records/relief_tasks?exclude=relief_task_id,create_time_stamp")
    @RequiresCookie(Constants.SESSION_NAME)
    Integer addNew(@Body ReliefTaskModel model);

    /** excludes auto generated column */
    @Put("/records/relief_tasks/{id}?exclude=relief_task_id,create_time_stamp")
    @RequiresCookie(Constants.SESSION_NAME)
    Integer edit(@Path int id, @Body ReliefTaskModel model);

    @Delete("/records/relief_tasks/{id}")
    @RequiresCookie(Constants.SESSION_NAME)
    Integer delete(@Path int id);

    @Get("/records/relief_tasks/{id}")
    @RequiresCookie(Constants.SESSION_NAME)
    ReliefTaskModel get(@Path int id);

    void setCookie(String name, String value);
    String getCookie(String name);
}
