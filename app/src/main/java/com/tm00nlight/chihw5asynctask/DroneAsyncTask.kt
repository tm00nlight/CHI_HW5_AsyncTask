package com.tm00nlight.chihw5asynctask

import android.os.Handler
import android.os.Looper

abstract class DroneAsyncTask<Integer, String> {

    fun execute() {
        onPreExecute()
        val thread = Thread {
            doInBackground().let { result ->
                Handler(Looper.getMainLooper()).post { onPostExecute(result) }
            }
        }
        thread.start()
    }

    abstract fun onPreExecute()
    abstract fun doInBackground(): String
    abstract fun onProgressUpdate(timeLeft: Integer)
    abstract fun onPostExecute(result: String)
}