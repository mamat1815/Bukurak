package com.afsar.bukurak.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.afsar.bukurak.data.Book
import com.afsar.bukurak.ui.viewmodel.BookViewModel
import com.afsar.bukurak.utility.BookApplication
import com.afsar.bukurak.utility.ViewModelFactory

@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val bookViewModel: BookViewModel = viewModel(
        factory = ViewModelFactory((context.applicationContext as BookApplication).repository)
    )

    val bookList by bookViewModel.allBooks.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(bookList) { book ->
            BookItemCard(
                book = book,
                onClick = {
                    val intent = DetailActivity.newIntent(context, book)
                    context.startActivity(intent)
                }
            )
        }
    }
}

@Composable
fun BookItemCard(
    book: Book,
    onClick: () -> Unit,
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Text(book.title, style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(4.dp))
            Text(book.description)
            Spacer(Modifier.height(8.dp))
            Row {
                Text("Penulis: ${book.author}")
                Spacer(Modifier.weight(1f))
                Text("Tahun: ${book.publishedYear}")
            }
        }
    }
}
