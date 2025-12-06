package com.afsar.bukurak.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.afsar.bukurak.data.Book
import com.afsar.bukurak.ui.viewmodel.BookViewModel
import com.afsar.bukurak.utility.BookApplication
import com.afsar.bukurak.utility.ViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(onBackClicked: () -> Unit,
                 book: Book?) {
    val context = LocalContext.current
    val bookViewModel: BookViewModel = viewModel(
        factory = ViewModelFactory((context.applicationContext as BookApplication).repository)
    )

    val liveTodo by bookViewModel.getBookById(book?.id ?: -1L)
        .collectAsState(initial = book)

    var isInEditMode by remember { mutableStateOf(false) }

    var editedTitle by remember(liveTodo) { mutableStateOf(liveTodo?.title ?: "") }
    var editedDesc by remember(liveTodo) { mutableStateOf(liveTodo?.description ?: "") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Buku") },
                navigationIcon = {
                    IconButton(onClick = onBackClicked) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Kembali"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            if (liveTodo != null) {
                FloatingActionButton(
                    onClick = {
                        if (isInEditMode) {
                            val updatedTodo = liveTodo!!.copy(
                                title = editedTitle,
                                description = editedDesc
                            )
                            bookViewModel.updateBook(updatedTodo)
                            isInEditMode = false
                        } else {
                            isInEditMode = true
                        }
                    }
                ) {
                    Icon(
                        imageVector = if (isInEditMode) Icons.Default.Save else Icons.Default.Edit,
                        contentDescription = if (isInEditMode) "Simpan" else "Edit"
                    )
                }
            }
        }
    ) { innerPadding ->
        if (liveTodo != null) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .padding(16.dp),
            ) {
                if (isInEditMode) {
                    OutlinedTextField(
                        value = editedTitle,
                        onValueChange = { editedTitle = it },
                        label = { Text("Judul Buku") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = editedDesc,
                        onValueChange = { editedDesc = it },
                        label = { Text("Deskripsi") },
                        modifier = Modifier.fillMaxWidth()
                    )
                } else {
                    Text(
                        text = liveTodo!!.title,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(text = liveTodo!!.description)
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Data ToDo tidak ditemukan.")
            }
        }
    }
}