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
    private val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()

    override fun onCreate() {
        Log.d("WM/D", "oncreate")
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