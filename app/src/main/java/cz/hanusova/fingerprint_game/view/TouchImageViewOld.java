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
import android.widget.OverScroller;

/**
 * Created by khanusova on 8.6.2016.
 * <p/>
 * http://www.c-sharpcorner.com/UploadFile/88b6e5/multi-touch-panning-pinch-zoom-image-view-in-android-using/
 */
public class TouchImageViewOld extends ImageView {
    private static final String TAG = "TouchImageView";
    //Possible states;
    private static final int NONE = 0;
    private static final int DRAG = 1;
    private static final int ZOOM = 2;
    private static final int CLICK = 3;
    int mode = NONE;
    float lastX = -1;
    float lastY = -1;
    private OverScroller overScroller;
    private int activePointerId = 200;
    private Matrix matrix;
    //Zooming parameters
    private PointF start = new PointF();
    //    private PointF last = new PointF();
    private float minScale = 1f;
    private float maxScale = 3f;
    private float saveScale = 1f;
    private float[] m;
    private float posX = 0;
    private float posY = 0;
    private int viewWidth;
    private int viewHeight;
    private float origWidth;
    private float origHeight;
    private int oldMeasuredWidth;
    private int oldMeasuredHeight;
    private ScaleGestureDetector detector;
    private Context context;

    public TouchImageViewOld(Context context) {
        super(context);
        initConstruct(context);
    }

    public TouchImageViewOld(Context context, AttributeSet attrSet) {
        super(context, attrSet);
        initConstruct(context);
    }


    private void initConstruct(Context context) {
        this.context = context;
        super.setClickable(true);
//        detector = new ScaleGestureDetector(context, new ScaleListener());
        matrix = new Matrix();
        m = new float[9];
        setImageMatrix(matrix);
//        setScaleType(ScaleType.MATRIX);
        setOnTouchListener(createOnTouchListener());
    }

    private OnTouchListener createOnTouchListener() {
        System.out.println("Creating on touch listener");
        OnTouchListener listener = new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                detector.onTouchEvent(event);
//                PointF curr = new PointF(event.getX(), event.getY());
                float x;
                float y;

                int pointerIndex = MotionEventCompat.getActionIndex(event);
                x = MotionEventCompat.getX(event, pointerIndex);
                y = MotionEventCompat.getY(event, pointerIndex);

                int action = MotionEventCompat.getActionMasked(event);

                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        System.out.println("Action DOWN");
//                        start.set(curr);
//                        last.set(curr);
                        mode = DRAG;


                        lastX = x;
                        lastY = y;
                        activePointerId = MotionEventCompat.getPointerId(event, 0);
                        break;

                    case MotionEvent.ACTION_MOVE:
                        System.out.println("Action MOVE");

//                        if (mode == DRAG) {
//                            System.out.println("DRAGGING");
//                        float deltaX = curr.x - start.x;
//                        float deltaY = curr.y - start.y;

//                        float posX = start.x - deltaX;
//                        float posy = start.y - deltaY;

                        float deltaX = x - lastX;
                        float deltaY = y - lastY;

                        //TODO: Nastavit pocatecni posX a na zaklade souradnic umistit obrazek
                        posX += deltaX;
                        posY += deltaY;

                        lastX = x;
                        lastY = y;


//                        start = curr;

//                            float fixTransX = getFixDragTrans(deltaX, viewWidth, origWidth * saveScale);
//                            float fixTransY = getFixDragTrans(deltaY, viewHeight, origHeight * saveScale);
                        // 0.0
//                            matrix.postTranslate(fixTransX, fixTransY);
//                        }
                        break;

                    case MotionEvent.ACTION_UP:
                        System.out.println("Action UP");
//                        mode = NONE;
//                        int xDiff = (int) Math.abs(curr.x - start.x);
//                        int yDiff = (int) Math.abs(curr.y - start.y);

//                        if (xDiff < CLICK && yDiff < CLICK) {
//                            performClick();
//                        }

                        final int pointerId = MotionEventCompat.getPointerId(event, pointerIndex);
//                        mode = NONE;
                        final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
                        lastX = x;
                        lastY = y;
                        activePointerId = MotionEventCompat.getPointerId(event, pointerIndex);
                        System.out.println("Scrolling: " + x + ", " + y);
                        scrollBy((int) posX, (int) posY);
                        break;

                    case MotionEvent.ACTION_POINTER_UP:
                        System.out.println("Pointer UP");

//                        final int pointerId = MotionEventCompat.getPointerId(event, pointerIndex);
////                        mode = NONE;
//                        final int newPointerIndex = pointerIndex == 0 ? 1 : 0;
//                        lastX = x;
//                        lastY = y;
//                        activePointerId = MotionEventCompat.getPointerId(event, pointerIndex);
//                        System.out.println("Scrolling: " + x + ", " + y);
//                        scrollBy((int)x, (int)y);
                        break;
                    default:
                        break;
                }


//                setImageMatrix(matrix);
                invalidate();
                return true;
            }
        };

        return listener;
    }
//
//    @Override
//    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        System.out.println("Measuring");
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//
//        viewWidth = MeasureSpec.getSize(widthMeasureSpec);
//        viewHeight = MeasureSpec.getSize(heightMeasureSpec);
//
//        System.out.println("View w + h: " + viewWidth + ", " + viewHeight);
//
//        if (oldMeasuredWidth == viewWidth && oldMeasuredHeight == viewHeight || viewWidth == 0 || viewHeight == 0) {
//            return;
//        }
//
//        oldMeasuredWidth = viewWidth;
//        oldMeasuredHeight = viewHeight;
//
//        if (saveScale == 1) { //Fit to screen
//            System.out.println("Fitting to screen");
//            Drawable drawable = getDrawable();
//            float scale;
//
//            if (drawable == null || drawable.getIntrinsicWidth() == 0 || drawable.getIntrinsicHeight() == 0) {
//                return;
//            }
//
//            float bmWidth = drawable.getIntrinsicWidth();
//            float bmHeight = drawable.getIntrinsicHeight();
//
//            float scaleX = viewWidth / bmWidth;
//            float scaleY = viewHeight / bmHeight;
//            scale = Math.min(scaleX, scaleY);
//
//            matrix.setScale(scale, scale);
//
//            //Center the image
//            float redundantYSpace = (float) viewHeight - (scale * bmHeight);
//            float redundantXSpace = (float) viewWidth - (scale * bmWidth);
//            redundantYSpace /= (float) 2;
//            redundantXSpace /= (float) 2;
//            matrix.postTranslate(redundantXSpace, redundantYSpace);
//            origWidth = viewWidth - 2 * redundantXSpace;
//            origHeight = viewHeight - 2 * redundantYSpace;
//
//            System.out.println("Orig w + h: " + origWidth + ", " + origHeight);
//
//            setImageMatrix(matrix);
//        }
//        fixTrans();
//    }
//
//    private float getFixTrans(float trans, float viewSize, float contentSize) {
//        float minTrans, maxTrans;
//        System.out.println("Getting fix trans. Trans: " + trans + ", viewSize: " + viewSize + ", contenSize: " + contentSize);
//
//        if (contentSize <= viewSize) {
//            minTrans = 0;
//            maxTrans = viewSize - contentSize;
//        } else {
//            minTrans = viewSize - contentSize;
//            maxTrans = 0;
//        }
//
//        System.out.println("Min trans: " + minTrans + ", max trans: " + maxTrans);
//
//        if (trans < minTrans) {
//            System.out.println("returning " + (-trans + minTrans));
//            return -trans + minTrans;
//        }
//
//        if (trans > maxTrans) {
//            System.out.println("returning " + (-trans + maxTrans));
//            return -trans + maxTrans;
//        }
//        return 0;
//    }
//
//    private float getFixDragTrans(float delta, float viewSize, float contentSize) {
//        System.out.println("Content size: " + contentSize); //0
//        System.out.println("View size: " + viewSize); //4000
//        if (contentSize <= viewSize) {
//            return 0;
//        }
//        return delta;
//    }
//
//    private void fixTrans() {
//        matrix.getValues(m);
//
//        float transX = m[Matrix.MTRANS_X];
//        float transY = m[Matrix.MTRANS_Y];
//        float fixTransX = getFixTrans(transX, viewWidth, origWidth * saveScale);
//        float fixTransY = getFixTrans(transY, viewHeight, origHeight * saveScale);
//
//        if (fixTransX != 0 || fixTransY != 0) {
//            System.out.println("Post translate: " + fixTransX + ", " + fixTransY);
//            matrix.postTranslate(fixTransX, fixTransY);
//        }
//    }
//
//    public void setMaxZoom(float zoom) {
//        this.maxScale = zoom;
//    }
//
//    private class ScaleListener extends ScaleGestureDetector.SimpleOnScaleGestureListener {
//
//        @Override
//        public boolean onScaleBegin(ScaleGestureDetector detector) {
//            mode = ZOOM;
//            return true;
//        }
//
//        @Override
//        public boolean onScale(ScaleGestureDetector detector) {
//            float mScaleFactor = detector.getScaleFactor();
//            float origScale = saveScale;
//            saveScale *= mScaleFactor;
//
//            System.out.println("Save scale " + saveScale);
//            System.out.println("Max scale " + maxScale);
//
//            if (saveScale > maxScale) {
//                System.out.println("saveScale > maxScale");
//                saveScale = maxScale;
//                mScaleFactor = maxScale / origScale;
//            } else if (saveScale < minScale) {
//                saveScale = minScale;
//                mScaleFactor = minScale / origScale;
//            }
//
//            System.out.println("Scale factor: " + mScaleFactor);
//
//            if (origWidth * saveScale <= viewWidth || origHeight * saveScale <= viewHeight) {
//                System.out.println("Mensi");
//                System.out.println("Scale: " + viewWidth/2 + ", " + viewHeight/2);
//                matrix.postScale(mScaleFactor, mScaleFactor, viewWidth / 2, viewHeight / 2);
//            } else {
//                matrix.postScale(mScaleFactor, mScaleFactor, detector.getFocusX(), detector.getFocusY());
//                System.out.println("Scale: " + detector.getFocusX() + ", " + detector.getFocusY());
//            }
//
//            fixTrans();
//            return true;
//        }
//    }


}
