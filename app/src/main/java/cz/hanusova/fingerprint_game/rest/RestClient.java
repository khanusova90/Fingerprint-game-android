package cz.hanusova.fingerprint_game.rest;

import org.androidannotations.rest.spring.annotations.Body;
import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Put;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.List;

import cz.hanusova.fingerprint_game.model.AppUser;
import cz.hanusova.fingerprint_game.model.Item;
import cz.hanusova.fingerprint_game.model.Place;
import cz.hanusova.fingerprint_game.model.Ranking;
import cz.hanusova.fingerprint_game.model.fingerprint.Fingerprint;
import cz.hanusova.fingerprint_game.base.utils.Constants;

/**
 * Created by khanusova on 7.9.2016.
 * <p>
 * Rest client for communication with server
 */
@Rest(converters = {MappingJackson2HttpMessageConverter.class}, rootUrl = Constants.URL_BASE, interceptors = {AuthInterceptor.class, LogInterceptor.class})
public interface RestClient {

    @Get("/qr/{code}")
    Place getPlaceByCode(@Path String code);

    @Post("/activity/start?materialAmount={materialAmount}")
    AppUser startActivity(@Path Integer materialAmount, @Body Place place);

    @Put("/fingerprint/save")
    void sendFingerprint(@Body Fingerprint fingerprint);

    @Get("/update/itemType")
    List<Item> getItemTypes();

    @Get("/activity/getItems")
    List<Item> getPossibleItems();

    @Post("/activity/buy")
    AppUser buyItem(@Body List<Item> items);

    @Get("/ranking/get")
    List<Ranking> getRankings();
}
