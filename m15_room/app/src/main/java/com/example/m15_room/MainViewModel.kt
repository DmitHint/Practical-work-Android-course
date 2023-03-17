package com.example.m15_room

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainViewModel(private val wordDao: WordDao) : ViewModel() {

    val allWords = wordDao.getAll()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )


    fun onAddBtn(addedText: String) {
        viewModelScope.launch(Dispatchers.IO) {
            var word: Word? = null

            run loop@{
                allWords.value.forEach {
                    val text = it.value
                    if (text == addedText) {
                        word = it.copy(count = it.count + 1)
                        return@loop
                    }
                }
            }

            if (word != null)
                wordDao.update(word as Word)
            else {
                wordDao.insert(
                    Word(
                        value = addedText,
                        count = 1,
                    )
                )
            }
        }
    }

    fun onDeleteBtn() {
        viewModelScope.launch(Dispatchers.IO) {
            wordDao.delete()
        }
    }

}