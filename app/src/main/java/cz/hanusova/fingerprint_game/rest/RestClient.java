package cz.hanusova.fingerprint_game.rest;

import android.app.Activity;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import cz.hanusova.fingerprint_game.model.AppUser;
import cz.hanusova.fingerprint_game.model.Place;
import cz.hanusova.fingerprint_game.utils.Constants;

/**
 * Created by khanusova on 7.9.2016.
 *
 * Rest client for communication with server
 */
@Rest(converters = {MappingJackson2HttpMessageConverter.class}, rootUrl = Constants.URL_BASE, interceptors = {AuthInterceptor.class, LoggingInterceptor.class})
public interface RestClient {

    @Post("/login?username={username}")
    AppUser login(@Path String username);

    @Post("place/addActivity?username={username}")
    void addActivity(@Path String username, @Body Place place);

    @Get("/qr/{code}") //TODO: v qr ulozen jen kod mista?
    Place getPlaceByCode(@Path String code);

}
