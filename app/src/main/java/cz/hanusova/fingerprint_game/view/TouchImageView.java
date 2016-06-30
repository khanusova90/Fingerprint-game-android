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
    private Context context;

    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;

    private int actMode = NONE;

    private PointF lastPoint = new PointF();
    private PointF startPoint = new PointF();

    private float minScale = 1f;
    private float maxScale = 4f;
    private float saveScale = 1f;

    private float[] m;

    private int viewWidth;
    private int viewHeight;
    private int origWidth;
    private int orighHeight;

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
        this.context = context;
        detector = new ScaleGestureDetector(context, new MyScaleListener());
        matrix = new Matrix();
        m = new float[9];
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
                        actMode = DRAG;
                        break;

                    case MotionEvent.ACTION_MOVE:
                        float deltaX = currX - lastPoint.x;
                        float deltaY = currY - lastPoint.y;
                        matrix.postTranslate(deltaX, deltaY);
                        lastPoint.set(currX, currY);
                        break;

                    case MotionEvent.ACTION_UP:
                        actMode = NONE;
//                        int xDiff = (int) Math.abs(currX - startPoint.x);
//                        int yDiff = (int) Math.abs(currY - startPoint.y);
                        performClick();
                        break;

                    case MotionEvent.ACTION_POINTER_UP:
                        actMode = NONE;
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
            actMode = ZOOM;
            return true;
        }

        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            float scaleFactor = detector.getScaleFactor();
            float origScale = saveScale;

            saveScale *= scaleFactor;

            if (saveScale > maxScale){
                saveScale = maxScale;
            } else if (saveScale < minScale){
                saveScale = minScale;
            }

            matrix.postScale(saveScale, saveScale, 0, 0);
            return true;
        }
    }
}
