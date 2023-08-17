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
    private lateinit var tvNoCards : TextView
    private lateinit var addBtn : ImageView
    private lateinit var ivEditBtn : ImageView
    private lateinit var ivNextBtn : ImageView
    private lateinit var ivDeleteBtn : ImageView
    private lateinit var ivNoCards : ImageView
    private lateinit var flashcardDatabase : FlashcardDatabase
    private  var allFlashcards = mutableListOf<Flashcard>()
    private var previousRandomNumber = -1
    private lateinit var cardToEdit: Flashcard

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Look up the views in layout
        initializeViews()

        // initialize the flashcardDatabase variable and read from the database
        flashcardDatabase = FlashcardDatabase(this)
        allFlashcards = flashcardDatabase.getAllCards().toMutableList()

        if (allFlashcards.size > 0) {
            setUpFlashcardViews(index = getRandomNumber(0, allFlashcards.size - 1))
        }else{
            showEmptyState()
        }

        // Add onClickListener to the save, edit, next and delete button and question textview
        addBtn.setOnClickListener {
            val intent = Intent(this, AddCardActivity::class.java)
            addCardActivityResultLauncher.launch(intent)
        }

        ivEditBtn.setOnClickListener {
            for (card in allFlashcards) {
                if (card.question == tvFlashcardQuestion.text) {
                    cardToEdit.question = card.question
                    cardToEdit.answer = card.answer
                }
            }

            startAddCardActivity()
        }

        ivNextBtn.setOnClickListener {
            getNext()
        }

        ivDeleteBtn.setOnClickListener {
            deleteCard()
        }

        tvFlashcardQuestion.setOnClickListener{
            showAnswer()
        }
    }

    private fun initializeViews(){
        tvFlashcardQuestion = findViewById(R.id.tvFlashcardQuestion)
        tvFlashcardAnswer = findViewById(R.id.tvFlashcardAnswer)
//        tvFlashcardAnswer2 = findViewById(R.id.tvFlashcardAnswer2)
//        tvFlashcardAnswer3 = findViewById(R.id.tvFlashcardAnswer3)
        tvNoCards = findViewById(R.id.tvNoCards)
        addBtn = findViewById(R.id.ivAddBtn)
        ivEditBtn = findViewById(R.id.ivEditBtn)
        ivNextBtn = findViewById(R.id.ivNextBtn)
        ivDeleteBtn = findViewById(R.id.ivDeleteBtn)
        ivNoCards = findViewById(R.id.ivNoCards)
    }

    private fun startAddCardActivity(){
        val intent = Intent(this, AddCardActivity::class.java)
        intent.putExtra("question", tvFlashcardQuestion.text.toString())
        intent.putExtra("answer", tvFlashcardAnswer.text.toString())
        editResultLauncher.launch(intent)
    }

    private fun getNext(){
        if (allFlashcards.size == 0) {
            return
        }

        hideAnswer()

        setUpFlashcardViews(index = getRandomNumber(0, allFlashcards.size - 1))
    }

    private fun deleteCard(){
        flashcardDatabase.deleteCard(tvFlashcardQuestion.text.toString())
        allFlashcards = flashcardDatabase.getAllCards().toMutableList()

        if (allFlashcards.size == 0){
            showEmptyState()
        }else{
            setUpFlashcardViews(index = getRandomNumber(0, allFlashcards.size - 1))
        }
    }

    private fun showEmptyState(){
        ivNextBtn.visibility = View.GONE
        ivEditBtn.visibility = View.GONE
        ivDeleteBtn.visibility = View.GONE
        tvFlashcardQuestion.visibility = View.GONE
        tvFlashcardAnswer.visibility = View.GONE
        ivNoCards.visibility = View.VISIBLE
        tvNoCards.visibility = View.VISIBLE
    }

    private fun hideEmptyState(){
        ivNextBtn.visibility = View.VISIBLE
        ivEditBtn.visibility = View.VISIBLE
        ivDeleteBtn.visibility = View.VISIBLE
        tvFlashcardQuestion.visibility = View.VISIBLE
        tvFlashcardAnswer.visibility = View.INVISIBLE
        ivNoCards.visibility = View.GONE
        tvNoCards.visibility = View.GONE
    }

    private fun setUpFlashcardViews(index: Int){
        // set the question and answer TextViews with data from the database
        val (question, answer) = allFlashcards[index]

        tvFlashcardQuestion.text = question
        tvFlashcardAnswer.text = answer
    }

    private fun showAnswer(){
        tvFlashcardQuestion.visibility = View.INVISIBLE
        tvFlashcardAnswer.visibility = View.VISIBLE
    }

    private fun hideAnswer(){
        tvFlashcardQuestion.visibility = View.VISIBLE
        tvFlashcardAnswer.visibility = View.INVISIBLE
    }

    private fun getRandomNumber(minNumber: Int, maxNumber: Int): Int {
        var randomNumber = (minNumber..maxNumber).random()

        while (randomNumber == previousRandomNumber) {
            randomNumber = (minNumber..maxNumber).random()
        }

        previousRandomNumber = randomNumber

        return randomNumber
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

                hideEmptyState()

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

    private val editResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data

            if (data != null) {
                val question = data.extras!!.getString("question")
                val answer = data.extras!!.getString("answer")

                hideEmptyState()

                tvFlashcardQuestion.text = question
                tvFlashcardAnswer.text = answer

                // update flashcard to database
                if (question != null && answer != null) {
                    cardToEdit.question = question
                    cardToEdit.answer = answer

                    flashcardDatabase.updateCard(cardToEdit)
                    allFlashcards = flashcardDatabase.getAllCards().toMutableList()
                } else {
                    Log.e("TAG", "Missing question or answer to input into database.")
                }
            }
        }
    }
}