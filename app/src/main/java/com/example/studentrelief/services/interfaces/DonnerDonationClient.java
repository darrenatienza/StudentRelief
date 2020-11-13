package com.example.studentrelief.services.interfaces;

import com.example.studentrelief.BuildConfig;
import com.example.studentrelief.services.model.DonationContainer;
import com.example.studentrelief.services.model.DonationModel;
import com.example.studentrelief.services.model.DonnerDonationContainer;
import com.example.studentrelief.services.model.DonnerDonationModel;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Delete;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Put;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Rest(rootUrl = BuildConfig.BASE_URL,converters = { MappingJackson2HttpMessageConverter.class })
public interface DonnerDonationClient {
    @Get("/records/donners_donations?filter=donation_date,cs,{date}")
    DonnerDonationContainer getAll(@Path String date);
    /** excludes auto generated column */
    @Post("/records/donners_donations?exclude=donners_donations_id,create_time_stamp")
    Integer addNew(@Body DonnerDonationModel model);

    /** excludes auto generated column */
    @Put("/records/donners_donations/{id}?exclude=donners_donations_id,create_time_stamp")
    Integer edit(@Path int id, @Body DonnerDonationModel model);

    @Delete("/records/donners_donations/{id}")
    Integer delete(@Path int id);

    @Get("/records/donners_donations/{id}")
    DonnerDonationModel get(@Path int id);

}
