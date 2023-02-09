package com.example.storagebasics

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log


private const val PREFERENCE_NAME = "prefs"
private const val EDIT_TEXT_STR = "editTextStr"

class Repository {
    private lateinit var prefs: SharedPreferences
    var localVariable: String? = "local variable"

    fun getText(context: Context): String {
        return when {
            getDataFromSharedPreference(context) != null ->
                getDataFromSharedPreference(context)!!
            getDataFromLocalVariable() != null -> getDataFromLocalVariable()!!
            else -> ""
        }
    }

    private fun getDataFromSharedPreference(context: Context): String? {
        prefs = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
        Log.d("GET", prefs.getString(EDIT_TEXT_STR, null).toString())
        return prefs.getString(EDIT_TEXT_STR, null)
    }

    private fun getDataFromLocalVariable(): String? {
        return localVariable
    }

    fun saveText(text: String, context: Context) {
        prefs = context.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putString(text, EDIT_TEXT_STR).apply()
        localVariable = text
    }

    fun clearText() {
        localVariable = null
        if (this::prefs.isInitialized)
            prefs.edit().clear().apply()
    }
}