package com.example.todolist.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.todolist.R
import com.example.todolist.models.Todo
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_add_new.*

class AddNewActivity : AppCompatActivity() {

    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new)

        databaseReference = FirebaseDatabase.getInstance().getReference(Todo.COLLECTION_NAME)

        init()
    }

    private fun init() {
        button_add.setOnClickListener {
            var title = et_title.text.toString()
            var description = et_description.text.toString()

            var todo = Todo(title, description)

            var toDoId = databaseReference.push().key

            databaseReference.child(toDoId!!).setValue(todo)
            Toast.makeText(applicationContext, "Inserted", Toast.LENGTH_SHORT).show()
            finish()
        }
    }
}