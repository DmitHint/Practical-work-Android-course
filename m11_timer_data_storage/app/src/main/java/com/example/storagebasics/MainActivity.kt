package com.example.storagebasics

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

class MainActivity : AppCompatActivity() {
    private val repository = Repository()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val buttonSave = findViewById<Button>(R.id.button_save)
        val buttonClear = findViewById<Button>(R.id.button_clear)
        val editText = findViewById<EditText>(R.id.edit_text)
        val textField = findViewById<TextView>(R.id.text_field)


        buttonSave.setOnClickListener {
            repository.saveText(editText.text.toString(), context = applicationContext)
            textField.text = repository.getText(applicationContext)
        }
        buttonClear.setOnClickListener {
            repository.clearText()
            textField.text = repository.getText(applicationContext)
        }
    }
}