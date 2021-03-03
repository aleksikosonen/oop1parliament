package com.example.oop1parliament

import android.app.Application
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.work.*
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.example.oop1parliament.work.RefreshParliamentDataWorker
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.concurrent.TimeUnit

class MyApp : Application() {
    private val applicationScope = CoroutineScope(Dispatchers.Default)

    companion object {
        lateinit var appContext: Context
    }

    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        delayedInit()
    }

    private fun delayedInit() {
        applicationScope.launch {
            setupRecurringWork()
        }
    }

    /**
     * Setup WorkManager background job to 'fetch' new network data daily.
     */
    private fun setupRecurringWork() {
        val constraints = Constraints.Builder()
                .setRequiresBatteryNotLow(true)
                .apply {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        setRequiresDeviceIdle(true)
                    }
                }
                .build()

        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshParliamentDataWorker>(7, TimeUnit.DAYS)
                .setConstraints(constraints)
                .build()

        Log.d("WM/D", "WorkManager: Periodic Work request for sync is scheduled")

        WorkManager.getInstance().enqueueUniquePeriodicWork(
                RefreshParliamentDataWorker.RefreshDatabase,
                ExistingPeriodicWorkPolicy.KEEP,
                repeatingRequest)
    }
}