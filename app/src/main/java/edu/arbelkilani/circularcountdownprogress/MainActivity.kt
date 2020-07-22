package edu.arbelkilani.circularcountdownprogress

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import edu.arbelkilani.circularcountdown.CircularCountdown

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val circularCountdown = findViewById<CircularCountdown>(R.id.first)
        circularCountdown.setBorderColor(R.color.colorPrimaryDark)
        circularCountdown.setDiskColor(R.color.colorAccent)
        circularCountdown.setDuration(2000)
        circularCountdown.start()

        circularCountdown.onAnimationStart = {
            Toast.makeText(applicationContext, "onAnimationStart", Toast.LENGTH_SHORT).show()
        }

        circularCountdown.onAnimationEnd = {
            Toast.makeText(applicationContext, "onAnimationEnd", Toast.LENGTH_SHORT).show()
        }
    }
}