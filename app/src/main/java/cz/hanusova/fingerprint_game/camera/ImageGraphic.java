package cz.hanusova.fingerprint_game.camera;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;

import com.google.android.gms.vision.barcode.Barcode;

import cz.hanusova.fingerprint_game.model.Activity;

/**
 * Created by khanusova on 7.9.2016.
 */
public class ImageGraphic extends GraphicOverlay.Graphic {
    //TODO: absraktni predek
    private Paint textPaint;
    private volatile Barcode barcode;
    private volatile Activity activity;
    private String text;

    public ImageGraphic(GraphicOverlay overlay, Activity activity) {
        super(overlay);
        textPaint = new Paint();
        textPaint.setColor(Color.MAGENTA);
        textPaint.getShader();

        text = activity.getName();
    }

    @Override
    public void draw(Canvas canvas) {
        if (barcode == null) {
            return;
        }
        if (activity == null) {
            return;
        }

        RectF rect = new RectF(barcode.getBoundingBox());
//        canvas.drawBitmap();
        canvas.drawText(activity.getName(), rect.left, rect.bottom, textPaint);
    }

    /**
     * Updates the barcode instance from the detection of the most recent frame.  Invalidates the
     * relevant portions of the overlay to trigger a redraw.
     */
    void updateItem(Barcode barcode, Activity activity) {
        this.barcode = barcode;
        this.activity = activity;
        postInvalidate();
    }
}
