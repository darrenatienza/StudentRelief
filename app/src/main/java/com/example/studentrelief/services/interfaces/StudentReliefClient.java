package com.example.studentrelief.services.interfaces;

import com.example.studentrelief.BuildConfig;
import com.example.studentrelief.services.model.DonnerDonationContainer;
import com.example.studentrelief.services.model.DonnerDonationModel;
import com.example.studentrelief.services.model.StudentReliefContainer;
import com.example.studentrelief.services.model.StudentReliefModel;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Delete;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Put;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Rest(rootUrl = BuildConfig.BASE_URL,converters = { MappingJackson2HttpMessageConverter.class })
public interface StudentReliefClient {
    @Get("/records/student_reliefs?filter=is_release,eq,{release}")
    StudentReliefContainer getAll(@Path int release);

    /** excludes auto generated column */
    @Post("/records/student_reliefs?exclude=donners_donations_id,create_time_stamp")
    Integer addNew(@Body StudentReliefModel model);

    /** excludes auto generated column */
    @Put("/records/student_reliefs/{id}?exclude=donners_donations_id,create_time_stamp")
    Integer edit(@Path int id, @Body StudentReliefModel model);

    @Delete("/records/student_reliefs/{id}")
    Integer delete(@Path int id);

    @Get("/records/student_reliefs/{id}")
    StudentReliefModel get(@Path int id);

}
