package com.example.studentrelief.services.interfaces;

import com.example.studentrelief.BuildConfig;
import com.example.studentrelief.services.model.DonationContainer;
import com.example.studentrelief.services.model.DonationModel;
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
public interface DonationClient {
    @Get("/records/donations?filter=name,cs,{criteria}")
    DonationContainer getAll(@Path String criteria);
    /** excludes auto generated column */
    @Post("/records/donations?exclude=donation_id,create_time_stamp")
    Integer addNew(@Body DonationModel model);

    /** excludes auto generated column */
    @Put("/records/donations/{id}?exclude=donation_id,create_time_stamp")
    Integer edit(@Path int id, @Body DonationModel model);

    @Delete("/records/donations/{id}")
    Integer delete(@Path int id);

    @Get("/records/donations/{id}")
    DonationModel get(@Path int id);
}
