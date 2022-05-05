package com.example.clicker

import android.annotation.SuppressLint
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

    private val SECOND = 1000;
    private val TIMER_COUNT = 10 * SECOND

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
    lateinit var pauseTimer: CountDownTimer
    lateinit var resumeTimer: CountDownTimer

    @SuppressLint("SetTextI18n")
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

        timer = object: CountDownTimer(TIMER_COUNT.toLong(), SECOND.toLong()) {
            override fun onTick(p0: Long) {
                currentTime--
                l_timer.text = "Time: $currentTime"
            }

            override fun onFinish() {
                l_timer.text = "Time: 0"
                b_start.isEnabled = true
                b_save.isEnabled = true
                i_heart.isEnabled = false
                l_game_over.visibility = View.VISIBLE
                i_heart.visibility = View.VISIBLE
            }
        }

        pauseTimer = object : CountDownTimer(SECOND.toLong(), SECOND.toLong()) {
            override fun onTick(p0: Long) {
                l_timer.text = "Time: $currentTime"
            }

            override fun onFinish() {
                i_bomb.visibility = View.GONE
                i_heart.visibility = View.VISIBLE
                resumeTimer.cancel()
                resumeTimer.start()
            }
        }

        resumeTimer = object : CountDownTimer((SECOND * currentTime).toLong(), SECOND.toLong()) {
            override fun onTick(p0: Long) {
                if (currentTime == 0) {
                    pauseTimer.cancel()
                    resumeTimer.onFinish()
                    resumeTimer.cancel()
                } else {
                    currentTime--
                    l_timer.text = "Time: $currentTime"
                }
            }

            override fun onFinish() {
                l_timer.text = "Time: 0"
                b_start.isEnabled = true
                b_save.isEnabled = true
                i_heart.isEnabled = false
                l_game_over.visibility = View.VISIBLE
                i_heart.visibility = View.VISIBLE
            }
        }

        b_start.setOnClickListener {
            currentTime = 10
            currentScore = 0
            l_game_over.visibility = View.GONE

            l_score.text = "Score: $currentScore"
            l_timer.text = "Time: $currentTime"

            i_heart.visibility = View.VISIBLE
            i_bomb.visibility = View.GONE
            b_start.isEnabled = false
            b_save.isEnabled = false
            i_heart.isEnabled = true
            timer.start()
        }

        i_heart.setOnClickListener {
            currentScore++
            l_score.text = "Score: $currentScore"
            generateBomb()
        }

        i_bomb.setOnClickListener {
            timer.onFinish()
            timer.cancel()
            pauseTimer.cancel()
            resumeTimer.onFinish()
            resumeTimer.cancel()
        }

        b_save.setOnClickListener {
            val editor: SharedPreferences.Editor = pref.edit()
            val date = Date()
            val dateFormat: DateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())
            editor.putString(dateFormat.format(date), currentScore.toString())
            editor.apply()
        }
    }

    private fun generateBomb() {

        val des = (1..5).random()
        if (des == 5) {
            i_bomb.visibility = View.VISIBLE
            i_heart.visibility = View.GONE
            timer.cancel()
            resumeTimer.cancel()
            pauseTimer.start()
        } else {
            i_bomb.visibility = View.GONE
            i_heart.visibility = View.VISIBLE
        }
    }

}