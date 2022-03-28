package dev.lyze.sar.components.cart;

import com.badlogic.gdx.math.Vector2;

public class CartConstants {
    public float slowDownXToGravity = 3f;
    public float slowDownXGravitySpeed = 4f;

    public float trackAcceleration = 100f;
    public float trackDeceleration = -100f;

    public float trackMinSpeed = 5f;

    public Vector2 jumpVelocity = new Vector2(0f, 20f);
}
