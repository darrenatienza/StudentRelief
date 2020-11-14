package com.example.studentrelief.services.interfaces;

import com.example.studentrelief.BuildConfig;
import com.example.studentrelief.services.model.DonationTaskModel;
import com.example.studentrelief.services.model.containers.DonationContainer;
import com.example.studentrelief.services.model.DonationModel;
import com.example.studentrelief.services.model.containers.DonationTaskContainer;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Delete;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Put;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Rest(rootUrl = BuildConfig.BASE_URL,converters = { MappingJackson2HttpMessageConverter.class })

public interface DonationTaskClient {
    @Get("/records/donation_task?filter=title,cs,{criteria}")
    DonationTaskContainer getAll(@Path String criteria);
    /** excludes auto generated column */
    @Post("/records/donation_task?exclude=donation_id,create_time_stamp")
    Integer addNew(@Body DonationTaskModel model);

    /** excludes auto generated column */
    @Put("/records/donation_task/{id}?exclude=donation_id,create_time_stamp")
    Integer edit(@Path int id, @Body DonationTaskModel model);

    @Delete("/records/donation_task/{id}")
    Integer delete(@Path int id);

    @Get("/records/donation_task/{id}")
    DonationTaskModel get(@Path int id);
}
