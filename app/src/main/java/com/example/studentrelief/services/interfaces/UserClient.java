package com.example.studentrelief.services.interfaces;

import com.example.studentrelief.BuildConfig;
import com.example.studentrelief.services.model.UserModel;
import com.example.studentrelief.services.model.containers.DonationContainer;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Delete;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Put;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Rest(rootUrl = BuildConfig.USER_URL,converters = { MappingJackson2HttpMessageConverter.class })
public interface UserClient {
    @Get("/records/users?filter=name,cs,{criteria}")
    DonationContainer getAll(@Path String criteria);

    /** excludes auto generated column */
    @Post("/login")
    UserModel login(@Body UserModel model);

    /** excludes auto generated column */
    @Post("/records/users?exclude=user_id,create_time_stamp")
    Integer addNew(@Body UserModel model);

    /** excludes auto generated column */
    @Put("/records/users/{id}?exclude=donation_id,create_time_stamp")
    Integer edit(@Path int id, @Body UserModel model);

    @Delete("/records/users/{id}")
    Integer delete(@Path int id);

    @Get("/records/users/{id}")
    UserModel get(@Path int id);
}
