package com.example.progetto_programmazionemobile.View

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progetto_programmazionemobile.Model.Utente
import com.example.progetto_programmazionemobile.R

class SearchResult : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)
        val intent = getIntent()
        val users : ArrayList<Utente> = intent.getSerializableExtra("usersList") as ArrayList<Utente>

        val rv: RecyclerView = findViewById(R.id.rv1)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = MyAdapter(users)
    }
}


class MyAdapter(val data: ArrayList<Utente>) : RecyclerView.Adapter<MyAdapter.MyViewHolder>() {
    class MyViewHolder(val row: View) : RecyclerView.ViewHolder(row) {
        val cognomeViewRV = row.findViewById<TextView>(R.id.user_rv_view_cognome)
        val nomeViewRV = row.findViewById<TextView>(R.id.user_rv_view_nome)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(
            R.layout.user_rv_layout,
            parent, false
        )
        return MyViewHolder(layout)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.cognomeViewRV.text = data.get(position).cognome.toString()
        holder.nomeViewRV.text = data.get(position).nome.toString()
    }

    override fun getItemCount(): Int = data.size
}
