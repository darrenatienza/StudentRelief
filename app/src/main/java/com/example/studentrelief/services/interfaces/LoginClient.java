package com.example.studentrelief.services.interfaces;

import com.example.studentrelief.BuildConfig;
import com.example.studentrelief.services.model.LoginModel;
import com.example.studentrelief.services.model.UserModel;
import com.example.studentrelief.ui.misc.Constants;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.RequiresCookie;
import org.androidannotations.rest.spring.annotations.Rest;
import org.androidannotations.rest.spring.annotations.SetsCookie;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Rest(rootUrl = BuildConfig.BASE_URL,converters = { MappingJackson2HttpMessageConverter.class })
public interface LoginClient {

    /** excludes auto generated column */
    @Post("/login")
    @SetsCookie({Constants.SESSION_NAME}) // retrieves cookie from http request
    UserModel login(@Body LoginModel model);

    void setCookie(String name, String value);
    String getCookie(String name);
}
