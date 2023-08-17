package com.example.mindglide.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.mindglide.R

class AddCardActivity : AppCompatActivity() {
    private lateinit var etQuestion : EditText
    private lateinit var etAnswer : EditText
    private lateinit var etWrongAnswer1 : EditText
    private lateinit var etWrongAnswer2 : EditText
    private lateinit var cancelBtn : ImageView
    private lateinit var saveBtn : ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        // Look up the views layout
        initializeViews()

        // This extracts any data that was passed back from MainActivity
        getData()

        // Add onClickListener to the cancel and save button
        cancelBtn.setOnClickListener{
            finish()
        }

        saveBtn.setOnClickListener {
            onSave()
        }
    }

    private fun initializeViews(){
        etQuestion = findViewById(R.id.etQuestion)
        etAnswer = findViewById(R.id.etAnswer)
        etWrongAnswer1 = findViewById(R.id.etWrongAnswer1)
        etWrongAnswer2 = findViewById(R.id.etWrongAnswer2)
        cancelBtn = findViewById(R.id.ivCancelBtn)
        saveBtn = findViewById(R.id.ivSaveBtn)
    }

    private fun getData(){
        etQuestion.setText(intent.getStringExtra("question"))
        etAnswer.setText(intent.getStringExtra("answer"))
        etWrongAnswer1.setText(intent.getStringExtra("wrong_answer_1"))
        etWrongAnswer2.setText(intent.getStringExtra("wrong_answer_2"))
    }

    // closes AddCardActivity and returns to MainActivity and put our data
    private fun onSave() {
        val data = Intent()

        val question = etQuestion.text.toString()
        val answer = etAnswer.text.toString()
        val wrongAnswer1 = etWrongAnswer1.text.toString()
        val wrongAnswer2 = etWrongAnswer2.text.toString()

        if (question.isNotEmpty() && answer.isNotEmpty() && wrongAnswer1.isNotEmpty() && wrongAnswer2.isNotEmpty()) {
            // Pass relevant data back as a result
            data.putExtra("question", question)
            data.putExtra("answer", answer)
            data.putExtra("wrong_answer_1", wrongAnswer1)
            data.putExtra("wrong_answer_2", wrongAnswer2)

            setResult(RESULT_OK, data) // set result code and bundle data for response
            finish() // closes the activity, pass data to parent
        }
        else {
            // show an error message in a Toast
            Toast.makeText(
                applicationContext,
                "Must enter both Question and Answers!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}