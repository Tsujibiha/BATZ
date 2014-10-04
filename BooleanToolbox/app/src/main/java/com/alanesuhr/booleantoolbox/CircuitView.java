package com.alanesuhr.booleantoolbox;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.view.View;

/**
 * Created by benjamin on 10/4/14.
 */
public class CircuitView extends View {
    private Path mPath;
    private Paint mPaint;

    public CircuitView(Context context) {
        super(context);

        mPath = Gates.Or();
        mPath.offset(0, 120);
        mPath.addPath(Gates.And());
        mPath.offset(0, 120);
        mPath.addPath(Gates.Not());
        mPath.offset(100, 100);

        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.STROKE);
    }

    protected void onDraw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
    }
}
