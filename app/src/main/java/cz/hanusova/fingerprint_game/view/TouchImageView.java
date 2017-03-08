package cz.hanusova.fingerprint_game.view;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

import java.util.Calendar;
import java.util.List;

import cz.hanusova.fingerprint_game.MapActivity;
import cz.hanusova.fingerprint_game.MapActivity_;
import cz.hanusova.fingerprint_game.fragment.PlaceInfoFragment;
import cz.hanusova.fingerprint_game.fragment.PlaceInfoFragment_;
import cz.hanusova.fingerprint_game.model.Place;

/**
 * Created by khanusova on 19.6.2016.
 */
public class TouchImageView extends ImageView {

    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private static final int TAP = 3;
    private static final int MAX_MAP_HEIGHT = 4500;
    float[] values;
    private int mode = NONE;
    private Matrix matrix;
    private Point lastPoint = new Point();
    private ScaleGestureDetector scaleDetector;
    private float origW, origH, screenWidth, screenHeight;
    private float actW = 0, actH = 0;
    private float scaleFactor = 1;
    private long startClickTime;
    private float relativeX, relativeY;
    private List<Place> places;
    private FragmentManager fragmentManager;


    public TouchImageView(Context context ) {
        super(context);
        initConstruct(context);
    }

    public TouchImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initConstruct(context);
    }
    private void initConstruct(Context context) {
        super.setClickable(true);
        scaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        matrix = new Matrix();
        values = new float[9];
        matrix.getValues(values);
        setImageMatrix(matrix);
        setScaleType(ScaleType.MATRIX);
        initOnTouchListener();
        values[2] = 0;
        values[5] = 0;
    }

    private void initOnTouchListener() {

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                scaleDetector.onTouchEvent(event);
                int pointerIndex = MotionEventCompat.getActionIndex(event);
                int currX = (int) MotionEventCompat.getX(event, pointerIndex);
                int currY = (int) MotionEventCompat.getY(event, pointerIndex);
                screenWidth = v.getWidth();
                screenHeight = v.getHeight();
                origW = (((ImageView) v).getDrawable().getIntrinsicWidth());
                origH = (((ImageView) v).getDrawable().getIntrinsicHeight());
                if (actW == 0 || actH == 0) {
                    actH = origH;
                    actW = origW;
                }


                int action = MotionEventCompat.getActionMasked(event);
                switch (action) {


                    case MotionEvent.ACTION_UP: {
                        long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
                        mode = clickDuration < 150 ? TAP : NONE;



                        if (mode == TAP) {
                            LayerDrawable ld = (LayerDrawable) getDrawable();

                            for (int i = 1; i < ld.getNumberOfLayers(); i++) {
                                lastPoint.set(currX, currY);
                                relativeX = (event.getX() - values[2]);
                                relativeY = (event.getY() - values[5]);

                                if (ld.getDrawable(i).getBounds().contains((int) relativeX, (int) relativeY)) {
                                    System.out.println("Icon at " + i + " position was clicked!");
                                    showFragment(i);
                                    break;
                                }
                            }
                        }
                        break;
                    }

                    case MotionEvent.ACTION_DOWN: {
                        startClickTime = Calendar.getInstance().getTimeInMillis();
                        lastPoint.set(currX, currY);
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        float deltaX = currX - lastPoint.x;
                        float deltaY = currY - lastPoint.y;

                        matrix.postTranslate(deltaX, deltaY);
                        if(mode == ZOOM){
                            values[2] *= scaleFactor;
                            values[5] *= scaleFactor;
                            deltaX *= scaleFactor;
                            deltaY *= scaleFactor;
                            mode = NONE;
                        }

                        values[2] += deltaX;
                        values[5] += deltaY;
                        lastPoint.set(currX, currY);

                        break;
                    }
                }
                setImageMatrix(matrix);
                invalidate();
                return true;
            }
        });
    }

    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
           // mode = ZOOM;
            return true;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {

//            if (scaleFactor < 1 && actH >= screenHeight && actW >= screenWidth) {
//                return setScale(detector);
//            } else if (scaleFactor > 1 && actH < MAX_MAP_HEIGHT) {
//                return setScale(detector);
//            }
            return false;
            //return setScale(detector);
        }

        public Boolean setScale(ScaleGestureDetector detector) {
            scaleFactor = detector.getScaleFactor();
            matrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
            values[Matrix.MSCALE_X] *= scaleFactor;
            values[Matrix.MSCALE_Y] *= scaleFactor;
            actW *= values[Matrix.MSCALE_X];
            actH *= values[Matrix.MSCALE_Y];
            return true;
        }


    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void showFragment(int i){
        if (places == null){
            return;
        }
        Place place = places.get(i-1);
        if (place != null){
            PlaceInfoFragment placeInfoFragment = new PlaceInfoFragment_();
            placeInfoFragment.setPlace(places.get(i - 1));
            placeInfoFragment.show(fragmentManager, "hovno");
        }
    }
}
