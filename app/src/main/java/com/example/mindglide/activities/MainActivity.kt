package com.example.mindglide.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.mindglide.R
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var tvFlashcardQuestion : TextView
    private lateinit var tvFlashcardAnswer : TextView
    private lateinit var tvFlashcardAnswer2 : TextView
    private lateinit var tvFlashcardAnswer3 : TextView
    private lateinit var addBtn : ImageView
    private lateinit var ivEditBtn : ImageView
    private lateinit var ivNextBtn : ImageView
    private lateinit var flashcardDatabase : FlashcardDatabase
    private  var allFlashcards = mutableListOf<Flashcard>()
    private var currentCardDisplayedIndex = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Look up the views in layout
        initializeViews()

        // initialize the flashcardDatabase variable and read from the database
        flashcardDatabase = FlashcardDatabase(this)
        allFlashcards = flashcardDatabase.getAllCards().toMutableList()

        if (allFlashcards.size > 0) {
            tvFlashcardQuestion.text = allFlashcards[0].question
            tvFlashcardAnswer.text = allFlashcards[0].answer
        }

        // Add onClickListener to the save, edit button and question textview
        addBtn.setOnClickListener {
            val intent = Intent(this, AddCardActivity::class.java)
            addCardActivityResultLauncher.launch(intent)
        }

        ivEditBtn.setOnClickListener {
            startAddCardActivity()
        }

        ivNextBtn.setOnClickListener {
            getNext()
        }

        tvFlashcardQuestion.setOnClickListener{
            tvFlashcardQuestion.visibility = View.INVISIBLE
            tvFlashcardAnswer.visibility = View.VISIBLE
        }
    }

    private fun initializeViews(){
        tvFlashcardQuestion = findViewById(R.id.tvFlashcardQuestion)
        tvFlashcardAnswer = findViewById(R.id.tvFlashcardAnswer)
//        tvFlashcardAnswer2 = findViewById(R.id.tvFlashcardAnswer2)
//        tvFlashcardAnswer3 = findViewById(R.id.tvFlashcardAnswer3)
        addBtn = findViewById(R.id.ivAddBtn)
        ivEditBtn = findViewById(R.id.ivEditBtn)
        ivNextBtn = findViewById(R.id.ivNextBtn)
    }

    private fun startAddCardActivity(){
        val intent = Intent(this, AddCardActivity::class.java)
        intent.putExtra("question", tvFlashcardQuestion.text.toString())
        intent.putExtra("answer1", tvFlashcardAnswer.text.toString())
//        intent.putExtra("answer2", tvFlashcardAnswer2.text.toString())
//        intent.putExtra("answer3", tvFlashcardAnswer3.text.toString())
        addCardActivityResultLauncher.launch(intent)
    }

    private fun getNext(){
        if (allFlashcards.size == 0) {
            return
        }

        tvFlashcardQuestion.visibility = View.VISIBLE
        tvFlashcardAnswer.visibility = View.INVISIBLE

        currentCardDisplayedIndex++

        // make sure we don't get an IndexOutOfBoundsError
        if(currentCardDisplayedIndex >= allFlashcards.size) {
            Snackbar.make(
                tvFlashcardQuestion,
                "You've reached the end of the cards, going back to start.",
                Snackbar.LENGTH_SHORT)
                .show()
            currentCardDisplayedIndex = 0
        }

        // set the question and answer TextViews with data from the database
//        allFlashcards = flashcardDatabase.getAllCards().toMutableList()
        val (question, answer) = allFlashcards[currentCardDisplayedIndex]

        tvFlashcardQuestion.text = question
        tvFlashcardAnswer.text = answer
    }

    // This extracts any data that was passed back from AddCardActivity
    private val addCardActivityResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data

            if (data != null) {
                val question = data.extras!!.getString("question")
                val answer = data.extras!!.getString("answer")
//                val answer2 = data.extras!!.getString("answer2")
//                val answer3 = data.extras!!.getString("answer3")

                tvFlashcardQuestion.text = question
                tvFlashcardAnswer.text = answer
//                tvFlashcardAnswer2.text = answer2
//                tvFlashcardAnswer3.text = answer3

                // Save newly created flashcard to database
                if (question != null && answer != null) {
                    flashcardDatabase.insertCard(Flashcard(question.toString(), answer.toString()))
                    allFlashcards = flashcardDatabase.getAllCards().toMutableList()
                } else {
                    Log.e("TAG", "Missing question or answer to input into database.")
                }

                // Get the root view of the activity
                val parentLayout = findViewById<View>(android.R.id.content)
                Snackbar.make(parentLayout, R.string.snackbar_text, Snackbar.LENGTH_LONG).show()
            }
        }
    }
}