package dev.lyze.sar;

import lombok.Getter;

public enum CharacterEnum {
    FOX("Fox"), ZEALOT("Zealot"), LYZE("Lyze");

    @Getter private final String path;

    CharacterEnum(String path) {
        this.path = path;
    }
}
