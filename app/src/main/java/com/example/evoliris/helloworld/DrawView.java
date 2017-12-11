package com.example.evoliris.helloworld;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import java.lang.reflect.Array;
import java.util.ArrayList;


public class DrawView extends View {

    private float STROKE_WIDTH = 5f;

    /**
     * Need to track this so the dirty region can accommodate the stroke.
     **/
    private float HALF_STROKE_WIDTH = STROKE_WIDTH / 2;

    private Paint paint = new Paint();
    private Path path = new Path();
    private ArrayList<Path> paths = new ArrayList<Path>();
    private ArrayList<Paint> paints = new ArrayList<Paint>();
    private Canvas storedCanvas;
    private Bitmap bitmap;

    /**
     * Optimizes painting by invalidating the smallest possible area.
     */
    private float lastTouchX;
    private float lastTouchY;
    private final RectF dirtyRect = new RectF();

    public DrawView(Context context, AttributeSet attrs) {
        super(context, attrs);
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeWidth(STROKE_WIDTH);
    }

    /**
     * Erases the drawing.
     */
    public void clear() {
        paths.clear();
        paints.clear();
        bitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.ARGB_8888);
        storedCanvas = new Canvas(bitmap);
        // Repaints the entire view.
        invalidate();
    }

    public void back() {
        if(paths.size()>0){
            paths.remove(paths.size()-1);
            paints.remove(paints.size()-1);
            bitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.ARGB_8888);
            storedCanvas = new Canvas(bitmap);
            updateStoredCanvas();
        }
        // Repaints the entire view.
        invalidate();
    }

    private void updateStoredCanvas() {
        int cpt = 0;
        while (paths.size() > cpt) {
            storedCanvas.drawPath(paths.get(cpt), paints.get(cpt));
            cpt++;
        }
    }


    @Override
    protected void onDraw(Canvas canvas) {
        if (bitmap == null) {
            bitmap = Bitmap.createBitmap(this.getWidth(), this.getHeight(), Bitmap.Config.ARGB_8888);
            storedCanvas = new Canvas(bitmap);
        }
        canvas.drawBitmap(bitmap, 0, 0, null);
        canvas.drawPath(path, paint);
    }

    Bitmap getBitmap() {
        return Bitmap.createBitmap(bitmap);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                setStartPoint(event);
                return true;

            case MotionEvent.ACTION_MOVE:
                dirtyTracking(event);
                return true;
            case MotionEvent.ACTION_UP:
                dirtyTracking(event);
                //Saves path to history
                paths.add(new Path(path));
                paints.add(new Paint(paint));
                storedCanvas.drawPath(path,paint);
                path.reset();

                return true;

            default:
                // debug("Ignored touch event: " + event.toString());
                return false;
        }
    }

    private void setStartPoint(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();

        path.moveTo(eventX, eventY);
        lastTouchX = eventX;
        lastTouchY = eventY;
        // There is no end point yet, so don't waste cycles invalidating.
    }

    private void dirtyTracking(MotionEvent event) {
        float eventX = event.getX();
        float eventY = event.getY();
        // Start tracking the dirty region.
        resetDirtyRect(eventX, eventY);

        // When the hardware tracks events faster than they are delivered, the
        // event will contain a history of those skipped points.
        int historySize = event.getHistorySize();
        for (int i = 0; i < historySize; i++) {
            float historicalX = event.getHistoricalX(i);
            float historicalY = event.getHistoricalY(i);
            expandDirtyRect(historicalX, historicalY);
            path.lineTo(historicalX, historicalY);
        }

        // After replaying history, connect the line to the touch point.
        path.lineTo(eventX, eventY);
        // Include half the stroke width to avoid clipping.
        invalidate(
                (int) (dirtyRect.left - HALF_STROKE_WIDTH),
                (int) (dirtyRect.top - HALF_STROKE_WIDTH),
                (int) (dirtyRect.right + HALF_STROKE_WIDTH),
                (int) (dirtyRect.bottom + HALF_STROKE_WIDTH));

        lastTouchX = eventX;
        lastTouchY = eventY;
    }

    /**
     * Called when replaying history to ensure the dirty region includes all
     * points.
     */
    private void expandDirtyRect(float historicalX, float historicalY) {
        if (historicalX < dirtyRect.left) {
            dirtyRect.left = historicalX;
        } else if (historicalX > dirtyRect.right) {
            dirtyRect.right = historicalX;
        }
        if (historicalY < dirtyRect.top) {
            dirtyRect.top = historicalY;
        } else if (historicalY > dirtyRect.bottom) {
            dirtyRect.bottom = historicalY;
        }
    }

    /**
     * Resets the dirty region when the motion event occurs.
     */
    private void resetDirtyRect(float eventX, float eventY) {

        // The lastTouchX and lastTouchY were set when the ACTION_DOWN
        // motion event occurred.
        dirtyRect.left = Math.min(lastTouchX, eventX);
        dirtyRect.right = Math.max(lastTouchX, eventX);
        dirtyRect.top = Math.min(lastTouchY, eventY);
        dirtyRect.bottom = Math.max(lastTouchY, eventY);
    }

    public void changePainterColor(int color) {
        Paint newPaint = new Paint(paint);
        newPaint.setColor(color);
        paint = newPaint;
        path.reset();
    }

    public void changeStrokeWidth(float width) {
        Paint newPaint = new Paint(paint);
        STROKE_WIDTH = width;
        HALF_STROKE_WIDTH = STROKE_WIDTH / 2;
        paint.setStrokeWidth(STROKE_WIDTH);
        path.reset();
    }
}