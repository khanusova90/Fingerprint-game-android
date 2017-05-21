package cz.hanusova.fingerprint_game.rest;

import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Post;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import cz.hanusova.fingerprint_game.model.AppUser;
import cz.hanusova.fingerprint_game.utils.Constants;

/**
 * Created by khanusova on 23.4.2017.
 */
@Rest(converters = {MappingJackson2HttpMessageConverter.class}, rootUrl = Constants.URL_BASE, interceptors = {AuthInterceptor.class, LogInterceptor.class, LoginInterceptor.class})
public interface LoginClient {

    @Post(value = "/login?username={username}")
    AppUser login(@Path String username);

}
