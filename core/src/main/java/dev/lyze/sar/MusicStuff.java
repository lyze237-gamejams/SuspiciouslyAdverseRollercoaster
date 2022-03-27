package dev.lyze.sar;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import lombok.Getter;

public class MusicStuff {
    @Getter
    private static Music clouds, town;

    public static void init() {
        clouds = Gdx.audio.newMusic(Gdx.files.internal("Music/Above the Clouds 8-Bit.ogg"));
        town = Gdx.audio.newMusic(Gdx.files.internal("Music/Robot Town.ogg"));

        clouds.setVolume(0.2f);
        town.setVolume(0.2f);
    }
}
