package com.afsar.bukurak.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.afsar.bukurak.data.Book
import kotlinx.coroutines.flow.Flow

@Dao
interface BookDao {


    @Query("SELECT * FROM book_table ORDER BY id DESC")
    fun getAllTodos(): Flow<List<Book>>


    @Query("SELECT * FROM book_table WHERE id = :id")
    fun getTodoById(id: Long): Flow<Book?>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(todo: Book)

    @Update
    suspend fun update(todo: Book)

    @Delete
    suspend fun delete(todo: Book)
}