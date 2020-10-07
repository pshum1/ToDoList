package com.example.todolist.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todolist.R
import com.example.todolist.models.Todo
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.row_recycler_todo.view.*

class AdapterToDo (private var context: Context, private var list: ArrayList<Todo>, private var keys: ArrayList<String>): RecyclerView.Adapter<AdapterToDo.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        var view = LayoutInflater.from(context).inflate(R.layout.row_recycler_todo, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        var todo = list[position]
        var key = keys[position]
        holder.bind(todo, key, position)
    }

    override fun getItemCount(): Int {
       return list.size
    }

    fun setData(l: ArrayList<Todo>, k: ArrayList<String>){
        list = l
        keys = k
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        fun bind(todo: Todo, key: String, position: Int){

            var databaseReference: DatabaseReference = FirebaseDatabase.getInstance().getReference(Todo.COLLECTION_NAME)

            itemView.tv_task_title.text = todo.title
            itemView.tv_task_description.text = todo.description

            itemView.button_delete_record.setOnClickListener {
                databaseReference.child(key).setValue(null)
                list.removeAt(position)
                keys.removeAt(position)
                notifyItemRemoved(position)
            }

        }
    }


}