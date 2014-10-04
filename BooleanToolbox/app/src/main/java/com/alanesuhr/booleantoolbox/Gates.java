package com.alanesuhr.booleantoolbox;

import android.graphics.Path;
import android.graphics.RectF;

/**
 * Created by benjamin on 10/4/14.
 */
public class Gates {
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
}
