package com.example.progetto_programmazionemobile.View

import android.app.Activity
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.Nullable
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.example.progetto_programmazionemobile.R

class RicercaCircoli : Fragment()
{
    //lateinit var spinnerRegione : Spinner
    //lateinit var spinnerCircoli : Spinner
    lateinit var autocomplete : AutoCompleteTextView
    lateinit var autocompleteCircoli: AutoCompleteTextView

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    @Nullable
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View?
    {
        val v =  inflater.inflate(R.layout.fragment_ricerca_circoli, container, false)
        val array = arrayOf("","Bella", "fra", "ciao")
        val array2 = arrayOf("","zio", "bellino")

        //spinnerRegione = v.findViewById(R.id.spinnerRegione) as Spinner
        //spinnerCircoli = v.findViewById(R.id.spinnerCircoli) as Spinner
        autocomplete = v.findViewById(R.id.autoCompleteRegione) as AutoCompleteTextView
        autocompleteCircoli = v.findViewById(R.id.autoCompleteCircolo) as AutoCompleteTextView

        val adapter = ArrayAdapter(activity, android.R.layout.simple_list_item_1, array)
        val adapter2 = ArrayAdapter(activity, android.R.layout.simple_list_item_1, array2)

        autocompleteCircoli.setText(adapter2.getItem(0).toString(), false)
        autocomplete.setText(adapter.getItem(0).toString(),false)
        autocompleteCircoli.setAdapter((adapter2))
        autocomplete.setAdapter(adapter)


        autocomplete.onItemSelectedListener = object  : AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener{
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(context, "Prova", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

        autocompleteCircoli.onItemSelectedListener = object : AdapterView.OnItemClickListener,  AdapterView.OnItemSelectedListener {

            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("Not yet implemented")
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(context, "Prova", Toast.LENGTH_SHORT).show()
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }


        return v
    }

}