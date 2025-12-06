package com.afsar.bukurak.ui

import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.afsar.bukurak.data.Book
import com.afsar.bukurak.ui.theme.BukurakTheme

class DetailActivity : ComponentActivity() {
    private val bookItem: Book? by lazy {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            intent?.getParcelableExtra(BOOK_EXTRA_KEY, Book::class.java)
        } else {
            @Suppress("DEPRECATION")
            intent?.getParcelableExtra(BOOK_EXTRA_KEY)
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BukurakTheme {
                DetailScreen(
                    book = bookItem,
                    onBackClicked = { finish() }
                )
            }
        }
    }


    companion object {
        private const val BOOK_EXTRA_KEY = "book_item"
        fun newIntent(context: Context, book: Book): Intent {
            return Intent(context, DetailActivity::class.java).apply {
                putExtra(BOOK_EXTRA_KEY, book)
            }
        }
    }
}