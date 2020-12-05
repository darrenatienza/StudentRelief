package com.example.studentrelief.services.interfaces;

import com.example.studentrelief.BuildConfig;
import com.example.studentrelief.services.model.JsonArrayHolder;
import com.example.studentrelief.services.model.ReliefRequestContainer;
import com.example.studentrelief.services.model.ReliefRequestModel;
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

public interface ReliefRequestClient {
    @Get("/records/relief_requests?filter=title,cs,{criteria}")
    ReliefRequestContainer getAll(@Path String criteria);

    /** excludes auto generated column */
    @Post("/records/relief_requests?exclude=relief_request_id,date_release,create_time_stamp")
    @RequiresCookie(Constants.SESSION_NAME)
    Integer addNew(@Body ReliefRequestModel model);

    /** excludes auto generated column */
    @Put("/records/relief_requests/{id}?exclude=relief_request_id,create_time_stamp")
    @RequiresCookie(Constants.SESSION_NAME)
    Integer edit(@Path int id, @Body ReliefRequestModel model);

    @Delete("/records/relief_requests/{id}")
    @RequiresCookie(Constants.SESSION_NAME)
    Integer delete(@Path int id);

    @Get("/records/relief_requests/{id}")
    @RequiresCookie(Constants.SESSION_NAME)
    ReliefRequestModel get(@Path int id);

    @Get("/records/relief_requests_view?filter=relief_task_id,eq,{reliefTaskID}&filter=student_full_name,cs,{search}")
    @RequiresCookie(Constants.SESSION_NAME)
    ReliefRequestContainer getAllByID(@Path int reliefTaskID, @Path String search);

    // cookie handler authentication
    void setCookie(String name, String value);
    String getCookie(String name);

    @Get("/records/relief_requests_view?filter=relief_task_id,eq,{reliefTaskID}&filter=student_full_name,cs,{search}&filter=released,eq,{released}")
    @RequiresCookie(Constants.SESSION_NAME)
    JsonArrayHolder<ReliefRequestModel> getAll(@Path int reliefTaskID, @Path String search, @Path int released);
}
