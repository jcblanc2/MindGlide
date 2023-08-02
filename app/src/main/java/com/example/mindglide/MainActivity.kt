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

        // Add an onClickListener for the question textview
        tvFlashcardQuestion.setOnClickListener {
            tvFlashcardQuestion.visibility = View.INVISIBLE
            tvFlashcardAnswer.visibility = View.VISIBLE
        }

        // Add an onClickListener to the question textview
        tvFlashcardAnswer.setOnClickListener {
            tvFlashcardQuestion.visibility = View.VISIBLE
            tvFlashcardAnswer.visibility = View.INVISIBLE
        }
    }
}