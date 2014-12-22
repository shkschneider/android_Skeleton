package me.shkschneider.skeleton;

import android.support.annotation.NonNull;

import me.shkschneider.skeleton.helpers.LogHelper;
import me.shkschneider.skeleton.java.MapHelper;
import me.shkschneider.skeleton.java.StringHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkeletonEventController {

    private static SkeletonEventController INSTANCE;

    private Map<String, ArrayList<Receiver>> mReceivers;

    public static SkeletonEventController getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new SkeletonEventController();
        }
        return INSTANCE;
    }

    protected SkeletonEventController() {
        mReceivers = new HashMap<String, ArrayList<Receiver>>();
    }

    public void bind(@NonNull final String event, @NonNull final Receiver receiver) {
        // TODO: enable ALL events?
        if (! MapHelper.keys(mReceivers).contains(event)) {
            mReceivers.put(event, new ArrayList<Receiver>());
        }

        mReceivers.get(event).add(receiver);
    }

    public void unbind(@NonNull final String event, @NonNull final Receiver receiver) {
        if (! MapHelper.keys(mReceivers).contains(event)) {
            LogHelper.debug("No such event");
            return ;
        }

        if (event.equals(SkeletonEvents.ALL)) {
            for (final String e : MapHelper.keys(mReceivers)) {
                mReceivers.get(e).remove(receiver);
            }
        }
        else {
            if (! mReceivers.get(event).contains(receiver)) {
                LogHelper.debug("No such receiver for event");
                return ;
            }
            mReceivers.get(event).remove(receiver);
        }
    }

    public List<String> events(@NonNull final Receiver receiver) {
        final List<String> events = new ArrayList<String>();
        for (final String event : MapHelper.keys(mReceivers)) {
            if (mReceivers.get(event).contains(receiver)) {
                events.add(event);
            }
        }
        return events;
    }

    public void notify(@NonNull final SkeletonEvent skeletonEvent) {
        LogHelper.verbose("SkeletonEvent: " + skeletonEvent.toString());
        final String event = skeletonEvent.getEvent();
        if (StringHelper.nullOrEmpty(event)) {
            LogHelper.warning("Event was NULL");
            return ;
        }
        if (mReceivers.size() == 0) {
            LogHelper.debug("No receivers");
            return ;
        }
        if (! MapHelper.keys(mReceivers).contains(event)) {
            LogHelper.debug("No such event");
            return ;
        }
        if (mReceivers.get(event).size() == 0) {
            LogHelper.debug("No such receiver for this event");
            return ;
        }

        if (event.equals(SkeletonEvents.ALL)) {
            for (final String e : MapHelper.keys(mReceivers)) {
                for (final Receiver receiver : mReceivers.get(e)) {
                    receiver.onEvent(skeletonEvent);
                }
            }
        }
        else {
            for (final Receiver receiver : mReceivers.get(event)) {
                receiver.onEvent(skeletonEvent);
            }
        }
    }

    public static interface Receiver {

        public void onEvent(final SkeletonEvent skeletonEvent);

    }

}
