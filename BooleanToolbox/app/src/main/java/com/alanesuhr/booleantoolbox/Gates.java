package com.alanesuhr.booleantoolbox;

import android.graphics.Path;
import android.graphics.RectF;

/**
 * Created by benjamin on 10/4/14.
 */
public class Gates {
    public static Path Empty() {
        Path out = new Path();
        return out;
    }

    public static Path Bauble() {
        Path out = new Path();

        out.addCircle(0f, 0f, 10f, Path.Direction.CCW);

        return out;
    }

    public static Path And() {
        Path out = new Path();

        out.arcTo(new RectF(50,0,150,100), -90f, 180f);
        out.lineTo(50f, 100f);
        out.lineTo(50f, 0f);
        out.close();

        out.moveTo(50f, 25f);
        out.lineTo(0f, 25f);

        out.moveTo(50f, 75f);
        out.lineTo(0f, 75f);

        return out;
    }

    public static Path Or() {
        Path out = new Path();

        out.arcTo(new RectF(-25, 0, 75, 100), -90f, 180f);
        out.lineTo(100f, 100f);
        out.moveTo(25f, 0f);
        out.arcTo(new RectF(50, 0, 150, 100), -90f, 180f);

        out.moveTo(69f, 25f);
        out.lineTo(0f, 25f);

        out.moveTo(69f, 75f);
        out.lineTo(0f, 75f);

        return out;
    }

    public static Path Xor() {
        Path out = new Path();

        out.arcTo(new RectF(-25, 0, 75, 100), -80f, 160f);
        out.lineTo(100f, 100f);
        out.moveTo(30f, 0f);
        out.arcTo(new RectF(50, 0, 150, 100), -90f, 180f);


        Path temp = new Path();
        temp.arcTo(new RectF(-50, 0, 50, 100), -80f, 160f);
        out.addPath(temp);

        out.moveTo(69f, 25f);
        out.lineTo(0f, 25f);

        out.moveTo(69f, 75f);
        out.lineTo(0f, 75f);

        return out;
    }

    public static Path Buf() {
        Path out = new Path();

        out.moveTo(50f, 0f);
        out.lineTo(50f, 100f);
        out.lineTo(130f, 50f);
        out.close();

        out.moveTo(50f, 50f);
        out.lineTo(0f, 50f);

        out.offset(0f, -25f);

        return out;
    }

    public static Path Const(boolean in) {
        Path out = new Path();

        if(in) {
            out.moveTo(0f, 0f);
            out.lineTo(25f, 0f);
            out.moveTo(12.5f, 0f);
            out.lineTo(12.5f, 50f);
        } else {
            out.moveTo(0f, 0f);
            out.lineTo(25f, 0f);
            out.moveTo(0f, 25f);
            out.lineTo(20f, 25f);
            out.moveTo(0f, 0f);
            out.lineTo(0f, 50f);
        }

        return out;
    }

    public static Path Variable(BoolExpr.Variable var) {
        Path out = new Path();

        switch (var) {
            case A:
                out.moveTo(0f, 50f);
                out.lineTo(12.5f, 0f);
                out.lineTo(25f, 50f);
                out.moveTo(20f, 25f);
                out.lineTo(5f, 25f);
                break;
            case B:
                out.moveTo(0f, 50f);
                out.lineTo(0f, 0f);
                out.arcTo(new RectF(-15, 0, 15, 25), -90f, 180f);
                out.arcTo(new RectF(-20, 25, 20, 50), -90f, 180f);
                break;
            case C:
                out.moveTo(25f, 0f);
                out.arcTo(new RectF(0, 0, 50, 50), -90f, -180f);
                break;
            case D:
                out.moveTo(0f, 50f);
                out.lineTo(0f, 0f);
                out.arcTo(new RectF(-25, 0, 25, 50), -90f, 180f);
                break;
            case W:
                out.moveTo(0f, 0f);
                out.lineTo(7.5f, 50f);
                out.lineTo(12.5f, 20f); // modify height later
                out.lineTo(17.5f, 50f);
                out.lineTo(25f, 0f);
                break;
            case X:
                out.moveTo(0f, 0f);
                out.lineTo(25f, 50f);
                out.moveTo(25f, 0f);
                out.lineTo(0f, 50f);
                break;
            case Y:
                out.moveTo(0f, 0f);
                out.lineTo(12.5f, 25f);
                out.lineTo(25f, 0f);
                out.moveTo(12.5f, 25f);
                out.lineTo(12.5f, 50f);
                break;
            case Z:
                out.moveTo(0f, 0f);
                out.lineTo(25f, 0f);
                out.lineTo(0f, 50f);
                out.lineTo(25f, 50f);
                break;
        }
        return out;
    }
}
