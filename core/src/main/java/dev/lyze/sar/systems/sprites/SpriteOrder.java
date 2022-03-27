package dev.lyze.sar.systems.sprites;

import lombok.Getter;

public enum SpriteOrder {
    BACKGROUND(-100), BEHIND_PLAYER(-10), PLAYER(0), INFRONT_PLAYER(10), FOREGROUND(100);

    @Getter private final int order;

    SpriteOrder(int order) {
        this.order = order;
    }
}
