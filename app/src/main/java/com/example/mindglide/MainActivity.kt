package com.example.mindglide

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Look up the question and answer textview and  in layout
        val tvFlashcardQuestion = findViewById<TextView>(R.id.tvFlashcardQuestion)
        val tvFlashcardAnswer = findViewById<TextView>(R.id.tvFlashcardAnswer)

        // Set an onClickListener for the question text view
        tvFlashcardQuestion.setOnClickListener {
            tvFlashcardQuestion.visibility = View.INVISIBLE
            tvFlashcardAnswer.visibility = View.VISIBLE
        }
    }
}
