package cz.hanusova.fingerprint_game.map;

import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.OptionsMenu;

import javax.inject.Inject;

import cz.hanusova.fingerprint_game.FingerprintApplication;
import cz.hanusova.fingerprint_game.R;
import cz.hanusova.fingerprint_game.base.BasePresenter;
import cz.hanusova.fingerprint_game.base.ui.BaseActivity;

/**
 * Created by khanusova on 6.6.2016.
 * <p>
 * Activity for displaying map
 */
@EActivity(R.layout.map)
@OptionsMenu(R.menu.map_toolbar_menu)
public class MapActivity extends BaseActivity implements MapActivityView {
    private static final int REQ_CODE_QR = 1;
    private static final int ICON_SIZE = 8;
    private static final int MAP_HEIGHT = 2800;
    private static final int MAP_WIDTH = 2600;

    @Inject
    MapActivityPresenter presenter;

    @Override
    public void inject() {
        FingerprintApplication.instance.getComponent().inject(this);
    }

    @Override
    public BasePresenter getPresenter() {
        return presenter;
    }


//
//    @Bean(UserServiceImpl.class)
//    UserService userService;
//    @ViewById(R.id.img_map)
//    TouchImageView mapView;
//    @ViewById(R.id.action_menu)
//    FloatingActionMenu floatingActionMenu;
//    @ViewById(R.id.action_floor_up)
//    FloatingActionButton buttonFloorUp;
//    @ViewById(R.id.action_floor_down)
//    FloatingActionButton buttonFloorDown;
//    @ViewById(R.id.action_camera)
//    FloatingActionButton buttonCamera;
//    @ViewById(R.id.action_profile)
//    FloatingActionButton buttonProfile;
//    @ViewById(R.id.action_menu)
//    FloatingActionMenu buttonActionMenu;
//    @OptionsMenuItem(R.id.action_map_logout)
//    MenuItem optionsItem;
//    private int currentFloor = 1;  // 1 - 4 NP, not 0 - 3
//    private Drawable[] layers;
//    private Bitmap[] mapField = new Bitmap[4];
//    private List<Place> places;
//
//    @Pref
//    Preferences_ preferences;
//
//
//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//    @AfterViews
//    void init() {
//        //TODO: proc tady?
////        Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
////            @Override
////            public void uncaughtException(Thread paramThread, Throwable paramThrowable) {
////                Log.e("Thread exception", paramThrowable.getMessage());
////            }
////        });
//        System.out.println("INITIALIZING");
//        setTitle(currentFloor + ". patro");
//        int index = currentFloor - 1;
//        if (mapField[index] == null) {
//            System.out.println("MAP WAS NULL");
//            String drawableName = "j" + currentFloor + "np";
////            Drawable defaultMap = getResources().getDrawable(getResources().getIdentifier(drawableName, "drawable", getPackageName()));
//            mapField[index] = BitmapFactory.decodeResource(getResources(), getResources().getIdentifier(drawableName, "drawable", getPackageName()));
//            System.out.println("MAP FIELD FILLED");
//            try {
//                System.out.println("DOWNLOADING MAP");
//                mapField[index] = new BitmapWorkerTask(getFloorName(currentFloor), this.getApplicationContext(), AppUtils.getVersionCode(this))
//                        .execute().get();
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//            System.out.println("MAP DOWNLOADED");
//        }
//        System.out.println("CREATING DRAWABLE");
//        final Drawable mapDrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(mapField[currentFloor - 1], MAP_WIDTH, MAP_HEIGHT, true));
//
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("GETTING PLACES");
//                places = userService.getActualUser().getPlacesByFloor(currentFloor);
//                List<Drawable> icons = getIcons(places);
//                changeIconPosition(mapDrawable, places, createLayers(mapDrawable, icons));
//            }
//        }).start();
//
//
//        buttonFloorDown.setEnabled(!(currentFloor == 1));
//        buttonFloorUp.setEnabled(!(currentFloor == 4));
//    }
//
//    @OptionsItem
//    void action_map_logout() {
//        preferences.clear();
//        LoginActivity_.intent(getApplicationContext()).flags(Intent.FLAG_ACTIVITY_NEW_TASK).start();
//        finish();
//
//    }
//
//    @OptionsItem
//    void action_map_end() {
//        AlertDialogFragment alertDialogFragment = new AlertDialogFragment_();
//        alertDialogFragment.show(getSupportFragmentManager(), "ddd");
//
//
//    }
//
//
//
//
//
//    private List<Drawable> getIcons(List<Place> places) {
//        if (places == null) {
//            return null;
//        }
//        List<Drawable> icons = new ArrayList<>();
//        for (Place p : places) {
//            try {
//                String iconName = p.getPlaceType().getImgUrl();
//                if (iconName == null) {
//                    iconName = p.getMaterial().getIconName();
//                }
//                Bitmap bitmap = new BitmapWorkerTask(iconName, this.getApplicationContext(), AppUtils.getVersionCode(this)).execute().get();
//                Drawable icon = new BitmapDrawable(getResources(), bitmap);
//                icons.add(icon);
//            } catch (InterruptedException | ExecutionException e) {
//                e.printStackTrace();
//            }
//        }
//        return icons;
//    }
//
//    private LayerDrawable createLayers(Drawable mapDrawable, List<Drawable> icons) {
//        layers = new Drawable[icons != null ? icons.size() + 1 : 1];
//        layers[0] = mapDrawable;
//        for (int i = 1; i < layers.length; i++) {
//            layers[i] = icons.get(i - 1);
//        }
//        return new LayerDrawable(layers);
//    }
//
//
//
//    private void changeIconPosition(Drawable mapDrawable, List<Place> places, LayerDrawable ld) {
//        if (places == null) {
//            mapView.setImageDrawable(ld);
//            return;
//        }
//        for (int i = 0; i < places.size(); i++) {
//            Place p = places.get(i);
//            int x = p.getxCoord();
//            int y = p.getyCoord();
//            ld.setLayerInset(i + 1, x, y, mapDrawable.getIntrinsicWidth() - x + ICON_SIZE, mapDrawable.getIntrinsicHeight() - y + ICON_SIZE);
//        }
//        mapView.setImageDrawable(ld);
//        mapView.setPlaces(places);
//        mapView.setFragmentManager(getSupportFragmentManager());
//
//
//    }
//
//
//    @Click(R.id.action_camera)
//    void startCamera() {
//        QrActivity_.intent(this).startForResult(REQ_CODE_QR);
//    }
//
//    @Click(R.id.action_floor_down)
//    void goDown() {
//        currentFloor = currentFloor > 1 ? --currentFloor : currentFloor;
//        init();
//
//    }
//
//    @Click(R.id.action_floor_up)
//    void goUp() {
//        currentFloor = currentFloor < 4 ? ++currentFloor : currentFloor;
//        init();
//    }
//
//    @Click(R.id.action_profile)
//    void goToProfile() {
//        UserDetailActivity_.intent(this).flags(Intent.FLAG_ACTIVITY_NEW_TASK).start();
//    }
//
//
//    @OnActivityResult(REQ_CODE_QR)
//    void showActivitiesUpdate(int resultCode, @OnActivityResult.Extra(value = Constants.EXTRA_ACTIVITIES) ArrayList<UserActivity> activities) {
//        int f = 555; //TODO: ???
//    }
//
//    //TODO: co to je za metody?
//    public void doPositiveClick() {
//        finish();
//    }
//
//    public void doNegativeClick(){
//    }
}
