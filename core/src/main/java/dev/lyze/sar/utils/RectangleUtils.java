package dev.lyze.sar.utils;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Polygon;
import lombok.var;

public class RectangleUtils {
    public static Polygon toPolygon(float posX, float posY, float width, float height, float rotation, Polygon polygon) {
        var verts = polygon.getVertices();
        var x = -width / 2f;
        var y = 0;

        verts[0] = x;
        verts[1] = y;

        verts[2] = x + width;
        verts[3] = y;

        verts[4] = x + width;
        verts[5] = y + height;

        verts[6] = x;
        verts[7] = y + height;

        polygon.setVertices(verts);
        polygon.setPosition(posX, posY);
        polygon.setRotation(rotation * MathUtils.radiansToDegrees);

        return polygon;
    }
}
