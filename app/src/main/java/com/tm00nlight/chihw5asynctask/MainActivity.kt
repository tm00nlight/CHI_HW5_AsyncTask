package com.tm00nlight.chihw5asynctask

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val intro = findViewById<TextView>(R.id.intro)
        val timeLeftText = findViewById<TextView>(R.id.timeLeft)
        val buttonStart = findViewById<Button>(R.id.startButton)

        val task = object : DroneAsyncTask<Int, String>() {
            override fun onPreExecute() {
                Log.i("onPreExecute", "thread: ${Thread.currentThread().name}")

                intro.visibility = View.VISIBLE
                timeLeftText.visibility = View.VISIBLE
                buttonStart.isClickable = false
            }

            override fun doInBackground(): String {
                Log.i("doInBackground", "thread: ${Thread.currentThread().name}")

                var counter = 10
                while (counter > 0) {
                    Log.i("doInBackground", "counter = $counter")
                    runOnUiThread { onProgressUpdate(counter) }

                    Thread.sleep(1000)
                    counter--
                }

                return arrayOf(
                    "Поражен 1 танк противника",
                    "Численность вражеских войск сокращена на 20 ед.",
                    "Частично уничтожена вражеская колонна",
                    "Общие потери в боевой технике противника составили 4 ед.",
                    "Наступление врага остановлено"
                ).random()
            }

            override fun onProgressUpdate(timeLeft: Int) {
                Log.i("onProgressUpdate", "thread: ${Thread.currentThread().name}")

                timeLeftText.text = timeLeft.toString()
            }

            override fun onPostExecute(result: String) {
                Log.i("onPostExecute", "thread: ${Thread.currentThread().name}")

                Toast.makeText(this@MainActivity, result, Toast.LENGTH_LONG).show()

                intro.visibility = View.INVISIBLE
                timeLeftText.visibility = View.INVISIBLE
                buttonStart.isClickable = true
            }
        }

        buttonStart.setOnClickListener { task.execute() }
    }
}