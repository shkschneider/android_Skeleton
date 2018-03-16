package me.shkschneider.skeleton.java

import android.Manifest
import android.annotation.TargetApi
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.app.job.JobService
import android.content.ComponentName
import android.os.PersistableBundle
import android.support.annotation.RequiresPermission

import me.shkschneider.skeleton.helper.AndroidHelper
import me.shkschneider.skeleton.helper.ContextHelper
import me.shkschneider.skeleton.helper.SystemServices

/**
 * <service android:name=".MyJobService" android:permission="android.permission.BIND_JOB_SERVICE"></service>
 */
@TargetApi(AndroidHelper.API_21)
class JobManager {

    private var id: Int
    private val builder: JobInfo.Builder

    constructor(id: Int, cls: Class<out JobService>) {
        this.id = id
        builder = JobInfo.Builder(this.id, ComponentName(ContextHelper.applicationContext(), cls::class.java))
    }

    @SkHide
    fun setExtras(extras: PersistableBundle): JobManager {
        builder.setExtras(extras)
        return this
    }

    @SkHide
    fun setRequiredNetworkType(networkType: Int): JobManager {
        builder.setRequiredNetworkType(networkType)
        return this
    }

    @SkHide
    fun setRequiresCharging(requiresCharging: Boolean): JobManager {
        builder.setRequiresCharging(requiresCharging)
        return this
    }

    @SkHide
    fun setRequiresDeviceIdle(requiresDeviceIdle: Boolean): JobManager {
        builder.setRequiresDeviceIdle(requiresDeviceIdle)
        return this
    }

    @TargetApi(AndroidHelper.API_24)
    @SkHide
    fun addTriggerContentUri(uri: JobInfo.TriggerContentUri): JobManager {
        builder.addTriggerContentUri(uri)
        return this
    }

    @TargetApi(AndroidHelper.API_24)
    @SkHide
    fun setTriggerContentUpdateDelay(durationMs: Long): JobManager {
        builder.setTriggerContentUpdateDelay(durationMs)
        return this
    }

    @TargetApi(AndroidHelper.API_24)
    @SkHide
    fun setTriggerContentMaxDelay(durationMs: Long): JobManager {
        builder.setTriggerContentMaxDelay(durationMs)
        return this
    }

    @SkHide
    fun setPeriodic(intervalMillis: Long): JobManager {
        builder.setPeriodic(intervalMillis)
        return this
    }

    @TargetApi(AndroidHelper.API_24)
    @SkHide
    fun setPeriodic(intervalMillis: Long, flexMillis: Long): JobManager {
        builder.setPeriodic(intervalMillis, flexMillis)
        return this
    }

    @SkHide
    fun setMinimumLatency(minLatencyMillis: Long): JobManager {
        builder.setMinimumLatency(minLatencyMillis)
        return this
    }

    @SkHide
    fun setOverrideDeadline(maxExecutionDelayMillis: Long): JobManager {
        builder.setOverrideDeadline(maxExecutionDelayMillis)
        return this
    }

    @SkHide
    fun setBackoffCriteria(initialBackoffMillis: Long, backoffPolicy: Int): JobManager {
        builder.setBackoffCriteria(initialBackoffMillis, backoffPolicy)
        return this
    }

    @RequiresPermission(Manifest.permission.RECEIVE_BOOT_COMPLETED)
    @SkHide
    fun setPersisted(isPersisted: Boolean): JobManager {
        builder.setPersisted(isPersisted)
        return this
    }

    fun schedule(): Boolean {
        val jobInfo = builder.build()
        id = jobInfo.id
        return SystemServices.jobScheduler()?.schedule(jobInfo) == JobScheduler.RESULT_SUCCESS
    }

    fun cancel() {
        SystemServices.jobScheduler()?.cancel(id)
    }

    companion object {

        fun cancelAll() {
            SystemServices.jobScheduler()?.cancelAll()
        }

        fun cancel(id: Int) {
            SystemServices.jobScheduler()?.cancel(id)
        }

    }

}
