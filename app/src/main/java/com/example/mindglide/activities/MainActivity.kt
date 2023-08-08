package com.example.mindglide.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.mindglide.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Look up the question, answer textview and the add and edit button in layout
        val tvFlashcardQuestion = findViewById<TextView>(R.id.tvFlashcardQuestion)
        val tvFlashcardAnswer = findViewById<TextView>(R.id.tvFlashcardAnswer)
        val addBtn = findViewById<ImageView>(R.id.ivAddBtn)
        val ivEditBtn = findViewById<ImageView>(R.id.ivEditBtn)

        // This extracts any data that was passed back from AddCardActivity
        val addCardActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data

                if (data != null) {
                    val question = data.getStringExtra("question")
                    val answer = data.getStringExtra("answer")

                    tvFlashcardQuestion.text = question
                    tvFlashcardAnswer.text = answer
                }
            }
        }

        // Add onClickListener to the save, edit button and question textview
        addBtn.setOnClickListener {
            val intent = Intent(this, AddCardActivity::class.java)
            addCardActivityResultLauncher.launch(intent)
        }

        tvFlashcardQuestion.setOnClickListener {
            tvFlashcardQuestion.visibility = View.INVISIBLE
            tvFlashcardAnswer.visibility = View.VISIBLE
        }

        ivEditBtn.setOnClickListener {
            val intent = Intent(this, AddCardActivity::class.java)
            intent.putExtra("question", tvFlashcardQuestion.text.toString())
            intent.putExtra("answer", tvFlashcardAnswer.text.toString())
            addCardActivityResultLauncher.launch(intent)
        }
    }
}