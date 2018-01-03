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

    private var _id: Int
    private val _builder: JobInfo.Builder

    constructor(id: Int, cls: Class<out JobService>) {
        _id = id
        _builder = JobInfo.Builder(_id, ComponentName(ContextHelper.applicationContext(), cls.javaClass))
    }

    @SkHide
    fun setExtras(extras: PersistableBundle): JobManager {
        _builder.setExtras(extras)
        return this
    }

    @SkHide
    fun setRequiredNetworkType(networkType: Int): JobManager {
        _builder.setRequiredNetworkType(networkType)
        return this
    }

    @SkHide
    fun setRequiresCharging(requiresCharging: Boolean): JobManager {
        _builder.setRequiresCharging(requiresCharging)
        return this
    }

    @SkHide
    fun setRequiresDeviceIdle(requiresDeviceIdle: Boolean): JobManager {
        _builder.setRequiresDeviceIdle(requiresDeviceIdle)
        return this
    }

    @TargetApi(AndroidHelper.API_24)
    @SkHide
    fun addTriggerContentUri(uri: JobInfo.TriggerContentUri): JobManager {
        _builder.addTriggerContentUri(uri)
        return this
    }

    @TargetApi(AndroidHelper.API_24)
    @SkHide
    fun setTriggerContentUpdateDelay(durationMs: Long): JobManager {
        _builder.setTriggerContentUpdateDelay(durationMs)
        return this
    }

    @TargetApi(AndroidHelper.API_24)
    @SkHide
    fun setTriggerContentMaxDelay(durationMs: Long): JobManager {
        _builder.setTriggerContentMaxDelay(durationMs)
        return this
    }

    @SkHide
    fun setPeriodic(intervalMillis: Long): JobManager {
        _builder.setPeriodic(intervalMillis)
        return this
    }

    @TargetApi(AndroidHelper.API_24)
    @SkHide
    fun setPeriodic(intervalMillis: Long, flexMillis: Long): JobManager {
        _builder.setPeriodic(intervalMillis, flexMillis)
        return this
    }

    @SkHide
    fun setMinimumLatency(minLatencyMillis: Long): JobManager {
        _builder.setMinimumLatency(minLatencyMillis)
        return this
    }

    @SkHide
    fun setOverrideDeadline(maxExecutionDelayMillis: Long): JobManager {
        _builder.setOverrideDeadline(maxExecutionDelayMillis)
        return this
    }

    @SkHide
    fun setBackoffCriteria(initialBackoffMillis: Long, backoffPolicy: Int): JobManager {
        _builder.setBackoffCriteria(initialBackoffMillis, backoffPolicy)
        return this
    }

    @RequiresPermission(Manifest.permission.RECEIVE_BOOT_COMPLETED)
    @SkHide
    fun setPersisted(isPersisted: Boolean): JobManager {
        _builder.setPersisted(isPersisted)
        return this
    }

    fun schedule(): Boolean {
        val jobInfo = _builder.build()
        _id = jobInfo.id
        return SystemServices.jobScheduler()?.schedule(jobInfo) == JobScheduler.RESULT_SUCCESS
    }

    fun cancel() {
        SystemServices.jobScheduler()?.cancel(_id)
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
