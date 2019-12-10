package me.shkschneider.skeleton.javax

import android.Manifest
import android.annotation.SuppressLint
import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.app.job.JobService
import android.os.PersistableBundle
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresPermission
import me.shkschneider.skeleton.extensions.android.ComponentName
import me.shkschneider.skeleton.helper.AndroidHelper
import me.shkschneider.skeleton.helper.ContextHelper
import me.shkschneider.skeleton.helperx.SystemServices

/**
 * <service android:name=".MyJobService" android:permission="android.permission.BIND_JOB_SERVICE"></service>
 */
@Suppress("DEPRECATION")
@Deprecated("Use WorkManager.")
open class JobManager(
        private var id: Int,
        cls: Class<out JobService>
) {

    @SuppressLint("JobSchedulerService")
    private val builder: JobInfo.Builder = JobInfo.Builder(this.id, ComponentName(ContextHelper.applicationContext(), cls::class))

    fun setExtras(extras: PersistableBundle): JobManager {
        builder.setExtras(extras)
        return this
    }

    fun setRequiredNetworkType(networkType: Int): JobManager {
        builder.setRequiredNetworkType(networkType)
        return this
    }

    fun setRequiresCharging(requiresCharging: Boolean): JobManager {
        builder.setRequiresCharging(requiresCharging)
        return this
    }

    fun setRequiresDeviceIdle(requiresDeviceIdle: Boolean): JobManager {
        builder.setRequiresDeviceIdle(requiresDeviceIdle)
        return this
    }

    @RequiresApi(AndroidHelper.API_24)
    fun addTriggerContentUri(uri: JobInfo.TriggerContentUri): JobManager {
        builder.addTriggerContentUri(uri)
        return this
    }

    @RequiresApi(AndroidHelper.API_24)
    fun setTriggerContentUpdateDelay(durationMs: Long): JobManager {
        builder.setTriggerContentUpdateDelay(durationMs)
        return this
    }

    @RequiresApi(AndroidHelper.API_24)
    fun setTriggerContentMaxDelay(durationMs: Long): JobManager {
        builder.setTriggerContentMaxDelay(durationMs)
        return this
    }

    fun setPeriodic(intervalMillis: Long): JobManager {
        builder.setPeriodic(intervalMillis)
        return this
    }

    @RequiresApi(AndroidHelper.API_24)
    fun setPeriodic(intervalMillis: Long, flexMillis: Long): JobManager {
        builder.setPeriodic(intervalMillis, flexMillis)
        return this
    }

    fun setMinimumLatency(minLatencyMillis: Long): JobManager {
        builder.setMinimumLatency(minLatencyMillis)
        return this
    }

    fun setOverrideDeadline(maxExecutionDelayMillis: Long): JobManager {
        builder.setOverrideDeadline(maxExecutionDelayMillis)
        return this
    }

    fun setBackoffCriteria(initialBackoffMillis: Long, backoffPolicy: Int): JobManager {
        builder.setBackoffCriteria(initialBackoffMillis, backoffPolicy)
        return this
    }

    @RequiresPermission(Manifest.permission.RECEIVE_BOOT_COMPLETED)
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
