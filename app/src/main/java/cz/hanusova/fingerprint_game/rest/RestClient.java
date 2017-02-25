package cz.hanusova.fingerprint_game.rest;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Put;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.ArrayList;

import cz.hanusova.fingerprint_game.model.AppUser;
import cz.hanusova.fingerprint_game.model.Place;
import cz.hanusova.fingerprint_game.model.UserActivity;
import cz.hanusova.fingerprint_game.model.fingerprint.Fingerprint;
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

    @Get("/qr/{code}")
    Place getPlaceByCode(@Path String code);

    @Post("/activity/start?materialAmount={materialAmount}")
    AppUser startActivity(@Path Integer materialAmount, @Body Place place);

    @Put("/fingerprint/save")
    void sendFingerprint(@Body Fingerprint fingerprint);

}
