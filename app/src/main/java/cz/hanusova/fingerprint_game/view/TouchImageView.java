package cz.hanusova.fingerprint_game.view;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

import java.util.Calendar;
import java.util.List;

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
    private float origW, origH, mapWidth, mapHeight;
    private float actW = 0, actH = 0;
    private float scaleFactor = 1;
    private long startClickTime;
    private float relativeX, relativeY;
    private List<Place> places;
    private FragmentManager fragmentManager;
    private float screenWidth, screenHeight;


    public TouchImageView(Context context) {
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

    }

    private void initOnTouchListener() {

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Display display = v.getDisplay();
                scaleDetector.onTouchEvent(event);
                int pointerIndex = MotionEventCompat.getActionIndex(event);
                int currX = (int) MotionEventCompat.getX(event, pointerIndex);
                int currY = (int) MotionEventCompat.getY(event, pointerIndex);
                mapWidth = v.getWidth();

                mapHeight = v.getHeight();
                origW = (((ImageView) v).getDrawable().getIntrinsicWidth());
                origH = (((ImageView) v).getDrawable().getIntrinsicHeight());
                if (actW == 0 || actH == 0) {
                    actH = origH;
                    actW = origW;
                }
                screenWidth = ((1080 - values[2]) / values[0]) - ((0 - values[2]) / values[0]);
                screenHeight = ((1570 - values[5]) / values[0]) - ((0 - values[5]) / values[4]);


                int action = MotionEventCompat.getActionMasked(event);
                switch (action) {

                    case MotionEvent.ACTION_UP: {
                        matrix.getValues(values);
                        long clickDuration = Calendar.getInstance().getTimeInMillis() - startClickTime;
                        mode = clickDuration < 150 ? TAP : NONE;
                        if (mode == TAP) {
                            LayerDrawable ld = (LayerDrawable) getDrawable();
                            for (int i = 1; i < ld.getNumberOfLayers(); i++) {
                                lastPoint.set(currX, currY);
                                relativeX = (event.getX() - values[2]) / values[0];
                                relativeY = (event.getY() - values[5]) / values[4];
                                if (ld.getDrawable(i).getBounds().contains((int) relativeX, (int) relativeY)) {
                                    showFragment(i);
                                    break;
                                }
                            }
                        }
                        break;
                    }

                    case MotionEvent.ACTION_DOWN: {
                        matrix.getValues(values);
                        startClickTime = Calendar.getInstance().getTimeInMillis();
                        lastPoint.set(currX, currY);
                        break;
                    }
                    case MotionEvent.ACTION_MOVE: {
                        matrix.getValues(values);
                        float deltaX = currX - lastPoint.x;
                        float deltaY = currY - lastPoint.y;
                        //deltaX = values[Matrix.MTRANS_X] + deltaX >= 0 || values[Matrix.MTRANS_X] + deltaX <= (-origW + screenWidth) * values[0] ? 0 : deltaX;
                        //deltaY = values[Matrix.MTRANS_Y] + deltaY >= 0 || values[Matrix.MTRANS_Y] + deltaY <= (-origH + screenHeight) * values[4] ? 0 : deltaY;
                        matrix.postTranslate(deltaX, deltaY);
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
            mode = ZOOM;
            return true;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {

            if (values[Matrix.MSCALE_Y] - 0.1 >= 0.7 && values[Matrix.MSCALE_Y] + 0.1  < 1.5) {
                return setScale(detector);
            }
            return false;
        }

        public boolean setScale(ScaleGestureDetector detector) {
            scaleFactor = detector.getScaleFactor();

            matrix.postScale(scaleFactor, scaleFactor, getX(detector), getY(detector));
            return true;
        }

        public float getX(ScaleGestureDetector detector) {
            float var;
            if (values[Matrix.MTRANS_X] >= 0) {
                var = 0;
            } else if (values[Matrix.MTRANS_X] <=(-origW + screenWidth) * values[0]){
                var = screenWidth;
            }else{
                var = detector.getFocusX();
            }

            return var;
        }

        public float getY(ScaleGestureDetector detector) {
            float var;
            if (values[Matrix.MTRANS_Y] >= 0) {
                var = 0;
            } else if (values[Matrix.MTRANS_Y] <= (-origH + screenHeight) * values[4]) {
                var = screenHeight;
            } else {
                var = detector.getFocusY();
            }

            return var;
        }


    }

    public void setPlaces(List<Place> places) {
        this.places = places;
    }

    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fragmentManager = fragmentManager;
    }

    public void showFragment(int i) {
        if (places == null) {
            return;
        }
        Place place = places.get(i - 1);
        if (place != null) {
            PlaceInfoFragment placeInfoFragment = new PlaceInfoFragment_();
            placeInfoFragment.setPlace(places.get(i - 1));
            placeInfoFragment.show(fragmentManager, "hovno");
        }
    }
}
