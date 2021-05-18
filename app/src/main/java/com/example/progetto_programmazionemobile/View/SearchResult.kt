package com.example.progetto_programmazionemobile.View

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progetto_programmazionemobile.Model.Circolo
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


class MyAdapter(val users: ArrayList<Utente>) : RecyclerView.Adapter<MyAdapter.MyViewHolderUser>() {


    class MyViewHolderUser(val row: View) : RecyclerView.ViewHolder(row) {
        val cognomeViewRV = row.findViewById<TextView>(R.id.user_rv_view_nome)
        val nomeViewRV = row.findViewById<TextView>(R.id.user_rv_view_cognome)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderUser {

                val layout = LayoutInflater.from(parent.context).inflate(
                        R.layout.user_rv_layout,
                        parent, false)
                return MyViewHolderUser(layout)

    }


    override fun onBindViewHolder(holder: MyAdapter.MyViewHolderUser, position: Int) {

                holder.cognomeViewRV.text = users.get(position).cognome.toString()
                holder.nomeViewRV.text = users.get(position).nome.toString()



    }

    override fun getItemCount(): Int = users.size
}


