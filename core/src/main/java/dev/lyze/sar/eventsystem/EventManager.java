package dev.lyze.sar.eventsystem;

import dev.lyze.sar.eventsystem.events.Event;

import java.util.ArrayList;
import java.util.HashMap;

public class EventManager
{
    private final HashMap<Class<? extends Event>, ArrayList<EventListener<?>>> events = new HashMap<>();
    private final HashMap<Class<? extends Event>, ArrayList<EventListener<?>>> preEvents = new HashMap<>();

    /**
     * Registers a listener which fires before all `addListener` listeners fire.
     */
    public <TEvent extends Event> void addPreListener(EventListener<TEvent> listener) {
        if (!preEvents.containsKey(listener.getClazz()))
            preEvents.put(listener.getClazz(), new ArrayList<>());

        preEvents.get(listener.getClazz()).add(listener);
    }

    /**
     * Registers a listener which fires for that specified event.
     */
    public <TEvent extends Event> void addListener(EventListener<TEvent> listener)
    {
        if (!events.containsKey(listener.getClazz()))
            events.put(listener.getClazz(), new ArrayList<>());

        events.get(listener.getClazz()).add(listener);
    }

    /**
     * Fires a specific event. Calling all `addPreListener` listeners first, then calling all `addListener`s.
     */
    public <TEvent extends Event> void fire(TEvent event)
    {
        fireOnList(event, preEvents);
        fireOnList(event, events);
    }

    private <TEvent extends Event> void fireOnList(TEvent event, HashMap<Class<? extends Event>, ArrayList<EventListener<?>>> events) {
        if (!events.containsKey(event.getClass()))
            return;

        ArrayList<EventListener<?>> get = events.get(event.getClass());

        for (int i = 0; i < get.size(); i++) {
            EventListener<?> eventListener = get.get(i);
            if (eventListener.shouldCastAndFire(event))
                eventListener.castAndFire(event);
        }
    }

    public void removeListenersOfBind(Object bind)
    {
        events.forEach((clazz, listeners) -> listeners.removeIf(l -> bind.equals(l.getBind())));
    }
}
