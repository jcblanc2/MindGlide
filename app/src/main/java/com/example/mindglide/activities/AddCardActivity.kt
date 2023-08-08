package com.example.mindglide.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import com.example.mindglide.R

class AddCardActivity : AppCompatActivity() {
    private lateinit var etQuestion : EditText
    private lateinit var etAnswer : EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        // Look up the question, answer editText and the cancel, save button in layout
        etQuestion = findViewById(R.id.etQuestion)
        etAnswer = findViewById(R.id.etAnswer)
        val cancelBtn = findViewById<ImageView>(R.id.ivCancelBtn)
        val saveBtn = findViewById<ImageView>(R.id.ivSaveBtn)

        // This extracts any data that was passed back from MainActivity
        etQuestion.setText(intent.getStringExtra("question"))
        etAnswer.setText(intent.getStringExtra("answer"))

        // Add onClickListener to the cancel and save button
        cancelBtn.setOnClickListener{
            finish()
        }

        saveBtn.setOnClickListener {
            onSave()
        }
    }

    // closes AddCardActivity and returns to MainActivity and put our data
    private fun onSave() {
        val data = Intent()

        // Pass relevant data back as a result
        data.putExtra("question", etQuestion.text.toString())
        data.putExtra("answer", etAnswer.text.toString())

        setResult(RESULT_OK, data) // set result code and bundle data for response

        finish() // closes the activity, pass data to parent
    }
}