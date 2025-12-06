package com.afsar.bukurak.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Save
import androidx.compose.material3.*
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.afsar.bukurak.data.Book
import com.afsar.bukurak.ui.viewmodel.BookViewModel
import com.afsar.bukurak.utility.BookApplication
import com.afsar.bukurak.utility.ViewModelFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(onBackClicked: () -> Unit,
                 book: Book?) {
    val context = LocalContext.current
    val bookViewModel: BookViewModel = viewModel(
        factory = ViewModelFactory((context.applicationContext as BookApplication).repository)
    )

    val liveBook by bookViewModel.getBookById(book?.id ?: -1L)
        .collectAsState(initial = book)

    var isInEditMode by remember { mutableStateOf(false) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    var editedTitle by remember(liveBook) { mutableStateOf(liveBook?.title ?: "") }
    var editedDesc by remember(liveBook) { mutableStateOf(liveBook?.description ?: "") }
    var editedAuthor by remember(liveBook) { mutableStateOf(liveBook?.author ?: "") }
    var editedPageCount by remember(liveBook) { mutableStateOf(liveBook?.pageCount?.toString() ?: "") }
    var editedYear by remember(liveBook) { mutableStateOf(liveBook?.publishedYear ?: 0) }
    var yearDisplay by remember(liveBook) { mutableStateOf(liveBook?.publishedYear?.toString() ?: "") }
    var showDatePicker by remember { mutableStateOf(false) }

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
                },
                actions = {
                    if (liveBook != null && !isInEditMode) {
                        IconButton(onClick = { showDeleteDialog = true }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Hapus",
                                tint = MaterialTheme.colorScheme.error
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            if (liveBook != null) {
                FloatingActionButton(
                    onClick = {
                        if (isInEditMode) {
                            val updatedBook = liveBook!!.copy(
                                title = editedTitle,
                                description = editedDesc,
                                author = editedAuthor,
                                publishedYear = editedYear,
                                pageCount = editedPageCount.toIntOrNull() ?: 0
                            )
                            bookViewModel.updateBook(updatedBook)
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
        if (liveBook != null) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(innerPadding)
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()),
            ) {
                if (isInEditMode) {
                    // Edit mode
                    OutlinedTextField(
                        value = editedTitle,
                        onValueChange = { editedTitle = it },
                        label = { Text("Judul Buku") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = editedDesc,
                        onValueChange = { editedDesc = it },
                        label = { Text("Deskripsi") },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = editedAuthor,
                        onValueChange = { editedAuthor = it },
                        label = { Text("Penulis") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    OutlinedTextField(
                        value = editedPageCount,
                        onValueChange = { editedPageCount = it.filter { char -> char.isDigit() } },
                        label = { Text("Jumlah Halaman") },
                        modifier = Modifier.fillMaxWidth(),
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    // Year picker
                    Box(modifier = Modifier.fillMaxWidth()) {
                        OutlinedTextField(
                            value = yearDisplay,
                            onValueChange = { },
                            label = { Text("Tahun Terbit") },
                            readOnly = true,
                            trailingIcon = {
                                Icon(Icons.Default.DateRange, contentDescription = "Pilih Tahun")
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                        Box(
                            modifier = Modifier
                                .matchParentSize()
                                .clickable { showDatePicker = true }
                        )
                    }

                    if (showDatePicker) {
                        DatePickerModal(
                            onDateSelected = { millis ->
                                if (millis != null) {
                                    val formatter = SimpleDateFormat("yyyy", Locale.getDefault())
                                    val yearString = formatter.format(Date(millis))
                                    editedYear = yearString.toIntOrNull() ?: 0
                                    yearDisplay = yearString
                                }
                                showDatePicker = false
                            },
                            onDismiss = {
                                showDatePicker = false
                            }
                        )
                    }
                } else {
                    // View mode
                    Text(
                        text = liveBook!!.title,
                        fontSize = 28.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Deskripsi",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = liveBook!!.description,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Text(
                        text = "Penulis",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = liveBook!!.author,
                        fontSize = 16.sp,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(24.dp)
                    ) {
                        Column {
                            Text(
                                text = "Tahun Terbit",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.secondary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${liveBook!!.publishedYear}",
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        Column {
                            Text(
                                text = "Jumlah Halaman",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.SemiBold,
                                color = MaterialTheme.colorScheme.secondary
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${liveBook!!.pageCount} halaman",
                                fontSize = 16.sp,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }
        } else {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Data Buku tidak ditemukan.")
            }
        }
    }

    // Delete confirmation dialog
    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("Hapus Buku") },
            text = { Text("Apakah Anda yakin ingin menghapus \"${liveBook?.title}\"?") },
            confirmButton = {
                TextButton(
                    onClick = {
                        liveBook?.let { bookViewModel.deleteBook(it) }
                        showDeleteDialog = false
                        onBackClicked()
                    },
                    colors = ButtonDefaults.textButtonColors(
                        contentColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Text("Hapus")
                }
            },
            dismissButton = {
                TextButton(onClick = { showDeleteDialog = false }) {
                    Text("Batal")
                }
            }
        )
    }
}