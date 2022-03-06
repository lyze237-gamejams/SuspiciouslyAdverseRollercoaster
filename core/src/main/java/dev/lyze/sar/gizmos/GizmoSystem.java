package dev.lyze.sar.gizmos;

import com.artemis.BaseSystem;
import com.artemis.annotations.Wire;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.FloatArray;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import lombok.Setter;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class GizmoSystem extends BaseSystem {
    private final FloatArray lines = new FloatArray();
    private final FloatArray circles = new FloatArray();

    @Setter private float lineWidth = 1f;
    private final Color color = new Color(Color.WHITE);

    @Wire private ExtendViewport viewport;
    @Wire private ShapeDrawer drawer;

    @Override
    protected void begin() {
        drawer.getBatch().setProjectionMatrix(viewport.getCamera().combined);
        drawer.getBatch().begin();
    }

    @Override
    protected void processSystem() {
        for (int i = 0; i < lines.size; i += 9) {
            drawer.setColor(lines.get(i + 5), lines.get(i + 6), lines.get(i + 7), lines.get(i + 8));
            drawer.line(lines.get(i), lines.get(i + 1), lines.get(i + 2), lines.get(i + 3), lines.get(i + 4));
        }

        for (int i = 0; i < circles.size; i += 8) {
            drawer.setColor(circles.get(i + 4), circles.get(i + 5), circles.get(i + 6), circles.get(i + 7));
            drawer.circle(circles.get(i), circles.get(i + 1), circles.get(i + 2), circles.get(i + 3));
        }
    }

    @Override
    protected void end() {
        lines.clear();
        circles.clear();
        drawer.getBatch().end();
    }

    public void addLine(float x, float y, float x1, float y1) {
        lines.add(x);
        lines.add(y);
        lines.add(x1);
        lines.add(y1);

        lines.add(lineWidth);

        lines.add(color.r);
        lines.add(color.g);
        lines.add(color.b);
        lines.add(color.a);
    }

    public void addCircle(float x, float y, float radius) {
        circles.add(x);
        circles.add(y);
        circles.add(radius);

        circles.add(lineWidth);

        circles.add(color.r);
        circles.add(color.g);
        circles.add(color.b);
        circles.add(color.a);
    }

    public void setColor(Color color) {
        this.color.set(color);
    }
}
