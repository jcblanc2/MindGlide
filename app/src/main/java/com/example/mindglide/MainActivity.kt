package com.example.mindglide

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var isShowingAnswers = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Look up the answers textview and eye icon in layout
        val tvAnswerClinton = findViewById<TextView>(R.id.tvAnswerClinton)
        val tvAnswerBush = findViewById<TextView>(R.id.tvAnswerBush)
        val tvAnswerObama = findViewById<TextView>(R.id.tvAnswerObama)
        val ivToggleChoicesVisibility = findViewById<ImageView>(R.id.toggle_choices_visibility)

        // Add onClickListener to the answers textview
        tvAnswerClinton.setOnClickListener {
            tvAnswerClinton.setBackgroundColor(resources.getColor(R.color.red, null))
            tvAnswerObama.setBackgroundColor(resources.getColor(R.color.green, null))
        }

        tvAnswerBush.setOnClickListener {
            tvAnswerBush.setBackgroundColor(resources.getColor(R.color.red, null))
            tvAnswerObama.setBackgroundColor(resources.getColor(R.color.green, null))
        }

        tvAnswerObama.setOnClickListener {
            tvAnswerObama.setBackgroundColor(resources.getColor(R.color.green, null))
        }

        // Add onClickListener to the eye icon
        ivToggleChoicesVisibility.setOnClickListener {
            isShowingAnswers = !isShowingAnswers

            if (!isShowingAnswers) {
                ivToggleChoicesVisibility.setImageResource(R.drawable.show_icon_foreground)
                hideAnswers()
            } else {
                ivToggleChoicesVisibility.setImageResource(R.drawable.hide_icon_foreground)
                showAnswers()
            }
        }
    }

    private fun hideAnswers() {
        findViewById<TextView>(R.id.tvAnswerClinton).visibility = View.INVISIBLE
        findViewById<TextView>(R.id.tvAnswerBush).visibility = View.INVISIBLE
        findViewById<TextView>(R.id.tvAnswerObama).visibility = View.INVISIBLE
    }

    private fun showAnswers() {
        findViewById<TextView>(R.id.tvAnswerClinton).visibility = View.VISIBLE
        findViewById<TextView>(R.id.tvAnswerBush).visibility = View.VISIBLE
        findViewById<TextView>(R.id.tvAnswerObama).visibility = View.VISIBLE
    }
}
