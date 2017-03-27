package cz.hanusova.fingerprint_game.rest;

import org.androidannotations.rest.spring.annotations.Get;
import org.androidannotations.rest.spring.annotations.Path;
import org.androidannotations.rest.spring.annotations.Rest;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.util.List;

import cz.hanusova.fingerprint_game.model.StagTimetable;
import cz.hanusova.fingerprint_game.model.StagUser;
import cz.hanusova.fingerprint_game.utils.Constants;

/**
 * Created by Kristyna on 25/03/2017.
 */


@Rest(converters = {MappingJackson2HttpMessageConverter.class}, rootUrl = Constants.STAG_API_BASE_URL, interceptors = {AuthInterceptor.class})
public interface StagRestClient {

    @Get("users/getOsobniCislaByExternalLogin?login={login}&outputFormat=JSON")
    List<StagUser> getUserNumberForLogin(@Path String login);

    @Get("rozvrhy/getRozvrhByStudent?osCislo={osCislo}&outputFormat=JSON")
    List<StagTimetable> getTimetableToAuthorize(@Path String osCislo);


}
