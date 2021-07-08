package com.example.progetto_programmazionemobile.View

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.FrameLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progetto_programmazionemobile.Model.Campo
import com.example.progetto_programmazionemobile.Model.Circolo
import com.example.progetto_programmazionemobile.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarView
import kotlinx.android.synthetic.main.activity_selezione_1.*
import kotlinx.android.synthetic.main.fragment_details_clubs__campi.*
import kotlinx.android.synthetic.main.rv_campi.*
import java.util.ArrayList

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [DetailsClubs_Campi.newInstance] factory method to
 * create an instance of this fragment.
 */
class DetailsClubs_Campi : Fragment()  {


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_details_clubs__campi, container, false)

        val bundle = activity?.intent?.extras
        val courts = bundle!!.getSerializable("courts") as ArrayList<Campo>

        val recyclerView = v.findViewById<View>(R.id.recyclearCampi) as RecyclerView
        val viewAdapter = MyAdapterCourts(courts)
        recyclerView.setLayoutManager(LinearLayoutManager(activity))
        recyclerView.setAdapter(viewAdapter)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

       /*val bundle = activity?.intent?.extras
        val courts = bundle!!.getSerializable("courts") as ArrayList<Campo>

        recyclearCampi.layoutManager = LinearLayoutManager(this.context)
        recyclearCampi.adapter = MyAdapterCourts(courts)

        */

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

        val layout = LayoutInflater.from(parent.context).inflate(R.layout.rv_campi, parent, false)

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


