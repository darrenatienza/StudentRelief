package com.example.studentrelief.services.interfaces;

import com.example.studentrelief.BuildConfig;
import com.example.studentrelief.services.model.ReliefRequestDonationContainer;
import com.example.studentrelief.services.model.ReliefRequestDonationModel;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Delete;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Put;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Rest(rootUrl = BuildConfig.BASE_URL,converters = { MappingJackson2HttpMessageConverter.class })
public interface ReliefRequestDonationClient {
    @Get("/records/student_reliefs?filter=is_release,eq,{release}")
    ReliefRequestDonationContainer getAll(@Path int release);

    /** excludes auto generated column */
    @Post("/records/relief_request_donations?exclude=relief_request_donation_id,create_time_stamp")
    Integer addNew(@Body ReliefRequestDonationModel model);

    /** excludes auto generated column */
    @Put("/records/relief_request_donations/{id}?exclude=relief_request_donation_id,create_time_stamp")
    Integer edit(@Path int id, @Body ReliefRequestDonationModel model);

    @Delete("/records/relief_request_donations/{id}")
    Integer delete(@Path int id);

    @Get("/records/relief_request_donations/{id}")
    ReliefRequestDonationModel get(@Path int id);

    @Get("/records/relief_request_donation_view?filter=relief_request_id,eq,{relief_request_id}")
    ReliefRequestDonationContainer getAllByID(@Path int relief_request_id);
}
