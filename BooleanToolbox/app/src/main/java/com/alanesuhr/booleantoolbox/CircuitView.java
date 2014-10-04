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
    static final String TAG = "CircuitView";

    private Path mPath;
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

    private Path drawGates(BoolExpr expr) {
        Path path = new Path();
        Path pathA;
        Path pathB;

        switch(expr.getKind()) {
            case AND:
                path.addPath(Gates.And());

                pathA = drawGates(expr.getChildA());
                pathB = drawGates(expr.getChildB());
                stitchPaths(path, pathA, pathB);
                break;
            case OR:
                path.addPath(Gates.Or());

                pathA = drawGates(expr.getChildA());
                pathB = drawGates(expr.getChildB());
                stitchPaths(path, pathA, pathB);
                break;
            case XOR:
                break;
            case CONST:

                break;
            case VAR:
                break;
        }
        return path;
    }

    private void stitchPaths(Path path, Path pathA, Path pathB) {
        if(pathA.isEmpty()) {
            stitchPaths(path, pathB);
        } else if(pathB.isEmpty()) {
            stitchPaths(path, pathA);
        } else {
            RectF boundsA = new RectF();
            pathA.computeBounds(boundsA, true);
            RectF boundsB = new RectF();
            pathB.computeBounds(boundsB, true);

            float xShift = Math.max(boundsA.right, boundsB.right) + 50f;
            float yShift = (boundsA.bottom + boundsB.bottom) / 2.0f;

            Log.d(TAG, "Chosen shift: (" + xShift + "," + yShift + ")");

            path.offset(xShift, -yShift);
            path.addPath(pathB);
            path.moveTo(0f, -yShift + 75);
            path.lineTo(xShift, -yShift + 75);

            path.offset(0f, 2 * yShift);
            path.addPath(pathA);
            path.moveTo(0f, yShift + 25);
            path.lineTo(xShift, yShift + 25);
        }
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
        canvas.drawPath(mPath, mPaint);
    }
}
