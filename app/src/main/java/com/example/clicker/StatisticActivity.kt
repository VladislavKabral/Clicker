package com.example.clicker

import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.widget.TableLayout
import android.widget.TableRow
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class StatisticActivity : AppCompatActivity() {

    private lateinit var table : TableLayout
    private lateinit var pref : SharedPreferences
    companion object {
        private const val PREFS_FILE = "Results"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_statistic)
        table = findViewById(R.id.resultsTable)
        pref = getSharedPreferences(PREFS_FILE, MODE_PRIVATE)
        populateTable()
    }

    fun populateTable() {
        var i : Int = 0
        for (key in pref.all.keys) {
            val tableRaw = TableRow(this)
            val dateTextView = TextView(this)
            val scoreValueView = TextView(this)
            dateTextView.text = key.substring(0, 10)
            dateTextView.setTextColor(Color.BLACK)
            scoreValueView.text = pref.all[key].toString()
            scoreValueView.setTextColor(Color.BLACK)
            dateTextView.gravity = Gravity.CENTER
            scoreValueView.gravity = Gravity.CENTER
            dateTextView.width = 200
            dateTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18F)
            scoreValueView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18F)
            if (i % 2 == 0) {
                tableRaw.setBackgroundColor(Color.BLUE)
                dateTextView.setTextColor(Color.WHITE)
                scoreValueView.setTextColor(Color.WHITE)
            } else {
                tableRaw.setBackgroundColor(Color.WHITE)
                dateTextView.setTextColor(Color.BLACK)
                scoreValueView.setTextColor(Color.BLACK)
            }
            tableRaw.gravity = Gravity.CENTER
            table.addView(tableRaw)
            tableRaw.addView(dateTextView)
            tableRaw.addView(scoreValueView)
            i++
        }
    }
}