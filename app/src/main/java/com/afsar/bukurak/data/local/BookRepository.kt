package com.afsar.bukurak.data.local

import com.afsar.bukurak.data.Book
import kotlinx.coroutines.flow.Flow


class BookRepository(private val bookDao: BookDao) {


    val getAllBooks: Flow<List<Book>> = bookDao.getAllTodos()


    fun getBookById(id: Long): Flow<Book?> {
        return bookDao.getTodoById(id)
    }

    suspend fun insert(todo: Book) {
        bookDao.insert(todo)
    }

    suspend fun update(todo: Book) {
        bookDao.update(todo)
    }

    suspend fun delete(todo: Book) {
        bookDao.delete(todo)
    }
}