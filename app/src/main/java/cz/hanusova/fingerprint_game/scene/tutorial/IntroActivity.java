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
 * Activity to show introduction
 *
 * Created by khanusova on 06/07/2017.
 */
@EActivity
public class IntroActivity extends AppIntro {

    private static final String[] slideNames = new String[]{
            "screen_login",
            "screen_map",
            "icon_food",
            "icon_money",
            "icon_stone",
            "icon_house",
            "icon_shop",
            "screen_scan",
            "screen_user_detail",
            "screen_place_info"
    };

    @Pref
    Preferences_ preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        for (int i = 0; i < slideNames.length; i++) {
            createSlide(i);
        }
    }

    private int getDrawableId(int page) {
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

    private void finishTutorial() {
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
                title = "Jídlo";
                text = "Jídlo je pro pracovníky, kteří aktuálně těží nějaké suroviny \n" +
                        "Pokud nemáte dost jídla, probíhající těžba bude zastavena";
                break;
            case 3:
                title = "Zlato";
                text = "Zlatem se platí všem pracovníkům, které máte k dispozici. \n" +
                        "Pokud nemáte dost zlata, budou odebrány postavené domky a dělníci, kteří v nich žijí";
                break;
            case 4:
                title = "Suroviny na stavbu";
                text = "Kámen a dřevo je potřeba ke stavbě nových domků pro pracovníky";
                break;
            case 5:
                title = "Nové domy";
                text = "Na určených místech lze postavit nové domy pro dělníky. V každém domě žije jeden dělník.";
                break;
            case 6:
                title = "Obchod";
                text = "V obchodě lze nakupovat předměty pro urychlení těžby. \n" +
                        "Sekera urychluje těžbu dřeva, krumpáč těžbu kamení a žebřík sběr jídla";
                break;
            case 7:
                title = "Snímání kódu";
                text = "Hledejte ve škole QR kódy ke hře. Každý kód zastupuje jedno místo \n" +
                        "Při snímání se spustí odpočet, během kterého můžete zvolit, kolik dělníků použijete pro těžbu, případně kolik nových domů postavíte.";
                break;
            case 8:
                title = "Detail uživatele";
                text = "Na tomto místě se zobrazuje aktuální stav hráče. \n" +
                        "Každý dosažený level zlepšuje schopnost těžby surovin" ;
                break;
            case 9:
                title = "Detail místa";
                text = "Detaily každého navštíveného místa se zobrazí po kliknutí na ikonu objeveného místa.";
                break;
            default:
                title = null;
                text = null;
                break;
        }
        addSlide(AppIntroFragment.newInstance(title, text, getDrawableId(position), getResources().getColor(R.color.colorNude), getResources().getColor(R.color.colorAccent), getResources().getColor(R.color.colorAccent)));
    }
}
