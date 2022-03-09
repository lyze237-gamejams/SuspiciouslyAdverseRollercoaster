package dev.lyze.sar.components.player;

import com.badlogic.gdx.math.Vector2;

public class PlayerConstants {
    public final Vector2 maxVelocity = new Vector2(14, 24);

    public final float slowDownXToGravity = 3f;
    public final float slowDownXGravitySpeed = 4f;

    public final float trackAcceleration = 100f;
    public final float trackDeceleration = -100f;

    public final float trackMinSpeed = 4f;
}