package cz.hanusova.fingerprint_game.scene.tutorial;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import com.github.paolorotolo.appintro.AppIntro;
import com.github.paolorotolo.appintro.AppIntroFragment;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.sharedpreferences.Pref;

import cz.hanusova.fingerprint_game.Preferences_;
import cz.hanusova.fingerprint_game.R;

/**
 * Created by khanusova on 06/07/2017.
 */
@EActivity
public class IntroActivity extends AppIntro {

    private static final String[] slideNames = new String[]{"screen_login", "screen_map", "screen_user_detail", "screen_place_info"};

    @Pref
    Preferences_ preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i = 0; i < 4; i++){
            createSlide(i);
        }
    }

    private int getDrawableId(int page){
        return getResources().getIdentifier(slideNames[page], "drawable", getPackageName());
    }

    @Override
    public void onSkipPressed(Fragment currentFragment) {
        super.onSkipPressed(currentFragment);
        finishTutorial();
    }

    @Override
    public void onDonePressed(Fragment currentFragment) {
        super.onDonePressed(currentFragment);
        finishTutorial();
    }

    private void finishTutorial(){
        preferences.sawTutorial().put(true);
        finish();
    }

    private void createSlide(int position) {
        String title;
        String text;
        switch (position) {
            case 0:
                title = "Přihlášení do hry";
                text = "Vítejte ve hře! Pro přihlášení používejte uživatelské údaje do IS STAG. ";
                break;
            case 1:
                title = "Mapa";
                text = "Mapa zobrazuje objevená místa. " +
                        "Ikona fotoaparátu spustí scanování QR kódu. " +
                        "Za každé načtení místa získáváte body. Průběžné pořadí uživatelů je možné prohlížet pomocí volby v horním menu.";
                break;
            case 2:
                title = "Detail uživatele";
                text = "Na tomto místě se zobrazuje aktuální stav hráče. " +
                        "\n Zlato je potřeba pro platbu za domy pro dělníky. " +
                        "Jídlo dostávají dělníci, kteří zrovna pracují. Dřevo a kamení slouží ke stavbě nových domů pro dělníky.";
                break;
            case 3:
                title = "Detail místa";
                text = "Detaily každého navštíveného místa se zobrazí po kliknutí na ikonu objeveného místa.";
                break;
            default:
                title = null;
                text = null;
                break;
        }
        addSlide(AppIntroFragment.newInstance(title, text, getDrawableId(position), getResources().getColor(R.color.colorNude)));
    }
}
