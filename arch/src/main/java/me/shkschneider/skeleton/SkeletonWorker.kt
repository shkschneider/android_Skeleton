package me.shkschneider.skeleton

import androidx.lifecycle.LiveData
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.Worker
import me.shkschneider.skeleton.helperx.Logger
import java.util.UUID
import java.util.concurrent.TimeUnit

/**
 * "WorkManager is like a JobManagerCompat."
 * WorkManager is a library used to enqueue work that is guaranteed to execute after its constraints
 * (or none) are met.
 *
 * WorkManager uses an underlying job dispatching service when available:
 *  - Uses JobScheduler for API 23+
 *  - For API 14-22:
 *    - If using Firebase JobDispatcher in the app and the optional Firebase dependency, uses Firebase JobDispatcher
 *    - Otherwise, uses a custom AlarmManager + BroadcastReceiver implementation
 *
 * @see <https://developer.android.com/reference/androidx/work/WorkManager>
 */
object SkeletonWorker {

    fun manager(): WorkManager {
        return WorkManager.getInstance()
    }

    // Single-Shot

    fun <T: Worker> enqueue(tag: String, worker: Class<T>, constraints: Constraints? = null, data: Data? = null): UUID {
        val workBuilder = OneTimeWorkRequest.Builder(worker).addTag(tag)
        constraints?.let {
            workBuilder.setConstraints(it)
        }
        data?.let {
            workBuilder.setInputData(it)
        }
        val work = workBuilder.build()
        manager().enqueue(work)
        return work.id
    }

    fun <T: Worker> uniqueWork(tag: String, worker: Class<T>, constraints: Constraints? = null, data: Data? = null): UUID {
        val workBuilder = OneTimeWorkRequest.Builder(worker).addTag(tag)
        constraints?.let {
            workBuilder.setConstraints(it)
        }
        data?.let {
            workBuilder.setInputData(it)
        }
        val work = workBuilder.build()
        manager().beginUniqueWork(tag, ExistingWorkPolicy.REPLACE, work).enqueue()
        return work.id
    }

    // Multi-Shot

    /**
     * It may run immediately, at the end of the period, or any time in between so long as the
     * other conditions are satisfied at the time.
     */
    fun <T: Worker> uniquePeriodicWork(tag: String, worker: Class<T>, repeatInterval: Long, timeUnit: TimeUnit,
                                       constraints: Constraints? = null, data: Data? = null): UUID? {
        if (timeUnit.toMillis(repeatInterval) < PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS) {
            Logger.warning("The intervalMillis must be greater than or equal to: ${PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS}")
            return null
        }
        val workBuilder = PeriodicWorkRequest.Builder(worker, repeatInterval, timeUnit).addTag(tag)
        constraints?.let {
            workBuilder.setConstraints(it)
        }
        data?.let {
            workBuilder.setInputData(it)
        }
        val work = workBuilder.build()
        manager().enqueueUniquePeriodicWork(tag, ExistingPeriodicWorkPolicy.REPLACE, work)
        return work.id
    }

    /**
     * Creates a PeriodicWorkRequest to run periodically once within the flex period of every
     * interval period. The flex period begins at (intervalMillis - flexMillis) to the end of
     * the interval.
     */
    fun <T: Worker> enqueueUniquePeriodicWork(tag: String, worker: Class<T>, repeatInterval: Long, timeUnit: TimeUnit,
                                              flexInterval: Long, flexIntervalTimeUnit: TimeUnit,
                                              constraints: Constraints? = null, data: Data? = null): UUID? {
        if (timeUnit.toMillis(repeatInterval) < PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS) {
            Logger.warning("intervalMillis must be greater than or equal to: ${PeriodicWorkRequest.MIN_PERIODIC_INTERVAL_MILLIS}")
            return null
        }
        if (flexIntervalTimeUnit.toMillis(flexInterval) < PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS) {
            Logger.warning("flexMillis must be greater than or equal to: ${PeriodicWorkRequest.MIN_PERIODIC_FLEX_MILLIS}")
            return null
        }
        val workBuilder = PeriodicWorkRequest.Builder(worker, repeatInterval, timeUnit, flexInterval, flexIntervalTimeUnit).addTag(tag)
        constraints?.let {
            workBuilder.setConstraints(it)
        }
        data?.let {
            workBuilder.setInputData(it)
        }
        val work = workBuilder.build()
        manager().enqueueUniquePeriodicWork(tag, ExistingPeriodicWorkPolicy.REPLACE, work)
        return work.id
    }

    /**
     * ENQUEUED
     * RUNNING
     * SUCCEEDED
     * FAILED
     * BLOCKED
     * CANCELLED
     */

    fun getWorkInfoById(id: UUID): LiveData<WorkInfo> {
        return manager().getWorkInfoByIdLiveData(id)
    }

    fun getWorkInfosByTag(tag: String): LiveData<List<WorkInfo>> {
        return manager().getWorkInfosByTagLiveData(tag)
    }

    fun getWorkInfosForUniqueWork(tag: String): LiveData<List<WorkInfo>> {
        return manager().getWorkInfosForUniqueWorkLiveData(tag)
    }

    fun cancelAllWork() {
        manager().cancelAllWork()
    }

    fun cancelAllWorkByTag(tag: String) {
        manager().cancelAllWorkByTag(tag)
    }

    fun cancelUniqueWork(tag: String) {
        manager().cancelUniqueWork(tag)
    }

}
