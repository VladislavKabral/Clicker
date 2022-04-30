package com.example.clicker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var b_play: Button
    lateinit var b_statistic: Button
    lateinit var b_about: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        b_play = findViewById(R.id.playButton)
        b_statistic = findViewById(R.id.statisticButton)
        b_about = findViewById(R.id.aboutButton)

        b_play.setOnClickListener {
            val intent : Intent = Intent(this, PlayActivity::class.java)
            startActivity(intent)
        }

        b_statistic.setOnClickListener{
            val intent : Intent = Intent(this, StatisticActivity::class.java)
            startActivity(intent)
        }

        b_about.setOnClickListener {
            val intent : Intent = Intent(this, AboutActivity::class.java)
            startActivity(intent)
        }
    }
}