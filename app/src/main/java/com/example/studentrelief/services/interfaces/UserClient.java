package com.example.studentrelief.services.interfaces;

import com.example.studentrelief.BuildConfig;
import com.example.studentrelief.services.model.UserAddEditModel;
import com.example.studentrelief.services.model.UserModel;
import com.example.studentrelief.services.model.containers.DonationContainer;
import com.example.studentrelief.services.model.user.ActivateUserModel;
import com.example.studentrelief.services.model.user.RegisterUserModel;
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
    Integer addNew(@Body UserAddEditModel model);

    /** excludes auto generated column */
    @Post("/records/users?exclude=user_id,create_time_stamp")
    @RequiresCookie(Constants.SESSION_NAME)
    Integer add(@Body UserModel model);


    /** excludes auto generated column */
    @Put("/records/users/{id}?exclude=user_id,create_time_stamp")
    @RequiresCookie(Constants.SESSION_NAME)
    Integer edit(@Path int id, @Body UserModel model);

    /** excludes auto generated column */
    @Put("/records/users/{id}?exclude=user_id,create_time_stamp")
    @RequiresCookie(Constants.SESSION_NAME)
    Integer edit2(@Path int id, @Body UserAddEditModel model);


    @Delete("/records/users/{id}")
    @RequiresCookie(Constants.SESSION_NAME)
    Integer delete(@Path int id);

    @Get("/records/users/{id}?exclude=password")
    @RequiresCookie(Constants.SESSION_NAME)
    UserModel get(@Path int id);

    @Get("/records/users/{id}")
    @RequiresCookie(Constants.SESSION_NAME)
    UserAddEditModel getUserAddEditModel(@Path int id);

    @Post("/logout")
    @RequiresCookie(Constants.SESSION_NAME)
    void logout();

    void setCookie(String name, String value);
    String getCookie(String name);

    @Post("/register")
    @RequiresCookie(Constants.SESSION_NAME)
    UserModel register(@Body RegisterUserModel model);

    @Put("/records/users/{id}")
    @RequiresCookie(Constants.SESSION_NAME)
    Integer activate(@Path int id, @Body ActivateUserModel model);

    /** excludes auto generated column */
    @Put("/records/users/{id}?exclude=user_id,create_time_stamp,password")
    @RequiresCookie(Constants.SESSION_NAME)
    Integer activate(@Path int id, @Body UserModel model);
}
