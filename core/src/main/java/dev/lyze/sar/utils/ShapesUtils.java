package dev.lyze.sar.utils;

import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Rectangle;

public class ShapesUtils {
    public static Polygon convertToPolygon(Rectangle rectangle) {
        return new Polygon(new float[] {
                rectangle.getX(), rectangle.getY(),
                rectangle.getX(), rectangle.getY() + rectangle.getHeight(),
                rectangle.getX() + rectangle.getWidth(), rectangle.getY() + rectangle.getHeight(),
                rectangle.getX() + rectangle.getWidth(), rectangle.getY()
        });
    }
}
