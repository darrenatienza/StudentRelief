package com.example.studentrelief.services.interfaces;

import com.example.studentrelief.BuildConfig;
import com.example.studentrelief.services.model.CourseModel;
import com.example.studentrelief.services.model.DashboardModel;
import com.example.studentrelief.services.model.JsonArrayHolder;
import com.example.studentrelief.ui.misc.Constants;

import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.RequiresCookie;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Rest(rootUrl = BuildConfig.BASE_URL,converters = { MappingJackson2HttpMessageConverter.class })
public interface CourseClient {

    @Get("/records/courses")
    @RequiresCookie(Constants.SESSION_NAME)
    JsonArrayHolder<CourseModel> get();

    void setCookie(String name, String value);
    String getCookie(String name);
}
