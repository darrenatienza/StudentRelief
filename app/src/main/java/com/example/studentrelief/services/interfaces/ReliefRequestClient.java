package com.example.studentrelief.services.interfaces;

import com.example.studentrelief.BuildConfig;
import com.example.studentrelief.services.model.ReliefRequestContainer;
import com.example.studentrelief.services.model.ReliefRequestModel;
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

public interface ReliefRequestClient {
    @Get("/records/relief_requests?filter=title,cs,{criteria}")
    ReliefRequestContainer getAll(@Path String criteria);

    /** excludes auto generated column */
    @Post("/records/relief_requests?exclude=relief_request_id,date_release,create_time_stamp")
    Integer addNew(@Body ReliefRequestModel model);

    /** excludes auto generated column */
    @Put("/records/relief_requests/{id}?exclude=relief_request_id,create_time_stamp")
    Integer edit(@Path int id, @Body ReliefRequestModel model);

    @Delete("/records/relief_requests/{id}")
    Integer delete(@Path int id);

    @Get("/records/relief_requests/{id}")
    ReliefRequestModel get(@Path int id);
}
