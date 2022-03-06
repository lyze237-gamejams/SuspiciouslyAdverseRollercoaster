package dev.lyze.sar.utils;

import com.badlogic.gdx.math.Polyline;
import com.badlogic.gdx.math.Vector2;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

public class IntersectionExtensions {
    public static Vector2 lineIntersectsLine(float line1StartX, float line1StartY, float line1EndX, float line1EndY, float line2StartX, float line2StartY, float line2EndX, float line2EndY, float tolerance, Vector2 outPoint) {
        return polylineIntersectsLine(new Line(line1StartX, line1StartY, line1EndX, line1EndY), new Line(line2StartX, line2StartY, line2EndX, line2EndY), tolerance, outPoint);
    }

    private static Vector2 polylineIntersectsLine(Line lineA, Line lineB, float tolerance, Vector2 outPoint) {
        float x1 = lineA.x1, y1 = lineA.y1;
        float x2 = lineA.x2, y2 = lineA.y2;

        float x3 = lineB.x1, y3 = lineB.y1;
        float x4 = lineB.x2, y4 = lineB.y2;

        // equations of the form x = c (two vertical lines)
        if (Math.abs(x1 - x2) < tolerance && Math.abs(x3 - x4) < tolerance && Math.abs(x1 - x3) < tolerance)
        {
            return null;
        }

        //equations of the form y=c (two horizontal lines)
        if (Math.abs(y1 - y2) < tolerance && Math.abs(y3 - y4) < tolerance && Math.abs(y1 - y3) < tolerance)
        {
            return null;
        }

        //equations of the form x=c (two vertical parallel lines)
        if (Math.abs(x1 - x2) < tolerance && Math.abs(x3 - x4) < tolerance)
        {
            //return default (no intersection)
            return null;
        }

        //equations of the form y=c (two horizontal parallel lines)
        if (Math.abs(y1 - y2) < tolerance && Math.abs(y3 - y4) < tolerance)
        {
            //return default (no intersection)
            return null;
        }

        //general equation of line is y = mx + c where m is the slope
        //assume equation of line 1 as y1 = m1x1 + c1
        //=> -m1x1 + y1 = c1 ----(1)
        //assume equation of line 2 as y2 = m2x2 + c2
        //=> -m2x2 + y2 = c2 -----(2)
        //if line 1 and 2 intersect then x1=x2=x & y1=y2=y where (x,y) is the intersection point
        //so we will get below two equations
        //-m1x + y = c1 --------(3)
        //-m2x + y = c2 --------(4)

        float x, y;

        //lineA is vertical x1 = x2
        //slope will be infinity
        //so lets derive another solution
        if (Math.abs(x1 - x2) < tolerance)
        {
            //compute slope of line 2 (m2) and c2
            float m2 = (y4 - y3) / (x4 - x3);
            float c2 = -m2 * x3 + y3;

            //equation of vertical line is x = c
            //if line 1 and 2 intersect then x1=c1=x
            //subsitute x=x1 in (4) => -m2x1 + y = c2
            // => y = c2 + m2x1
            x = x1;
            y = c2 + m2 * x1;
        }
        //lineB is vertical x3 = x4
        //slope will be infinity
        //so lets derive another solution
        else if (Math.abs(x3 - x4) < tolerance)
        {
            //compute slope of line 1 (m1) and c2
            float m1 = (y2 - y1) / (x2 - x1);
            float c1 = -m1 * x1 + y1;

            //equation of vertical line is x = c
            //if line 1 and 2 intersect then x3=c3=x
            //subsitute x=x3 in (3) => -m1x3 + y = c1
            // => y = c1 + m1x3
            x = x3;
            y = c1 + m1 * x3;
        }
        //lineA & lineB are not vertical
        //(could be horizontal we can handle it with slope = 0)
        else
        {
            //compute slope of line 1 (m1) and c2
            float m1 = (y2 - y1) / (x2 - x1);
            float c1 = -m1 * x1 + y1;

            //compute slope of line 2 (m2) and c2
            float m2 = (y4 - y3) / (x4 - x3);
            float c2 = -m2 * x3 + y3;

            //solving equations (3) & (4) => x = (c1-c2)/(m2-m1)
            //plugging x value in equation (4) => y = c2 + m2 * x
            x = (c1 - c2) / (m2 - m1);
            y = c2 + m2 * x;

            //verify by plugging intersection point (x, y)
            //in orginal equations (1) & (2) to see if they intersect
            //otherwise x,y values will not be finite and will fail this check
            if (!(Math.abs(-m1 * x + y - c1) < tolerance
                    && Math.abs(-m2 * x + y - c2) < tolerance))
            {
                //return default (no intersection)
                return null;
            }
        }

        //x,y can intersect outside the line segment since line is infinitely long
        //so finally check if x, y is within both the line segments
        if (isInsideLine(lineA, x, y) && isInsideLine(lineB, x, y))
        {
            outPoint.set(x, y);
            return outPoint;
        }

        //return default (no intersection)
        return null;
    }

    private static boolean isInsideLine(Line line, float x, float y)
    {
        return (x >= line.x1 && x <= line.x2
                || x >= line.x2 && x <= line.x1)
                && (y >= line.y1 && y <= line.y2
                || y >= line.y2 && y <= line.y1);
    }

    @NoArgsConstructor
    @AllArgsConstructor
    private static class Line
    {
        public float x1;
        public float y1;

        public float x2;
        public float y2;

        public Line(Vector2 start, Vector2 end) {
            x1 = start.x;
            y1 = start.y;

            x2 = end.x;
            y2 = end.y;
        }
    }
}
