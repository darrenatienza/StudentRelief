package com.example.studentrelief.services.interfaces;

import com.example.studentrelief.BuildConfig;
import com.example.studentrelief.services.model.EmployeeModel;
import com.example.studentrelief.services.model.JsonArrayHolder;
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
public interface EmployeeClient {


    @Get("/records/employee_list_view?filter=full_name,cs,{criteria}")
    @RequiresCookie(Constants.SESSION_NAME)
    JsonArrayHolder<EmployeeModel> getAll(@Path String criteria);

    /**
     * excludes auto generated column
     */
    @Post("/records/employees?exclude=employee_id,create_time_stamp")
    @RequiresCookie(Constants.SESSION_NAME)
    Integer addNew(@Body EmployeeModel model);

    /**
     * excludes auto generated column
     */
    @Put("/records/employees/{id}?exclude=employee_id,create_time_stamp")
    @RequiresCookie(Constants.SESSION_NAME)
    Integer edit(@Path int id, @Body EmployeeModel model);


    @Delete("/records/employees/{id}")
    @RequiresCookie(Constants.SESSION_NAME)
    Integer delete(@Path int id);

    @Get("/records/employees/{id}")
    @RequiresCookie(Constants.SESSION_NAME)
    EmployeeModel get(@Path int id);


    void setCookie(String name, String value);

    String getCookie(String name);
}
