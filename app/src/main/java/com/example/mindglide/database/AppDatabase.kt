package com.example.mindglide.database


import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mindglide.model.Flashcard

@Database(entities = [Flashcard::class], version = 1)

abstract class AppDatabase : RoomDatabase() {
    abstract fun flashcardDao(): FlashcardDao
}