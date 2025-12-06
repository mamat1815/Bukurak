package com.afsar.bukurak.utility

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.afsar.bukurak.data.local.BookRepository
import com.afsar.bukurak.ui.viewmodel.BookViewModel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val dataRepository: BookRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(BookViewModel::class.java) -> BookViewModel(
                dataRepository
            ) as T
            else -> throw IllegalArgumentException("Unknown ViewModel: " + modelClass.name)
        }

    }
}