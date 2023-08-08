package com.example.mindglide.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.mindglide.R

class AddCardActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_card)

        // Look up the cancel icon in layout
        val cancelFloatingBtn = findViewById<ImageView>(R.id.cancelFloatingBtn)

        cancelFloatingBtn.setOnClickListener{
            finish()
        }
    }
}