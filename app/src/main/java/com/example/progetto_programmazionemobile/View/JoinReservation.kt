package com.example.progetto_programmazionemobile.View

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.progetto_programmazionemobile.R
import com.example.progetto_programmazionemobile.ViewModel.Auth_Handler
import com.example.progetto_programmazionemobile.ViewModel.DB_Handler_Reservation
import kotlinx.android.synthetic.main.fragment_partecipa.*


// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Partecipa.newInstance] factory method to
 * create an instance of this fragment.
 */
class Partecipa : Fragment() {
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v =  inflater.inflate(R.layout.fragment_partecipa, container, false)
        val partecipaBtn = v.findViewById<Button>(R.id.partecipaBtn)
        partecipaBtn.setOnClickListener(object : View.OnClickListener {

            override fun onClick(v: View?) {
                var connectivityManager = requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
                val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
                if(activeNetworkInfo!=null && activeNetworkInfo.isConnected){
                    val progress: ProgressDialog = ProgressDialog(context)
                    progress.setTitle("Controllando il codice...")
                    progress.show()
                    DB_Handler_Reservation.newPartecipation(
                        Auth_Handler.CURRENT_USER!!.email,
                        codicePrenotazione.text.toString(),
                        object : DB_Handler_Reservation.MyCallBackPartecipazione {
                            override fun onCallback(result: Boolean, codErrore: Int) {

                                progress.dismiss()

                                if (result) {
                                    val builder: AlertDialog.Builder = AlertDialog.Builder(context!!)
                                    builder.setTitle("Operazione conclusa")
                                    builder.setMessage("Partecipazione aggiunta con successo")
                                    builder.setPositiveButton("OK",
                                        object : DialogInterface.OnClickListener {
                                            override fun onClick(dialog: DialogInterface?, which: Int) {
                                                var fr = getFragmentManager()?.beginTransaction()
                                                fr?.replace(
                                                    R.id.fragment_container,
                                                    InfoFragment(),
                                                    "APP"
                                                )
                                                fr?.commit()

                                            }
                                        })
                                    builder.setOnDismissListener {
                                        var fr = getFragmentManager()?.beginTransaction()
                                        fr?.replace(R.id.fragment_container, InfoFragment(), "APP")
                                        fr?.commit()
                                    }
                                    val alertDialog = builder.create()
                                    alertDialog.show()
                                } else {
                                    val builder: AlertDialog.Builder = AlertDialog.Builder(context!!)
                                    builder.setTitle("Errore")
                                    when(codErrore){
                                        1->builder.setMessage("Errore di comunicazione col il database remoto.")
                                        2->builder.setMessage("Non puoi partecipare alla tua stessa prenotazione.")
                                        3->builder.setMessage("Il codice inserito non corrisponde a nessuna prenotazione.")
                                    }

                                    builder.setPositiveButton("Riprova",
                                        object : DialogInterface.OnClickListener {
                                            override fun onClick(dialog: DialogInterface?, which: Int) {
                                            }
                                        })
                                    val alertDialog = builder.create()
                                    alertDialog.show()
                                }
                            }
                        })
                }else{
                    val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
                    builder.setTitle("Errore")
                    builder.setMessage("Assicurarsi che il dispositivo sia connesso alla rete.")

                    builder.setPositiveButton("OK",
                        object : DialogInterface.OnClickListener {
                            override fun onClick(dialog: DialogInterface?, which: Int) {

                            }
                        })
                    builder.setOnDismissListener {

                    }

                    val alertDialog = builder.create()
                    alertDialog.show()
                }


            }
        })

        return v
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Partecipa.
         */
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Partecipa().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}