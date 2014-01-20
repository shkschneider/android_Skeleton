package me.shkschneider.skeleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.shkschneider.skeleton.helpers.LogHelper;
import me.shkschneider.skeleton.java.MapHelper;
import me.shkschneider.skeleton.java.StringHelper;

@SuppressWarnings("unused")
public final class SkeletonEventController {

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

    public boolean registered(final Receiver receiver) {
        if (receiver == null) {
            LogHelper.warning("Receiver was NULL");
            return false;
        }

        for (final String event : MapHelper.keys(mReceivers)) {
            if (mReceivers.get(event).contains(receiver)) {
                return true;
            }
        }

        return false;
    }

    public List<String> registeredForEvents(final Receiver receiver) {
        if (receiver == null) {
            LogHelper.warning("Receiver was NULL");
            return null;
        }

        final List<String> events = new ArrayList<String>();
        for (final String event : MapHelper.keys(mReceivers)) {
            if (mReceivers.get(event).contains(receiver)) {
                events.add(event);
            }
        }

        return events;
    }

    public void register(final String event, final Receiver receiver) {
        // TODO: enable ALL events?
        if (! MapHelper.keys(mReceivers).contains(event)) {
            mReceivers.put(event, new ArrayList<Receiver>());
        }

        mReceivers.get(event).add(receiver);
    }

    public void unregister(final String event, final Receiver receiver) {
        if (receiver == null) {
            LogHelper.warning("Receiver was NULL");
            return ;
        }
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

    @Deprecated
    public void notify(final SkeletonEvent skeletonEvent) {
        if (skeletonEvent == null) {
            LogHelper.warning("SkeletonEvent was NULL");
            return ;
        }

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
