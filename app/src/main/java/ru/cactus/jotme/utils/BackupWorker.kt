package ru.cactus.jotme.utils

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters

class BackupWorker(
    context: Context,
    workerParameters: WorkerParameters
) : Worker(context, workerParameters) {
    override fun doWork(): Result {
        Log.d("TAG", "DO-WORK")
//        Toast.makeText(context, "Upload all notes", Toast.LENGTH_SHORT).show()
        return Result.success()
    }

}