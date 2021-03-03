package com.example.oop1parliament.work

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.example.oop1parliament.MyApp
import com.example.oop1parliament.ParliamentMemberDB
import com.example.oop1parliament.repository.MemberRepository
import retrofit2.HttpException


class RefreshParliamentDataWorker(appContext: Context, params: WorkerParameters) : CoroutineWorker(appContext, params) {

    companion object {
        const val RefreshDatabase = "com.example.oop1parliament.work"
    }

    override suspend fun doWork(): Result {
        val database = ParliamentMemberDB.getInstance(MyApp.appContext)
        val repository = MemberRepository(database)

        try {
            repository.getMembers()

        } catch (e: HttpException) {
            return Result.retry()
        }

        return Result.success()
    }
}