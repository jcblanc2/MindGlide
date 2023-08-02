package com.example.mindglide

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Look up the question and answers textview and  in layout
        val tvFlashcardQuestion = findViewById<TextView>(R.id.tvFlashcardQuestion)
        val tvAnswerClinton = findViewById<TextView>(R.id.tvAnswerClinton)
        val tvAnswerBush = findViewById<TextView>(R.id.tvAnswerBush)
        val tvAnswerObama = findViewById<TextView>(R.id.tvAnswerObama)

        // Add an onClickListener for the question textview
//        tvFlashcardQuestion.setOnClickListener {
//            tvFlashcardQuestion.visibility = View.INVISIBLE
//            tvFlashcardAnswer.visibility = View.VISIBLE
//        }
//
//        // Add an onClickListener to the question textview
//        tvFlashcardAnswer.setOnClickListener {
//            tvFlashcardQuestion.visibility = View.VISIBLE
//            tvFlashcardAnswer.visibility = View.INVISIBLE
//        }
    }
}