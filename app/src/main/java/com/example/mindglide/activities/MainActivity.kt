package com.example.mindglide.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.ViewParent
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Snackbar
import com.example.mindglide.R
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var tvFlashcardQuestion : TextView
    private lateinit var tvFlashcardAnswer1 : TextView
    private lateinit var tvFlashcardAnswer2 : TextView
    private lateinit var tvFlashcardAnswer3 : TextView
    private lateinit var addBtn : ImageView
    private lateinit var ivEditBtn : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Look up the views in layout
        initializeViews()

        // Add onClickListener to the save, edit button and question textview
        addBtn.setOnClickListener {
            val intent = Intent(this, AddCardActivity::class.java)
            addCardActivityResultLauncher.launch(intent)
        }

        ivEditBtn.setOnClickListener {
            startAddCardActivity()
        }
    }

    private fun initializeViews(){
        tvFlashcardQuestion = findViewById(R.id.tvFlashcardQuestion)
        tvFlashcardAnswer1 = findViewById(R.id.tvFlashcardAnswer1)
        tvFlashcardAnswer2 = findViewById(R.id.tvFlashcardAnswer2)
        tvFlashcardAnswer3 = findViewById(R.id.tvFlashcardAnswer3)
        addBtn = findViewById(R.id.ivAddBtn)
        ivEditBtn = findViewById(R.id.ivEditBtn)
    }

    private fun startAddCardActivity(){
        val intent = Intent(this, AddCardActivity::class.java)
        intent.putExtra("question", tvFlashcardQuestion.text.toString())
        intent.putExtra("answer1", tvFlashcardAnswer1.text.toString())
        intent.putExtra("answer2", tvFlashcardAnswer2.text.toString())
        intent.putExtra("answer3", tvFlashcardAnswer3.text.toString())
        addCardActivityResultLauncher.launch(intent)
    }

    // This extracts any data that was passed back from AddCardActivity
    private val addCardActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data

            if (data != null) {
                val question = data.extras!!.getString("question")
                val answer1 = data.extras!!.getString("answer1")
                val answer2 = data.extras!!.getString("answer2")
                val answer3 = data.extras!!.getString("answer3")

                tvFlashcardQuestion.text = question
                tvFlashcardAnswer1.text = answer1
                tvFlashcardAnswer2.text = answer2
                tvFlashcardAnswer3.text = answer3

                // Get the root view of the activity
                val parentLayout = findViewById<View>(android.R.id.content)
                Snackbar.make(parentLayout, R.string.snackbar_text, Snackbar.LENGTH_LONG).show()
            }
        }
    }
}