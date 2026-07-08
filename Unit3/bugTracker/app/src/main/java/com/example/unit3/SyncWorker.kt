package com.example.unit3

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters

class SyncWorker (
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {

        val repository = IssueRepository(applicationContext as android.app.Application)

        return try {
            repository.syncPendingIssues()

            Result.success()

        } catch (e: Exception) {
            Result.retry()
        }
    }
}