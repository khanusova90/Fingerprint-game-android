package cz.hanusova.fingerprint_game.view;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LayerDrawable;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

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
    private Point startPoint = new Point();
    private ScaleGestureDetector scaleDetector;
    private GestureDetector gestureDetector;
    private int possibleMoveToRight = 0;
    private int possibleMoveDown = 0;
    private float origW, origH, screenWidth, screenHeight;
    private float actW = 0, actH = 0;
    private float scaleFactor;


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
        gestureDetector = new GestureDetector(context, new TapListener());
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
                scaleDetector.onTouchEvent(event);
                gestureDetector.onTouchEvent(event);
                int pointerIndex = MotionEventCompat.getActionIndex(event);
                int currX = (int) MotionEventCompat.getX(event, pointerIndex);
                int currY = (int) MotionEventCompat.getY(event, pointerIndex);
                screenWidth = v.getWidth();
                screenHeight = v.getHeight();


                origW = (((ImageView) v).getDrawable().getIntrinsicWidth());
                origH = (((ImageView) v).getDrawable().getIntrinsicHeight());

                possibleMoveToRight *= scaleFactor;
                possibleMoveDown *= scaleFactor;


                if (actW == 0 || actH == 0) {
                    actH = origH;
                    actW = origW;
                }


                int action = MotionEventCompat.getActionMasked(event);
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        if (mode == TAP) {
                            LayerDrawable ld = (LayerDrawable) getDrawable();
                            for (int i = 1; i < ld.getNumberOfLayers(); i++) {

                                BitmapDrawable d = (BitmapDrawable) ld.getDrawable(i);
                                Rect r = d.getBounds();

                                if (ld.getDrawable(i).getBounds().contains(currX, currY)) {
                                    System.out.println("Icon at " + i + " position was clicked!");
                                    break;
                                }
                            }
                        } else {
                            mode = DRAG;
                            lastPoint.set(currX, currY);
                            startPoint.set(currX, currY);
                        }
                        break;

                    case MotionEvent.ACTION_MOVE:
                        if (mode == DRAG) {

//                            boolean xChanged = false;
//                            boolean yChanged = false;

                            float deltaX = currX - lastPoint.x;
                            float deltaY = currY - lastPoint.y;


//                            if (possibleMoveToRight < deltaX || possibleMoveDown < deltaY) {
//                                if (possibleMoveToRight < deltaX) {
//                                    deltaX = possibleMoveToRight;
//                                    possibleMoveToRight = 0;
//                                    xChanged = true;
//                                }
//                                if (possibleMoveDown < deltaY) {
//                                    deltaY = possibleMoveDown;
//                                    possibleMoveDown = 0;
//                                    yChanged = true;
//                                }
//                            }
//                            if (!xChanged) {
//                                possibleMoveToRight -= deltaX;
//                            }
//                            if (!yChanged) {
//                                possibleMoveDown -= deltaY;
//                            }


                            matrix.postTranslate(deltaX, deltaY);
                            lastPoint.set(currX, currY);
                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        performClick();
                        break;

                    case MotionEvent.ACTION_POINTER_UP:
                        mode = NONE;
                        break;
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
            scaleFactor = detector.getScaleFactor();
            if (scaleFactor < 1 && actH >= screenHeight && actW >= screenWidth) {
                return setScale(detector);
            } else if (scaleFactor > 1 && actH < MAX_MAP_HEIGHT) {
                return setScale(detector);
            }
            return false;
        }

        public Boolean setScale(ScaleGestureDetector detector) {
            matrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
            values[Matrix.MSCALE_X] = scaleFactor;
            values[Matrix.MSCALE_Y] = scaleFactor;
            actW *= values[Matrix.MSCALE_X];
            actH *= values[Matrix.MSCALE_Y];
            return true;
        }
    }

    private class TapListener extends GestureDetector.SimpleOnGestureListener {

        @Override
        public boolean onSingleTapConfirmed(MotionEvent e) {
            mode = TAP;
            return true;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            mode = DRAG;
            return true;
//            return super.onScroll(e1, e2, distanceX, distanceY);
        }
    }
}
