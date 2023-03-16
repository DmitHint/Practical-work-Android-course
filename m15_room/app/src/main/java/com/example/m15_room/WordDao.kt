package com.example.m15_room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface WordDao {

    @Query("SELECT * FROM word")
    fun getAll(): Flow<List<Word>>

    @Insert(entity = Word::class)
    suspend fun insert(word: Word)

    @Query("DELETE FROM word")
    suspend fun delete()

    @Update
    suspend fun update(word: Word)
}