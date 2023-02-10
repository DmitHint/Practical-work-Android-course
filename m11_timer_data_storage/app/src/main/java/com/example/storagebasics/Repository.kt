package com.example.storagebasics

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.contentValuesOf


private const val PREFERENCE_NAME = "prefs"
private const val EDIT_TEXT_STR = "editTextStr"

class Repository(context: Context) {
    private var prefs: SharedPreferences =
        context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
    var localVariable: String? = "local variable"

    fun getText(): String =
        when {
            getDataFromSharedPreference() != null ->
                getDataFromSharedPreference()!!
            getDataFromLocalVariable() != null -> getDataFromLocalVariable()!!
            else -> ""
        }

    private fun getDataFromSharedPreference(): String? =
        prefs.getString(EDIT_TEXT_STR, null)

    private fun getDataFromLocalVariable(): String? = localVariable

    fun saveText(text: String) {
        prefs.edit().putString(EDIT_TEXT_STR, text).apply()
        localVariable = text
    }

    fun clearText() {
        localVariable = null
        prefs.edit().clear().apply()
    }
}