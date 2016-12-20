package cz.hanusova.fingerprint_game.view;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
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
    private int mode = NONE;

    private Matrix matrix;

    private PointF lastPoint = new PointF();
    private PointF startPoint = new PointF();

    private ScaleGestureDetector scaleDetector;
    private GestureDetector gestureDetector;

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
                float currX = MotionEventCompat.getX(event, pointerIndex);
                float currY = MotionEventCompat.getY(event, pointerIndex);

                int action = MotionEventCompat.getActionMasked(event);
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        if (mode == TAP) {
                            LayerDrawable ld = (LayerDrawable) getDrawable();
                            for (int i = 1; i < ld.getNumberOfLayers(); i++) {
                                BitmapDrawable d = (BitmapDrawable) ld.getDrawable(i);
                                Rect r = d.getBounds();

                                if (r.contains((int) currX, (int) currY)) {
                                    System.out.println("Icon at " + i + " position was clicked!");
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
                            float deltaX = currX - lastPoint.x;
                            float deltaY = currY - lastPoint.y;
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
            float scaleFactor = detector.getScaleFactor();
            matrix.postScale(scaleFactor, scaleFactor, detector.getFocusX(), detector.getFocusY());
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
