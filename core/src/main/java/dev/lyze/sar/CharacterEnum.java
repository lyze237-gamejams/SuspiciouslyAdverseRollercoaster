package dev.lyze.sar;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum CharacterEnum {
    FOX("Fox", "Landing", 1), ZEALOT("Zealot", "Landing", 1), LYZE("Lyze", "CrouchIn", 3);

    @Getter private final String path;
    @Getter private final String gameOver;
    @Getter private final int gameOverIndex;
}
