package com.example.clicker

import android.content.SharedPreferences
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class PlayActivity : AppCompatActivity() {

    companion object {
        private const val PREFS_FILE = "Results"
    }

    lateinit var l_timer: TextView
    lateinit var l_score: TextView
    lateinit var l_game_over: TextView

    lateinit var i_heart: ImageView
    lateinit var i_bomb: ImageView

    lateinit var b_start: Button
    lateinit var b_save: Button

    var currentTime = 10
    var currentScore = 0

    lateinit var pref: SharedPreferences
    lateinit var timer: CountDownTimer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play)
        pref = getSharedPreferences(PREFS_FILE, MODE_PRIVATE)

        l_timer = findViewById(R.id.timeLabel)
        l_score = findViewById(R.id.scoreLabel)
        l_game_over = findViewById(R.id.gameOverLabel)

        i_heart = findViewById(R.id.heartImage)
        i_bomb = findViewById(R.id.bombImage)

        b_start = findViewById(R.id.startButton)
        b_save = findViewById(R.id.saveButton)

        i_bomb.visibility = View.GONE
        l_game_over.visibility = View.GONE
        i_heart.isEnabled = false

        timer = object: CountDownTimer(10000, 1000) {
            override fun onTick(p0: Long) {
                currentTime--
                val time = currentTime + 1
                l_timer.text = "Time: $time"
            }

            override fun onFinish() {
                l_timer.text = "Time: 0"
                b_start.isEnabled = true
                b_save.isEnabled = true
                i_heart.isEnabled = false
                l_game_over.visibility = View.VISIBLE
            }
        }

        b_start.setOnClickListener {
            currentTime = 10
            currentScore = 0
            l_game_over.visibility = View.GONE

            l_score.text = "Score: $currentScore"
            l_timer.text = "Time: $currentTime"

            b_start.isEnabled = false
            b_save.isEnabled = false
            i_heart.isEnabled = true
            timer.start()
        }

        i_heart.setOnClickListener {
            currentScore++
            l_score.text = "Score: $currentScore"
        }

        b_save.setOnClickListener {
            val editor: SharedPreferences.Editor = pref.edit()
            val date = Date()
            val dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())
            editor.putString(dateFormat.format(date), currentScore.toString())
            editor.apply()
        }

    }
}