package com.alanesuhr.booleantoolbox;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.util.Log;
import android.view.View;

/**
 * Created by benjamin on 10/4/14.
 */
public class CircuitView extends View {

    private class SubPath {
        public Path path;
        float yOut;
    }

    static final String TAG = "CircuitView";

    private SubPath mPath;
    private Paint mPaint;

    public CircuitView(Context context, BoolExpr expr) {
        super(context);



        /*mPath = Gates.Or();
        mPath.offset(0, 120);
        mPath.addPath(Gates.And());
        mPath.offset(0, 120);
        mPath.addPath(Gates.Not());
        mPath.offset(100, 100);*/

        mPath = drawGates(expr);

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
    }

    private SubPath drawGates(BoolExpr expr) {
        SubPath path = new SubPath();

        SubPath pathA;
        SubPath pathB;

        switch(expr.getKind()) {
            case AND:
                path.path = Gates.And();
                path.yOut = 50f;

                pathA = drawGates(expr.getChildA());
                pathB = drawGates(expr.getChildB());
                stitchPaths(path, pathA, pathB);
                break;
            case OR:
                path.path = Gates.Or();
                path.yOut = 50f;

                pathA = drawGates(expr.getChildA());
                pathB = drawGates(expr.getChildB());
                stitchPaths(path, pathA, pathB);
                break;
            case XOR:
                path.path = new Path();
                break;
            case CONST:
                path.path = new Path();
                break;
            case VAR:
                path.path = new Path();
                break;
        }
        return path;
    }

    private void stitchPaths(SubPath path, SubPath pathA, SubPath pathB) {
        RectF boundsA = new RectF();
        pathA.path.computeBounds(boundsA, true);
        RectF boundsB = new RectF();
        pathB.path.computeBounds(boundsB, true);

        float xShift = Math.max(boundsA.right, boundsB.right) + 50f;
        float yShift = (boundsA.bottom + boundsB.bottom) / 2.0f;

        Log.d(TAG, "Chosen shift: (" + xShift + "," + yShift + ")");

        path.path.offset(xShift, -yShift);
        path.path.addPath(pathB.path);
        path.path.moveTo(boundsB.right, pathB.yOut);
        path.path.lineTo(xShift, -yShift + 75);

        path.path.offset(0f, 2 * yShift);
        path.path.addPath(pathA.path);
        path.path.moveTo(boundsA.right, pathA.yOut);
        path.path.lineTo(xShift, yShift + 25);

        path.yOut += yShift;
    }

    private void stitchPaths(Path path, Path pathA) {
        if(pathA.isEmpty()) {
            stitchPaths(path);
        } else {
            RectF boundsA = new RectF();
            pathA.computeBounds(boundsA, true);

            float xShift = boundsA.right + 50f;
            float yShift = boundsA.bottom;

            Log.d(TAG, "Chosen shift: (" + xShift + "," + yShift + ")");

            path.offset(xShift, yShift);
            path.addPath(pathA);

            path.moveTo(boundsA.right, boundsA.bottom - 50f);
            path.lineTo(xShift, yShift + 25f);

            path.moveTo(0f, yShift + 75f);
            path.lineTo(xShift, yShift + 75f);
        }
    }

    private void stitchPaths(Path path) {
        path.moveTo(0f, 25f);
        path.lineTo(50f, 25f);
        path.moveTo(0f, 75f);
        path.lineTo(50f, 75f);
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawPath(mPath.path, mPaint);
    }
}
