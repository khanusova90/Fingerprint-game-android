package cz.hanusova.fingerprint_game.view;

import android.content.Context;
import android.graphics.Matrix;
import android.graphics.PointF;
import android.support.v4.view.MotionEventCompat;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by khanusova on 19.6.2016.
 */
public class TouchImageView extends ImageView {

    private Matrix matrix;

    private PointF lastPoint = new PointF();
    private PointF startPoint = new PointF();

    private ScaleGestureDetector detector;

    public TouchImageView(Context context){
        super(context);
        initConstruct(context);
    }

    public TouchImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initConstruct(context);
    }

    private void initConstruct(Context context){
        super.setClickable(true);
        detector = new ScaleGestureDetector(context, new MyScaleListener());
        matrix = new Matrix();
        setImageMatrix(matrix);
        setScaleType(ScaleType.MATRIX);
        initOnTouchListener();
    }

    private void initOnTouchListener(){
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                detector.onTouchEvent(event);
                int pointerIndex = MotionEventCompat.getActionIndex(event);
                float currX = MotionEventCompat.getX(event, pointerIndex);
                float currY = MotionEventCompat.getY(event, pointerIndex);

                int action = MotionEventCompat.getActionMasked(event);
                switch (action){
                    case MotionEvent.ACTION_DOWN:
                        lastPoint.set(currX, currY);
                        startPoint.set(currX, currY);
                        break;

                    case MotionEvent.ACTION_MOVE:
                        float deltaX = currX - lastPoint.x;
                        float deltaY = currY - lastPoint.y;
                        matrix.postTranslate(deltaX, deltaY);
                        lastPoint.set(currX, currY);
                        break;

                    case MotionEvent.ACTION_UP:
                        performClick();
                        break;

                    case MotionEvent.ACTION_POINTER_UP:
                        break;
                }
                setImageMatrix(matrix);
                invalidate();

                return true;
            }
        });
    }

    private class MyScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener{

        @Override
        public boolean onScaleBegin(ScaleGestureDetector detector) {
            return true;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float scaleFactor = detector.getScaleFactor();

            matrix.postScale(scaleFactor, scaleFactor, 0, 0);
            return true;
        }
    }
}
