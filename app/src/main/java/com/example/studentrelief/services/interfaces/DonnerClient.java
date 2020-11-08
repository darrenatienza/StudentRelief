package com.example.studentrelief.services.interfaces;

import com.example.studentrelief.BuildConfig;
import com.example.studentrelief.services.model.AddEditDonnerModel;
import com.example.studentrelief.services.model.DonnerContainer;
import com.example.studentrelief.services.model.DonnerModel;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Delete;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Put;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

@Rest(rootUrl = BuildConfig.BASE_URL,converters = { MappingJackson2HttpMessageConverter.class })
public interface DonnerClient {

    @Get("/records/donners?filter=full_name,cs,{criteria}")
    DonnerContainer getAll(@Path String criteria);

    @Post("/records/donners")
    Integer addNew(@Body AddEditDonnerModel model);

    @Put("/records/donners/{id}")
    Integer edit(@Path int id, @Body AddEditDonnerModel model);

    @Delete("/records/donners/{id}")
    Integer delete(@Path int id);

    @Get("/records/donners/{id}")
    AddEditDonnerModel getDonner(@Path int id);
}
