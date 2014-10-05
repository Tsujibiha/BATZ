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
import android.view.ViewGroup;

/**
 * Created by benjamin on 10/4/14.
 */
public class CircuitView extends View {

    private class SubPath {
        public Path path;
        float yOut;
        float xOut;
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
        mPath.path.offset(10f, 20f);
        mPath.path.moveTo(mPath.xOut + 60f, mPath.yOut + 20f);
        mPath.path.lineTo(mPath.xOut + 100f, mPath.yOut + 20f);

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
                path.xOut = 100f;

                drawChildren(path, expr.getChildA(), expr.getChildB());
                break;
            case OR:
                path.path = Gates.Or();
                path.yOut = 50f;
                path.xOut = 100f;

                pathA = drawGates(expr.getChildA());
                pathB = drawGates(expr.getChildB());
                stitchPaths(path, pathA, pathB);
                break;
            case XOR:
                path.path = Gates.Xor();
                path.yOut = 50f;
                path.xOut = 100f;

                pathA = drawGates(expr.getChildA());
                pathB = drawGates(expr.getChildB());
                stitchPaths(path, pathA, pathB);
                break;
            case CONST:
                if(expr.getInverted()) {
                    BoolExpr tmp = BoolExpr.makeBuf(expr);
                    path = drawGates(tmp);
                } else {
                    path.path = Gates.Const(expr.getConstant());
                    path.yOut = 25f;
                    path.xOut = 25f;
                }
                break;
            case VAR:
                if(expr.getInverted()) {
                    expr.setInverted(false);
                    BoolExpr tmp = BoolExpr.makeNot(expr);
                    path = drawGates(tmp);
                } else {
                    path.path = Gates.Variable(expr.getVariable());
                    path.yOut = 25f;
                    path.xOut = 25f;
                }
                break;
            case BUF:
                path.path = Gates.Buf();
                pathA = drawGates(expr.getChildA());
                stitchPaths(path, pathA);
                path.yOut = 50f;
                path.xOut = 150f;
                break;
        }
        handleInversion(path, expr.getInverted());
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
        path.xOut += xShift;
    }

    private void stitchPaths(SubPath path, SubPath pathA) {
        RectF boundsA = new RectF();
        pathA.path.computeBounds(boundsA, true);

        float xShift = boundsA.right + 50f;
        float yShift = boundsA.bottom / 2.0f;

        Log.d(TAG, "Chosen shift: (" + xShift + "," + yShift + ")");

        path.path.offset(xShift, yShift);
        path.path.addPath(pathA.path);
        path.path.moveTo(boundsA.right, pathA.yOut);
        path.path.lineTo(xShift, yShift + 25);

        path.yOut += yShift;
        path.xOut += xShift;
    }

    private void handleInversion(SubPath path, boolean inverted) {
        if(inverted) {
            path.path.addPath(Gates.Bauble(), path.xOut + 60f, path.yOut);
            path.xOut += 20f;
        }
    }

    private void drawChildren(SubPath parent, BoolExpr childA, BoolExpr childB) {
        if(childA != childB) {
            SubPath pathA = drawGates(childA);
            SubPath pathB = drawGates(childB);
            stitchPaths(parent, pathA, pathB);
        } else {
            Log.d(TAG, childA.toString());
            parent.path.moveTo(0f, 25f);
            parent.path.lineTo(0f, 75f);
            SubPath childPath = drawGates(childA);
            stitchPaths(parent, childPath);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        RectF rect = new RectF();
        mPath.path.computeBounds(rect,true);

        setMeasuredDimension((int)rect.width()+20,(int)rect.height()+40);
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawPath(mPath.path, mPaint);

    }
}
