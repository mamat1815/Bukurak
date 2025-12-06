package com.afsar.bukurak.utility

import android.app.Application
import com.afsar.bukurak.data.local.AppDatabase
import com.afsar.bukurak.data.local.BookRepository

class BookApplication : Application() {
    val database by lazy { AppDatabase.Companion.getDatabase(this) }
    val repository by lazy { BookRepository(database.todoDao()) }
}