package dev.lyze.sar;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CharacterEnum {
    FOX("Fox", "Landing", 1, false),
    ZEALOT("Zealot", "Landing", 1, false),
    LYZE("Lyze", "CrouchIn", 3, true);

    @Getter private final String path;
    @Getter private final String gameOver;
    @Getter private final int gameOverIndex;
    @Getter private final boolean hoverInAir;
}
