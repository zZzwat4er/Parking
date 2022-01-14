package com.example.parking.utility;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.graphics.Rect;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import static java.lang.Math.abs;
import static java.lang.Math.pow;

public class MapTouchListener implements View.OnTouchListener{

    // TODO: map move boundaries
    // TODO: map zoom out animation (not instant as it is now)
    // TODO: double tap zoom
    private static final String TAG = "Touch";
    @SuppressWarnings("unused")
    private static final float MIN_ZOOM = 1f, MAX_ZOOM = 1f;

    // These matrices will be used to scale points of the image
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();
    Matrix startMatrix;
    float[] startParams = new float[9];

    // The 3 states (events) which the user is trying to perform
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    // these PointF objects are used to record the point(s) the user is touching
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;
    ImageView view;

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        view = (ImageView) v;
        if(startMatrix == null) {
            startMatrix = new Matrix(view.getImageMatrix());
            startMatrix.getValues(startParams);
        }
        matrix.set(view.getImageMatrix());
        view.setScaleType(ImageView.ScaleType.MATRIX);
        float scale;

        // dumpEvent(event);
        // Handle touch events here...
        float [] params;
        switch (event.getAction() & MotionEvent.ACTION_MASK)
        {
            case MotionEvent.ACTION_DOWN:   // first finger down only
                savedMatrix.set(matrix);
                start.set(event.getX(), event.getY());
                Log.d(TAG, "mode=DRAG"); // write to LogCat
                mode = DRAG;
                break;

            case MotionEvent.ACTION_UP: // first finger lifted

            case MotionEvent.ACTION_POINTER_UP: // second finger lifted
                params = new float[9];
                matrix.getValues(params);

                Log.d(TAG, String.format("%f", (params[Matrix.MTRANS_X] - startParams[Matrix.MTRANS_X])));
                Log.d(TAG, String.format("%f", (params[Matrix.MTRANS_Y] - startParams[Matrix.MTRANS_Y])));

                if(params[Matrix.MSCALE_X]/startParams[Matrix.MSCALE_X] < 1){
                    while(params[Matrix.MSCALE_X]/startParams[Matrix.MSCALE_X] < 1) {
                        matrix.postScale(1.01f, 1.01f, mid.x, mid.y);
                        matrix.getValues(params);
                    }
                }else if(params[Matrix.MSCALE_X]/startParams[Matrix.MSCALE_X] > 5){
                    while(params[Matrix.MSCALE_X]/startParams[Matrix.MSCALE_X] > 5){
                        matrix.postScale(.99f, .99f, mid.x, mid.y);
                        matrix.getValues(params);
                    }
                }
                while((params[Matrix.MTRANS_X] - startParams[Matrix.MTRANS_X]) > 200){
                    matrix.postTranslate(-1, 0);
                    matrix.getValues(params);
                }
                while((params[Matrix.MTRANS_Y] - startParams[Matrix.MTRANS_Y]) > 200){
                    matrix.postTranslate(0, -1);
                    matrix.getValues(params);
                }
                while((params[Matrix.MTRANS_X] - startParams[Matrix.MTRANS_X]) < -300 *
                        pow(params[Matrix.MSCALE_X]/startParams[Matrix.MSCALE_X], 1.8) ||
                        (params[Matrix.MTRANS_X] - startParams[Matrix.MTRANS_X]) < -4000){
                    matrix.postTranslate(1, 0);
                    matrix.getValues(params);
                }
                while((params[Matrix.MTRANS_Y] - startParams[Matrix.MTRANS_Y]) < -300 *
                        pow(params[Matrix.MSCALE_Y]/startParams[Matrix.MSCALE_Y], 2) ||
                        (params[Matrix.MTRANS_Y] - startParams[Matrix.MTRANS_Y]) < -5500){
                    matrix.postTranslate(0, 1);
                    matrix.getValues(params);
                }

                mode = NONE;
                Log.d(TAG, "mode=NONE");
                break;
            case MotionEvent.ACTION_POINTER_DOWN: // first and second finger down

                oldDist = spacing(event);
                Log.d(TAG, "oldDist=" + oldDist);
                if (oldDist > 5f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, event);
                    mode = ZOOM;
                    Log.d(TAG, "mode=ZOOM");
                }
                break;

            case MotionEvent.ACTION_MOVE:

                if (mode == DRAG)
                {
                    matrix.set(savedMatrix);
                    matrix.postTranslate(event.getX() - start.x, event.getY() - start.y); // create the transformation in the matrix  of points
                }
                else if (mode == ZOOM)
                {
                    // pinch zooming
                    float newDist = spacing(event);
                    Log.d(TAG, "newDist=" + newDist);
                    if (newDist > 5f)
                    {
                        matrix.set(savedMatrix);
                        params = new float[9];
                        matrix.getValues(params);
                        scale = newDist / oldDist; // setting the scaling of the
                        // matrix...if scale > 1 means
                        // zoom in...if scale < 1 means
                        // zoom out
                        matrix.postScale(scale, scale, mid.x, mid.y);

                        Log.d(TAG, "Global scale=" + params[Matrix.MSCALE_X]/startParams[Matrix.MSCALE_X]);

                    }
                }
                break;
        }

        view.setImageMatrix(matrix); // display the transformation on screen

        return true; // indicate event was handled
    }

    /*
     * --------------------------------------------------------------------------
     * Method: spacing Parameters: MotionEvent Returns: float Description:
     * checks the spacing between the two fingers on touch
     * ----------------------------------------------------
     */

    private float spacing(MotionEvent event)
    {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    /*
     * --------------------------------------------------------------------------
     * Method: midPoint Parameters: PointF object, MotionEvent Returns: void
     * Description: calculates the midpoint between the two fingers
     * ------------------------------------------------------------
     */

    private void midPoint(PointF point, MotionEvent event)
    {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    /** Show an event in the LogCat view, for debugging */
    private void dumpEvent(MotionEvent event)
    {
        String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE","POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
        StringBuilder sb = new StringBuilder();
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        sb.append("event ACTION_").append(names[actionCode]);

        if (actionCode == MotionEvent.ACTION_POINTER_DOWN || actionCode == MotionEvent.ACTION_POINTER_UP)
        {
            sb.append("(pid ").append(action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
            sb.append(")");
        }

        sb.append("[");
        for (int i = 0; i < event.getPointerCount(); i++)
        {
            sb.append("#").append(i);
            sb.append("(pid ").append(event.getPointerId(i));
            sb.append(")=").append((int) event.getX(i));
            sb.append(",").append((int) event.getY(i));
            if (i + 1 < event.getPointerCount())
                sb.append(";");
        }

        sb.append("]");
        Log.d("Touch Events ---------", sb.toString());
    }

    public void zoomIn(View v){
        Rect rectf = new Rect();
        v.getLocalVisibleRect(rectf);
        float x = rectf.left + (float)(rectf.width()) / 2;
        float y = rectf.top + (float)(rectf.height()) / 2;
        float[] params = new float[9];
        matrix.getValues(params);
        if(params[Matrix.MSCALE_X]/startParams[Matrix.MSCALE_X] < 4.8){
            matrix.postScale(1.8f, 1.8f, x, y);
            view.setImageMatrix(matrix);
        }
    }

    public void zoomOut(View v){
        Rect rectf = new Rect();
        v.getLocalVisibleRect(rectf);
        float x = rectf.left + (float)(rectf.width()) / 2;
        float y = rectf.top + (float)(rectf.height()) / 2;
        float[] params = new float[9];
        matrix.getValues(params);
        if(params[Matrix.MSCALE_X]/startParams[Matrix.MSCALE_X] > 1.2){
            matrix.postScale(.6f, .6f, x, y);
            view.setImageMatrix(matrix);
        }
    }
}
