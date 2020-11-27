package com.example.studentrelief.services.interfaces;

import com.example.studentrelief.BuildConfig;
import com.example.studentrelief.services.model.UserAddModel;
import com.example.studentrelief.services.model.UserModel;
import com.example.studentrelief.services.model.containers.DonationContainer;
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

@Rest(rootUrl = BuildConfig.USER_URL,converters = { MappingJackson2HttpMessageConverter.class })
public interface UserClient {
    @Get("/records/users?filter=name,cs,{criteria}")
    @RequiresCookie(Constants.SESSION_NAME)
    DonationContainer getAll(@Path String criteria);

    /** excludes auto generated column */
    @Post("/login")
    UserModel login(@Body UserModel model);

    /** excludes auto generated column */
    @Post("/records/users?exclude=user_id,create_time_stamp")
    @RequiresCookie(Constants.SESSION_NAME)
    Integer addNew(@Body UserAddModel model);

    /** excludes auto generated column */
    @Put("/records/users/{id}?exclude=user_id,create_time_stamp")
    @RequiresCookie(Constants.SESSION_NAME)
    Integer edit(@Path int id, @Body UserModel model);

    @Delete("/records/users/{id}")
    @RequiresCookie(Constants.SESSION_NAME)
    Integer delete(@Path int id);

    @Get("/records/users/{id}")
    @RequiresCookie(Constants.SESSION_NAME)
    UserModel get(@Path int id);

    @Post("/logout")
    @RequiresCookie(Constants.SESSION_NAME)
    void logout();

    void setCookie(String name, String value);
    String getCookie(String name);


}
