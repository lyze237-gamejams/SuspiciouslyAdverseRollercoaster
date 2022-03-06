package dev.lyze.sar.eventsystem.events;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ResizeEvent extends Event {
    private int width;
    private int height;

    public ResizeEvent(int width, int height) {
        this.width = width;
        this.height = height;
    }
}
