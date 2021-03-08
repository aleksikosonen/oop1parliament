package com.example.oop1parliament

import android.app.Application
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

    //Myapp class for Workmanager, the workmanager is set to check if the data has changed every 7 days.
    //Also a constraint for connected network is set

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        delayedInit()
    }

    private fun delayedInit() {
        applicationScope.launch {
            setupRecurringWork()
        }
    }

    private fun setupRecurringWork() {

        val constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

        val repeatingRequest = PeriodicWorkRequestBuilder<RefreshParliamentDataWorker>(7, TimeUnit.DAYS)
                .setConstraints(constraints)
                .build()

        WorkManager.getInstance().enqueueUniquePeriodicWork(
                RefreshParliamentDataWorker.RefreshDatabase,
                ExistingPeriodicWorkPolicy.KEEP,
                repeatingRequest)
    }
}