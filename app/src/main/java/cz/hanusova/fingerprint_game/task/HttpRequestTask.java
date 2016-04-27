package cz.hanusova.fingerprint_game.task;

import android.os.AsyncTask;
import android.widget.TextView;

import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import cz.hanusova.fingerprint_game.model.AppUser;

/**
 * Created by khanusova on 20.3.2016.
 */
public class HttpRequestTask extends AsyncTask <Void, Void, AppUser>{

    private TextView usernameTv;
    private TextView stagnameTv;

    public HttpRequestTask(TextView usernameTv, TextView stagnameTv) {
        this.usernameTv = usernameTv;
        this.stagnameTv = stagnameTv;
    }

    @Override
    protected AppUser doInBackground(Void[] params) {
        String url = "http://localhost:8080/fingerprint-game/login/loginProcess?username=user&password=user";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
        AppUser user = restTemplate.getForObject(url, AppUser.class);


        return user;
    }

    /**
     * Metoda, ktera se spusti po dokonceni asynchronni operace
     *
     * @param user
     */
    @Override
    protected void onPostExecute(AppUser user) {
        usernameTv.setText(user.getUsername());
        stagnameTv.setText(user.getStagname());
    }
}
