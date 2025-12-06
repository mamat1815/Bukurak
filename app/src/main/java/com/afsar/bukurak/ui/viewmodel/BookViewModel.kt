package com.afsar.bukurak.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.afsar.bukurak.data.Book
import com.afsar.bukurak.data.local.BookRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class BookViewModel(private val repository: BookRepository) : ViewModel() {

    val allBooks: StateFlow<List<Book>> = repository.getAllBooks.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5000L),
        initialValue = emptyList()
    )

    fun addBook(title: String, description: String, author: String, publishedYear: Int, pageCount: Int) {
        viewModelScope.launch {
            val newBook = Book(
                title = title,
                description = description,
                author = author,
                publishedYear = publishedYear,
                pageCount = pageCount
            )
            repository.insert(newBook)
        }
    }

    fun updateBook(todo: Book) {
        viewModelScope.launch {
            repository.update(todo)
        }
    }

    fun deleteBook(todo: Book) {
        viewModelScope.launch {
            repository.delete(todo)
        }
    }

    fun getBookById(id: Long): Flow<Book?> {
        return repository.getBookById(id)
    }
}