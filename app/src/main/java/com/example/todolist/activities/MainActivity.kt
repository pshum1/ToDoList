package com.example.todolist.activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todolist.R
import com.example.todolist.adapters.AdapterToDo
import com.example.todolist.models.Todo
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var databaseReference: DatabaseReference
    lateinit var  auth: FirebaseAuth

    var mList: ArrayList<Todo> = ArrayList()
    var keyList: ArrayList<String> = ArrayList()

    var adapterTodo: AdapterToDo? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        databaseReference = FirebaseDatabase.getInstance().getReference(Todo.COLLECTION_NAME)
        auth = FirebaseAuth.getInstance()
        var user = auth.uid

        init()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.button_logout -> dialogueLogout()
        }
        return true
    }

    private fun init() {
        getData()

        adapterTodo = AdapterToDo(this, mList, keyList)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapterTodo


        button_add_floating.setOnClickListener {
            startActivity(Intent(this, AddNewActivity::class.java))
        }

    }

    private fun getData() {
        databaseReference.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                mList.clear()
                keyList.clear()
                for (data in dataSnapshot.children) {
                    var todo = data.getValue(Todo::class.java)
                    var key = data.key

                    mList.add(todo!!)
                    keyList.add(key!!)
                }
                adapterTodo?.setData(mList, keyList)
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })
    }

    private fun logout() {
        auth.signOut()
        startActivity(Intent(this, LoginActivity::class.java))
        finish()
    }

    private fun dialogueLogout() {
        var builder = AlertDialog.Builder(this)
        builder.setTitle("Logout")
        builder.setMessage("Are you sure you want to logout?")
        builder.setPositiveButton("Yes", object : DialogInterface.OnClickListener {
            override fun onClick(p0: DialogInterface?, p1: Int) {
                logout()
            }

        })
        builder.setNegativeButton(
            "No"
//            object: DialogInterface.OnClickListener{
//                override fun onClick(dialogue: DialogInterface?, p1: Int) {
//                    dialogue?.dismiss()
//                }
        )
        { dialogue, p1 -> dialogue?.dismiss() }
        var alertDialogue = builder.create()
        alertDialogue.show()

    }
}