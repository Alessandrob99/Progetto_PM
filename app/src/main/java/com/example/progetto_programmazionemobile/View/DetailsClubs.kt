package com.example.progetto_programmazionemobile.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import com.example.progetto_programmazionemobile.Model.Campo
import com.example.progetto_programmazionemobile.Model.Circolo
import com.example.progetto_programmazionemobile.Model.Utente
import com.example.progetto_programmazionemobile.R
import com.example.progetto_programmazionemobile.ViewModel.Auth_Handler
import com.example.progetto_programmazionemobile.ViewModel.DB_Handler_Courts
import com.google.android.material.circularreveal.coordinatorlayout.CircularRevealCoordinatorLayout
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_details_club.*
import kotlinx.android.synthetic.main.activity_selezione_1.*
import java.util.*
import kotlin.collections.ArrayList


class DetailsClubs:AppCompatActivity() {
    lateinit var autocompleteDurata: AutoCompleteTextView
    lateinit var autocompleteInizio: AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details_club)

        init()

        val nomeClub: TextView = findViewById(R.id.nomeClub)
        nomeClub.text = intent.getStringExtra("titleClub")
        val courts = intent.getSerializableExtra("courts") as ArrayList<Campo>
        val club = intent.getSerializableExtra("club") as Circolo


        val email : TextView = findViewById(R.id.txtemail_Club)
        email.text = club.email
        val telefono : TextView = findViewById(R.id.txtTelefono_club)
        telefono.text = club.telefono

        val rv: RecyclerView = findViewById(R.id.recyclearCampi)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = MyAdapterCourts(courts)


    }

    private fun init(){
        viewPagerClub.adapter = MyAdapterViewPager(this@DetailsClubs)

    }
}


class MyAdapterCourts(val courts: ArrayList<Campo>) : RecyclerView.Adapter<MyAdapterCourts.MyViewHolderCourts>() {


    class MyViewHolderCourts(val row: View) : RecyclerView.ViewHolder(row) {
        val riscaldamentoCheck = row.findViewById<CheckBox>(R.id.riscaldamentoCheck)
        val copertoCheck = row.findViewById<CheckBox>(R.id.copertoCheck)
        val prezzoText = row.findViewById<TextView>(R.id.prezzo)
        val n_campoText = row.findViewById<TextView>(R.id.numeroCampo)
        val superficieText = row.findViewById<TextView>(R.id.superficie)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderCourts {

        val layout = LayoutInflater.from(parent.context).inflate(
            R.layout.rv_campi,
            parent, false)

        return MyViewHolderCourts(layout)

    }


    override fun onBindViewHolder(holder: MyAdapterCourts.MyViewHolderCourts, position: Int) {

        holder.superficieText.text = courts.get(position).superficie.capitalize()
        holder.prezzoText.text = courts.get(position).prezzo_ora.toString()
        holder.n_campoText.text = courts.get(position).n_c.toString()
        holder.copertoCheck.isChecked = courts.get(position).coperto
        holder.riscaldamentoCheck.isChecked = courts.get(position).riscaldamento

    }

    override fun getItemCount(): Int = courts.size
}















/*
class MyAdapter(val courts: DB_Handler_Courts) : RecyclerView.Adapter<MyAdapter.MyViewHolderUser>() {


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

/*
        val OraInizio: EditText = findViewById(R.id.oraInizio)
        val arrayOrario = arrayOf(

            "0:30",
            "1:00",
            "1:30",
            "2:00",
            "2:30",
            "3:00",
            "3:30",
            "4:00"
        )

        val arrayOrarioFine = arrayOf(

            "8:30",
            "1:00",
            "1:30",
            "2:00",
            "2:30",
            "3:00",
            "3:30",
            "4:00"
        )

        autocompleteDurata = findViewById(R.id.durataOra) as AutoCompleteTextView
        val adapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayOrario)
        autocompleteDurata.setText(adapter.getItem(0).toString(), false)
        autocompleteDurata.setAdapter(adapter)
        autocompleteDurata.setOnItemClickListener(this);

        autocompleteInizio = findViewById(R.id.oraInizio) as AutoCompleteTextView
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayOrarioFine)
        autocompleteInizio.setText(adapter.getItem(0).toString(), false)
        autocompleteInizio.setAdapter(adapter2)
        autocompleteInizio.setOnItemClickListener(this);

        autocompleteInizio.onItemClickListener = AdapterView.OnItemClickListener{
                parent,view,position,id->
            // fetch the user selected value
            val item = parent.getItemAtPosition(position).toString()
            somma()
            // set user selected value to the TextView
            provaaaa2.setText(item)
        }


    }

    override fun onItemClick(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
        // fetch the user selected value
        val item = parent.getItemAtPosition(position).toString()
        somma()
        // set user selected value to the TextView
        provaaaa.setText(item)
    }

    private fun somma() {
        val fine :TextView = findViewById(R.id.fine)

        val splitDurata: List<String> = durataOra.text.split(":")
        val ora = splitDurata[0]
        val minuti = splitDurata[1]

        val splitInizio: List<String> = oraInizio.text.split(":")
        val oraInzio = splitInizio[0]
        val minutiInizio = splitInizio[1]

        var oraFine : Int = oraInzio.toInt() + ora.toInt()
        var minutiFine:Int = minuti.toInt() + minutiInizio.toInt()
        if(minutiFine == 60)
        {
            minutiFine = 0
            oraFine = oraFine+1
            fine.text = "$oraFine:$minutiFine"+"0"
        }
        else
        {
            if(minutiFine == 0)
            {

            }
            fine.text = "$oraFine:$minutiFine"
        }


    }
}

 */




*/
