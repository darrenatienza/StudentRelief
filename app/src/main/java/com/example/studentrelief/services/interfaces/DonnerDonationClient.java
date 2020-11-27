package com.example.studentrelief.services.interfaces;

import com.example.studentrelief.BuildConfig;
import com.example.studentrelief.services.model.DonnerDonationContainer;
import com.example.studentrelief.services.model.DonnerDonationModel;
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
public interface DonnerDonationClient {
    @Get("/records/donners_donations_view?filter1=donner_full_name,cs,{criteria}&filter2=donation_name,cs,{criteria}")
    @RequiresCookie(Constants.SESSION_NAME)
    DonnerDonationContainer getAll(@Path String criteria);

    @Get("/records/donners_donations_view?filter=donners_donations_id,eq,{id}")
    @RequiresCookie(Constants.SESSION_NAME)
    DonnerDonationContainer getByDonnersDonationID(@Path int id);

    /** excludes auto generated column */
    @Post("/records/donners_donations?exclude=donners_donations_id,create_time_stamp")
    @RequiresCookie(Constants.SESSION_NAME)
    Integer addNew(@Body DonnerDonationModel model);

    /** excludes auto generated column */
    @Put("/records/donners_donations/{id}?exclude=donners_donations_id,create_time_stamp")
    @RequiresCookie(Constants.SESSION_NAME)
    Integer edit(@Path int id, @Body DonnerDonationModel model);

    @Delete("/records/donners_donations/{id}")
    @RequiresCookie(Constants.SESSION_NAME)
    Integer delete(@Path int id);

    @Get("/records/donners_donations/{id}")
    @RequiresCookie(Constants.SESSION_NAME)
    DonnerDonationModel get(@Path int id);

    void setCookie(String name, String value);
    String getCookie(String name);

}
