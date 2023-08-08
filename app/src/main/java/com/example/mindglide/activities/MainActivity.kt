package com.example.mindglide.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.mindglide.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Look up plus icon in layout
        val ivToggleChoicesVisibility = findViewById<ImageView>(R.id.addFloatingBtn)

        // Add onClickListener to the plus icon
        ivToggleChoicesVisibility.setOnClickListener {
            val intent = Intent(this, AddCardActivity::class.java)
            startActivity(intent)
        }
    }
}
