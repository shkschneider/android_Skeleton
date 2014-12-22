package me.shkschneider.skeleton;

import java.util.HashMap;

public class SkeletonEvent extends HashMap<Object, Object> {

    private String mEvent;

    public SkeletonEvent(final String event) {
        mEvent = event;
    }

    public SkeletonEvent() {
        this(SkeletonEvents.ALL);
    }

    public SkeletonEvent put(final String key, final Object object) {
        super.put(key, object);

        return this;
    }

    public String getEvent() {
        return mEvent;
    }

    public void send() {
        SkeletonEventController.getInstance().notify(this);
    }

}
