package com.example.mindglide

import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mindglide.ui.theme.MindGlideTheme

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Look up the question and answer textview and  in layout
        val tvFlashcardQuestion = findViewById<TextView>(R.id.tvFlashcardQuestion)
        val tvFlashcardAnswer = findViewById<TextView>(R.id.tvFlashcardAnswer)

        // Set an onClickListener for the question text view
        tvFlashcardQuestion.setOnClickListener {
            tvFlashcardAnswer.visibility = View.VISIBLE
        }
    }
}
