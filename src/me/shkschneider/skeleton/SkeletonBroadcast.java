package me.shkschneider.skeleton;

import android.content.Intent;
import android.os.Bundle;

import me.shkschneider.skeleton.helpers.ApplicationHelper;

@SuppressWarnings("unused")
public final class SkeletonBroadcast extends Intent {

    public SkeletonBroadcast(final String action, final SkeletonEvent skeletonEvent) {
        super(String.format("%s.action.BROADCAST", ApplicationHelper.packageName()));

        final Bundle bundle = new Bundle();
        bundle.putSerializable("event", skeletonEvent);
        putExtras(bundle);
    }

    public SkeletonBroadcast(final SkeletonEvent skeletonEvent) {
        this(String.format("%s.action.BROADCAST", ApplicationHelper.packageName()), skeletonEvent);
    }

    public void send() {
        SkeletonApplication.CONTEXT.sendBroadcast(this);
    }

}
