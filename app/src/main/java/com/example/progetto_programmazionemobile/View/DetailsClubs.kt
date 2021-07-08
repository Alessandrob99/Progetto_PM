package com.example.progetto_programmazionemobile.View

import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.progetto_programmazionemobile.Model.Campo
import com.example.progetto_programmazionemobile.Model.Circolo
import com.example.progetto_programmazionemobile.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import com.google.android.material.navigation.NavigationView
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_details_club.*
import kotlinx.android.synthetic.main.activity_details_clubs.*
import kotlinx.android.synthetic.main.activity_selezione_1.*
import java.util.*


class DetailsClubs:AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    private val mBtmView: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val club = intent.getSerializableExtra("club") as Circolo
        val courts = intent.getSerializableExtra("courts") as ArrayList<Campo>
        setContentView(R.layout.activity_details_club)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigationView.setOnNavigationItemSelectedListener { item: MenuItem? ->  // using lamda
            myClickItem(item) //call here
            true
        }

        val nomeClub: TextView = findViewById(R.id.nomeClub)
        nomeClub.text = intent.getStringExtra("titleClub")


        /*val email : TextView = findViewById(R.id.txtemail_Club)
        email.text = "Email: "+club.email
        val telefono : TextView = findViewById(R.id.txtTelefono_Club)
        telefono.text = "Telefono: "+club.telefono
        val rv: RecyclerView = findViewById(R.id.recyclearCampi)
        rv.layoutManager = LinearLayoutManager(this)
        rv.adapter = MyAdapterCourts(courts)

         */






    }

    private fun myClickItem(item: MenuItem?) {
        when (item!!.itemId) {
            R.id.campi_Disponibili -> {ChangeFragment(DetailsClubs_Campi(),"INFO")
            }
            R.id.infoClub -> {ChangeFragment(DetailsClubs_Info(),"PROFILO")
            }
        }
    }


    fun ChangeFragment(frag: Fragment, ID: String)
    {
        val fragment = supportFragmentManager.beginTransaction()
        fragment.replace(R.id.fragment_details, frag, ID).commit()
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when(item.itemId)
        {
            R.id.campi_Disponibili ->{
                ChangeFragment(DetailsClubs_Campi(),"INFO")
            }
            R.id.infoClub ->{
                ChangeFragment(DetailsClubs_Info(),"PROFILO")
            }

        }
        return true
    }
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
