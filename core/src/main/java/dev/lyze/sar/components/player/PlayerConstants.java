package dev.lyze.sar.components.player;

import com.badlogic.gdx.math.Vector2;

public class PlayerConstants {
    public Vector2 maxVelocity = new Vector2(14, 24);

    public float slowDownXToGravity = 3f;
    public float slowDownXGravitySpeed = 4f;

    public float trackAcceleration = 100f;
    public float trackDeceleration = -100f;

    public float trackMinSpeed = 4f;
}
