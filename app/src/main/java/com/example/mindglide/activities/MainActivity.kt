package com.example.mindglide.activities

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.mindglide.R
import com.example.mindglide.animation.Presets
import com.example.mindglide.database.FlashcardDatabase
import com.example.mindglide.model.Flashcard
import com.google.android.material.snackbar.Snackbar
import nl.dionsegijn.konfetti.xml.KonfettiView

class MainActivity : AppCompatActivity() {
    private lateinit var tvFlashcardQuestion : TextView
    private lateinit var tvHideAnswer : TextView
    private lateinit var tvFlashcardAnswer : TextView
    private lateinit var tvWrongAnswer1 : TextView
    private lateinit var tvWrongAnswer2 : TextView
    private lateinit var tvTimer : TextView
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
    var countDownTimer: CountDownTimer? = null
    private lateinit var viewKonfetti: KonfettiView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Look up the views in layout
        initializeViews()

        // initialize the flashcardDatabase variable and read from the database
        flashcardDatabase = FlashcardDatabase(this)
        allFlashcards = flashcardDatabase.getAllCards().toMutableList()

        //  the countdown timer
        countDownTimer = object : CountDownTimer(16000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                val time = "" + millisUntilFinished / 1000
                tvTimer.text = time
            }

            override fun onFinish() {}
        }

        if (allFlashcards.size > 0) {
            setUpFlashcardViews(index = getRandomNumber(0, allFlashcards.size - 1))
        }else{
            showEmptyState()
        }

        // Add onClickListener to the save, edit, next and delete button and question textview
        addBtn.setOnClickListener {
            val intent = Intent(this, AddCardActivity::class.java)
            addCardActivityResultLauncher.launch(intent)
            overridePendingTransition(R.anim.right_in, R.anim.left_out)
        }

        ivEditBtn.setOnClickListener {
            for (card in allFlashcards) {
                if (card.question == tvFlashcardQuestion.text) {
                    cardToEdit = Flashcard(card.question, card.answer, card.wrongAnswer1, card.wrongAnswer2, card.uuid)
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

        tvFlashcardQuestion.setOnClickListener {
            tvFlashcardQuestion.cameraDistance = 25000F
            tvHideAnswer.cameraDistance = 25000F

            tvFlashcardQuestion.animate()
                .rotationY(90f)
                .setDuration(200)
                .withEndAction(
                    Runnable {
                        tvFlashcardQuestion.setVisibility(View.INVISIBLE)
                        tvHideAnswer.visibility = View.VISIBLE
                        // second quarter turn
                        tvHideAnswer.rotationY = -90f
                        tvHideAnswer.animate()
                            .rotationY(0f)
                            .setDuration(200)
                            .start()
                    }
                ).start()
        }

        // Add onClickListener to the answers textview
        tvWrongAnswer1.setOnClickListener {
            tvWrongAnswer1.setBackgroundColor(resources.getColor(R.color.red, null))
        }

        tvWrongAnswer2.setOnClickListener {
            tvWrongAnswer2.setBackgroundColor(resources.getColor(R.color.red, null))
        }

        tvFlashcardAnswer.setOnClickListener {
            tvFlashcardAnswer.setBackgroundColor(resources.getColor(R.color.green, null))
            viewKonfetti.start(Presets.explode())
        }
    }

    private fun initializeViews(){
        tvFlashcardQuestion = findViewById(R.id.tvFlashcardQuestion)
        tvHideAnswer = findViewById(R.id.tvHideAnswer)
        tvFlashcardAnswer = findViewById(R.id.tvFlashcardAnswer)
        tvWrongAnswer1 = findViewById(R.id.tvWrongAnswer1)
        tvWrongAnswer2 = findViewById(R.id.tvWrongAnswer2)
        tvTimer = findViewById(R.id.timer)
        tvNoCards = findViewById(R.id.tvNoCards)
        addBtn = findViewById(R.id.ivAddBtn)
        ivEditBtn = findViewById(R.id.ivEditBtn)
        ivNextBtn = findViewById(R.id.ivNextBtn)
        ivDeleteBtn = findViewById(R.id.ivDeleteBtn)
        ivNoCards = findViewById(R.id.ivNoCards)
        viewKonfetti = findViewById(R.id.konfettiView)
    }

    private fun startTimer() {
        countDownTimer?.cancel()
        countDownTimer?.start()
    }

    private fun startAddCardActivity(){
        val intent = Intent(this, AddCardActivity::class.java)
        intent.putExtra("question", tvFlashcardQuestion.text.toString())
        intent.putExtra("answer", tvFlashcardAnswer.text.toString())
        intent.putExtra("wrong_answer_1", tvWrongAnswer1.text.toString())
        intent.putExtra("wrong_answer_2", tvWrongAnswer2.text.toString())
        editResultLauncher.launch(intent)
        overridePendingTransition(R.anim.right_in, R.anim.left_out)
    }

    private fun getNext(){
        if (allFlashcards.size == 0) {
            return
        }

        val leftOutAnim = AnimationUtils.loadAnimation(this, R.anim.left_out)
        val rightInAnim = AnimationUtils.loadAnimation(this, R.anim.right_in)

        leftOutAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
            }

            override fun onAnimationRepeat(animation: Animation?) {
                // we don't need to worry about this method
            }
        })
        rightInAnim.setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
            }

            override fun onAnimationEnd(animation: Animation?) {
            }

            override fun onAnimationRepeat(animation: Animation?) {
                // we don't need to worry about this method
            }
        })

        setUpFlashcardViews(index = getRandomNumber(0, allFlashcards.size - 1))

        tvFlashcardQuestion.startAnimation(leftOutAnim)
        tvFlashcardQuestion.startAnimation(rightInAnim)
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
        tvHideAnswer.visibility = View.GONE
        tvTimer.visibility = View.GONE
        tvWrongAnswer1.visibility = View.GONE
        tvWrongAnswer2.visibility = View.GONE
        ivNoCards.visibility = View.VISIBLE
        tvNoCards.visibility = View.VISIBLE
    }

    private fun hideEmptyState(){
        ivNextBtn.visibility = View.VISIBLE
        ivEditBtn.visibility = View.VISIBLE
        ivDeleteBtn.visibility = View.VISIBLE
        tvFlashcardQuestion.visibility = View.VISIBLE
        tvFlashcardAnswer.visibility = View.VISIBLE
        tvTimer.visibility = View.VISIBLE
        tvWrongAnswer1.visibility = View.VISIBLE
        tvWrongAnswer2.visibility = View.VISIBLE
        ivNoCards.visibility = View.GONE
        tvNoCards.visibility = View.GONE
    }

    private fun setUpFlashcardViews(index: Int){
        // set the question and answer TextViews with data from the database
        val (question, answer, wrongAnswer1, wrongAnswer2) = allFlashcards[index]

        tvHideAnswer.visibility = View.INVISIBLE
        tvFlashcardQuestion.visibility = View.VISIBLE

        tvFlashcardQuestion.text = question
        tvFlashcardAnswer.text = answer
        tvHideAnswer.text = answer
        tvWrongAnswer1.text = wrongAnswer1
        tvWrongAnswer2.text = wrongAnswer2
        startTimer()
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
                val wrongAnswer1 = data.extras!!.getString("wrong_answer_1")
                val wrongAnswer2 = data.extras!!.getString("wrong_answer_2")

                hideEmptyState()

                tvFlashcardQuestion.text = question
                tvFlashcardAnswer.text = answer
                tvHideAnswer.text = answer
                tvWrongAnswer1.text = wrongAnswer1
                tvWrongAnswer2.text = wrongAnswer2

                // Save newly created flashcard to database
                if (question != null && answer != null && wrongAnswer1 != null && wrongAnswer2 != null) {
                    flashcardDatabase.insertCard(Flashcard(question, answer, wrongAnswer1, wrongAnswer2))
                    allFlashcards = flashcardDatabase.getAllCards().toMutableList()
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
                val wrongAnswer1 = data.extras!!.getString("wrong_answer_1")
                val wrongAnswer2 = data.extras!!.getString("wrong_answer_2")

                hideEmptyState()

                tvFlashcardQuestion.text = question
                tvFlashcardAnswer.text = answer
                tvHideAnswer.text = answer
                tvWrongAnswer1.text = wrongAnswer1
                tvWrongAnswer2.text = wrongAnswer2

                if (question != null && answer != null && wrongAnswer1 != null && wrongAnswer2 != null) {
                    cardToEdit.question = question
                    cardToEdit.answer = answer
                    cardToEdit.wrongAnswer1 = wrongAnswer1
                    cardToEdit.wrongAnswer2 = wrongAnswer2

                    flashcardDatabase.updateCard(cardToEdit)
                    allFlashcards = flashcardDatabase.getAllCards().toMutableList()
                }
            }
        }
    }
}
