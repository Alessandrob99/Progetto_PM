package com.example.progetto_programmazionemobile.View

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.progetto_programmazionemobile.Model.User
import com.example.progetto_programmazionemobile.R
import com.google.firebase.storage.FirebaseStorage
import de.hdodenhof.circleimageview.CircleImageView

class SearchResult : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        val broadcastReceiver1: BroadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(arg0: Context?, intent: Intent) {
                val action = intent.action
                if (action == "logout") {
                    finish()
                    // DO WHATEVER YOU WANT.
                }
            }
        }
        registerReceiver(broadcastReceiver1, IntentFilter("logout"))




        val intent = getIntent()
        val users : ArrayList<User> = intent.getSerializableExtra("usersList") as ArrayList<User>

        val rv: RecyclerView = findViewById(R.id.rv1)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = MyAdapter(users,this)
    }
}


class MyAdapter(val users: ArrayList<User>, val context : Context) : RecyclerView.Adapter<MyAdapter.MyViewHolderUser>() {


    class MyViewHolderUser(val row: View) : RecyclerView.ViewHolder(row) {
        val cognomeViewRV = row.findViewById<TextView>(R.id.user_rv_view_nome)
        val nomeViewRV = row.findViewById<TextView>(R.id.user_rv_view_cognome)
        val emailViewRV = row.findViewById<TextView>(R.id.user_rv_view_email)
        val profileImg = row.findViewById<CircleImageView>(R.id.profileImg)
        val txtTitle = row.findViewById<TextView>(R.id.txtParetecipanti)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderUser {

                val layout = LayoutInflater.from(parent.context).inflate(
                    R.layout.user_rv_layout,
                    parent, false
                )

                return MyViewHolderUser(layout)

    }


    override fun onBindViewHolder(holder: MyAdapter.MyViewHolderUser, position: Int) {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        val picRef = storageRef.child("usersPics/"+ users.get(position).email)


        holder.cognomeViewRV.text = users.get(position).cognome.capitalize()
        holder.nomeViewRV.text = users.get(position).nome.capitalize()
        holder.emailViewRV.text = users.get(position).email

        picRef.downloadUrl.addOnSuccessListener{
            Glide.with(context).load(it).into(holder.profileImg)
        }

    }

    override fun getItemCount(): Int = users.size
}


