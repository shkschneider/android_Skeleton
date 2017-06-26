package me.shkschneider.skeleton.java;

import android.annotation.TargetApi;
import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.content.ComponentName;
import android.os.PersistableBundle;
import android.support.annotation.NonNull;

import me.shkschneider.skeleton.helper.AndroidHelper;
import me.shkschneider.skeleton.helper.ContextHelper;
import me.shkschneider.skeleton.helper.SystemServices;

/**
 * <service
 *  android:name=".MyJobService"
 *  android:permission="android.permission.BIND_JOB_SERVICE" />
 */
@TargetApi(AndroidHelper.API_21)
public class JobManager {

    public static void cancelAll() {
        SystemServices.jobScheduler().cancelAll();
    }

    public static void cancel(final int id) {
        SystemServices.jobScheduler().cancel(id);
    }

    private int mId;
    private JobInfo.Builder mBuilder;

    public JobManager(final int id, @NonNull final Class<? extends JobService> cls) {
        mId = id;
        final ComponentName componentName = new ComponentName(ContextHelper.applicationContext(), cls.getClass());
        mBuilder = new JobInfo.Builder(id, componentName);
    }

    @SkHide
    public JobManager setExtras(final PersistableBundle extras) {
        mBuilder.setExtras(extras);
        return this;
    }

    @SkHide
    public JobManager setRequiredNetworkType(final int networkType) {
        mBuilder.setRequiredNetworkType(networkType);
        return this;
    }

    @SkHide
    public JobManager setRequiresCharging(final boolean requiresCharging) {
        mBuilder.setRequiresCharging(requiresCharging);
        return this;
    }

    @SkHide
    public JobManager setRequiresDeviceIdle(final boolean requiresDeviceIdle) {
        mBuilder.setRequiresDeviceIdle(requiresDeviceIdle);
        return this;
    }

    @SkHide
    public JobManager addTriggerContentUri(@NonNull final JobInfo.TriggerContentUri uri) {
        mBuilder.addTriggerContentUri(uri);
        return this;
    }

    @SkHide
    public JobManager setTriggerContentUpdateDelay(final long durationMs) {
        mBuilder.setTriggerContentUpdateDelay(durationMs);
        return this;
    }

    @SkHide
    public JobManager setTriggerContentMaxDelay(final long durationMs) {
        mBuilder.setTriggerContentMaxDelay(durationMs);
        return this;
    }

    @SkHide
    public JobManager setPeriodic(final long intervalMillis) {
        mBuilder.setPeriodic(intervalMillis);
        return this;
    }

    @SkHide
    public JobManager setPeriodic(final long intervalMillis, final long flexMillis) {
        mBuilder.setPeriodic(intervalMillis, flexMillis);
        return this;
    }

    @SkHide
    public JobManager setMinimumLatency(final long minLatencyMillis) {
        mBuilder.setMinimumLatency(minLatencyMillis);
        return this;
    }

    @SkHide
    public JobManager setOverrideDeadline(final long maxExecutionDelayMillis) {
        mBuilder.setOverrideDeadline(maxExecutionDelayMillis);
        return this;
    }

    @SkHide
    public JobManager setBackoffCriteria(final long initialBackoffMillis, final int backoffPolicy) {
        mBuilder.setBackoffCriteria(initialBackoffMillis, backoffPolicy);
        return this;
    }

    @SkHide
    public JobManager setPersisted(final boolean isPersisted) {
        mBuilder.setPersisted(isPersisted);
        return this;
    }

    public boolean schedule() {
        final JobInfo jobInfo = mBuilder.build();
        mId = jobInfo.getId();
        return (SystemServices.jobScheduler().schedule(jobInfo) == JobScheduler.RESULT_SUCCESS);
    }

    public void cancel() {
        SystemServices.jobScheduler().cancel(mId);
    }

}
