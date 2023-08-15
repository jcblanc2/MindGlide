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
    private lateinit var etAnswer2 : EditText
    private lateinit var etAnswer3 : EditText
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
//        etAnswer2 = findViewById(R.id.etAnswer2)
//        etAnswer3 = findViewById(R.id.etAnswer3)
        cancelBtn = findViewById(R.id.ivCancelBtn)
        saveBtn = findViewById(R.id.ivSaveBtn)
    }

    private fun getData(){
        etQuestion.setText(intent.getStringExtra("question"))
        etAnswer.setText(intent.getStringExtra("answer"))
//        etAnswer2.setText(intent.getStringExtra("answer2"))
//        etAnswer3.setText(intent.getStringExtra("answer3"))
    }

    // closes AddCardActivity and returns to MainActivity and put our data
    private fun onSave() {
        val data = Intent()

        val question = etQuestion.text.toString()
        val answer = etAnswer.text.toString()
//        val answer2 = etAnswer2.text.toString()
//        val answer3 = etAnswer3.text.toString()
//                && answer2.isNotEmpty() && answer3.isNotEmpty()
        if (question.isNotEmpty() && answer.isNotEmpty() ) {
            // Pass relevant data back as a result
            data.putExtra("question", question)
            data.putExtra("answer", answer)
//            data.putExtra("answer2", answer2)
//            data.putExtra("answer3", answer3)

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