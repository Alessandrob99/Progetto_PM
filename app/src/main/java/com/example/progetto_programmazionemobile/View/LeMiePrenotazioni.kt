package com.example.progetto_programmazionemobile.View

import android.app.ProgressDialog
import android.content.*
import android.content.Context.CLIPBOARD_SERVICE
import android.graphics.Paint
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.ContextCompat.startActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.progetto_programmazionemobile.Model.Prenotazione
import com.example.progetto_programmazionemobile.Model.Utente
import com.example.progetto_programmazionemobile.R
import com.example.progetto_programmazionemobile.ViewModel.Auth_Handler
import com.example.progetto_programmazionemobile.ViewModel.DB_Handler_Reservation
import com.example.progetto_programmazionemobile.ViewModel.DB_Handler_Users
import kotlinx.android.synthetic.main.fragment_le_mie_prenotazioni.*
import java.util.*
import kotlin.collections.ArrayList


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LeMiePrenotazioni.newInstance] factory method to
 * create an instance of this fragment.
 */
class LeMiePrenotazioni : Fragment() {
    // TODO: Rename and change types of parameters
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
        val v =  inflater.inflate(R.layout.fragment_le_mie_prenotazioni, container, false)



        var connectivityManager = requireActivity().getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
        if(activeNetworkInfo!=null && activeNetworkInfo.isConnected){
            val progress : ProgressDialog = ProgressDialog(context)
            progress.setTitle("Caricando le tue prenotazioni...")
            progress.show()

            DB_Handler_Users.getReservationList(Auth_Handler.CURRENT_USER!!.email,
                object : DB_Handler_Users.MyCallbackReservations {
                    override fun onCallback(reservations: ArrayList<Prenotazione>?) {
                        progress.dismiss()
                        if (reservations.isNullOrEmpty()) {
                            topText.text = "Nessuna prenotazione registrata"
                        } else {
                            val recyclerView =
                                v.findViewById<View>(R.id.recyclerViewPrenotazioni) as RecyclerView
                            val viewAdapter = MyAdapterReservations(reservations, context!!)
                            recyclerView.setLayoutManager(LinearLayoutManager(activity))
                            recyclerView.setAdapter(viewAdapter)
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



        return v
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment LeMiePrenotazioni.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LeMiePrenotazioni().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}



class MyAdapterReservations(val prenotazioni: ArrayList<Prenotazione>?, val context: Context) : RecyclerView.Adapter<MyAdapterReservations.MyViewHolderReservations>() {

    val conx = context
    val cal = Calendar.getInstance()
    val today = cal.time
    var c : Int = 0


    class MyViewHolderReservations(val row: View) : RecyclerView.ViewHolder(row) {

        val circolo = row.findViewById<TextView>(R.id.CircoloText)
        val campo = row.findViewById<TextView>(R.id.CampoTxt)
        val giorno = row.findViewById<TextView>(R.id.giornoPrenText)
        val oraInizio = row.findViewById<TextView>(R.id.oraInizioPrenText)
        val oraFine= row.findViewById<TextView>(R.id.oraFinePrenText)
        val cod_prem = row.findViewById<TextView>(R.id.codicePrenText)
        val btnElimina = row.findViewById<Button>(R.id.btnElimina)
        val copyCode = row.findViewById<ImageButton>(R.id.copyCode)
        val partecipaBtn = row.findViewById<Button>(R.id.partecipantiBtn)
        val btnExpand = row.findViewById<ImageView>(R.id.btn_Expand)
        val textViewCod = row.findViewById<TextView>(R.id.textView9)

        //Menu espandibile
        val expandableLayout = row.findViewById<LinearLayout>(R.id.expandableLayout)
        val notexpandableLayout = row.findViewById<LinearLayout>(R.id.notexpandedLayout)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolderReservations {

        val layout = LayoutInflater.from(parent.context).inflate(
            R.layout.rv_reservation,
            parent,
            false
        )

        return MyViewHolderReservations(layout)

    }


    override fun onBindViewHolder(holder: MyViewHolderReservations, position: Int) {

        //Bottone partecipanti
        holder.partecipaBtn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                DB_Handler_Reservation.getPartecipanti(prenotazioni!!.get(position).id,object : DB_Handler_Reservation.MyCallBackPartecipanti{
                    override fun onCallback(users: ArrayList<Utente>) {
                        val intent = Intent(v?.context,SearchResult::class.java)
                        intent.putExtra("usersList", users)
                        startActivity(context,intent,null)
                    }
                })
            }
        })


        //Vedo se il layout è allargato
        var isExpanded = prenotazioni!!.get(position).expanded
        if(isExpanded){
            holder.expandableLayout.visibility = View.VISIBLE
            holder.btnExpand.setImageResource(R.drawable.ic_arrow_up)
        }else{
            holder.expandableLayout.visibility = View.GONE
            holder.btnExpand.setImageResource(R.drawable.ic_arrow_down)
        }

        holder.notexpandableLayout.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                prenotazioni.get(position).expanded = !prenotazioni.get(position).expanded
                notifyDataSetChanged()
            }
        })

        holder.btnExpand.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                prenotazioni.get(position).expanded = !prenotazioni.get(position).expanded
                notifyDataSetChanged()
            }
        })

        holder.copyCode.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                val textToCopy = holder.cod_prem.text

                val clipboardManager = conx.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
                val clipData = ClipData.newPlainText("text", textToCopy)
                clipboardManager.setPrimaryClip(clipData)
                Toast.makeText(conx, "Copiato negli appunti: $textToCopy", Toast.LENGTH_LONG).show()

            }
        })

        holder.btnElimina.isClickable = false

        //Decodifica del codice
        DB_Handler_Reservation.getReservationLayoutInfo(prenotazioni!!.get(position),
            object : DB_Handler_Reservation.MyCallBackInfo {
                override fun onCallBack(
                    campo: String,
                    circolo: String,
                    oraInizio: String,
                    oraFine: String,
                    giorno: String,
                    codice: String
                ) {

                    //Scrivo sul mio layout

                    holder.giorno.text = giorno
                    holder.oraInizio.text = oraInizio
                    holder.oraFine.text = oraFine
                    holder.cod_prem.text = codice
                    holder.circolo.text = circolo
                    holder.campo.text = campo
                    if (prenotazioni.get(position).oraFine.before(today)) {

                        //Segnala che la prenotazione è scadut
                        holder.row.setBackgroundResource(R.drawable.btn_custom_red)

                        holder.copyCode.isEnabled = false

                        holder.copyCode.isVisible = false

                        holder.cod_prem.paintFlags = Paint.STRIKE_THRU_TEXT_FLAG
                    }
                    if(prenotazioni.get(position).prenotatore!= Auth_Handler.CURRENT_USER!!.email){
                        holder.partecipaBtn.isVisible = false
                        holder.copyCode.isVisible = false
                        holder.textViewCod.text = "Prenotazione effettuata da"
                        holder.cod_prem.text = prenotazioni.get(position).prenotatore
                    }
                }
            })
        holder.btnElimina.isClickable = true

        holder.btnElimina.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                val builder: AlertDialog.Builder = AlertDialog.Builder(context)
                builder.setTitle("Sei sicuro?")
                builder.setMessage("Eliminare la prenotazione dalla lista?")

                builder.setPositiveButton("Si", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {
                        val progress: ProgressDialog = ProgressDialog(context)
                        progress.setTitle("Eliminazione in corso...")
                        progress.show()

                        DB_Handler_Reservation.deleteReservation(Auth_Handler.CURRENT_USER!!.email,
                            holder.cod_prem.text.toString(),
                            object : DB_Handler_Reservation.MyCallBackNewRes {
                                override fun onCallback(result: Boolean) {

                                    progress.dismiss()

                                    if (result) {
                                        val builder: AlertDialog.Builder = AlertDialog.Builder(
                                            context
                                        )
                                        builder.setTitle("Eliminazione completata")
                                        builder.setMessage("Operazione completata con successo")

                                        builder.setPositiveButton("OK",
                                            object : DialogInterface.OnClickListener {
                                                override fun onClick(
                                                    dialog: DialogInterface?,
                                                    which: Int
                                                ) {
                                                }

                                            })
                                        builder.setOnDismissListener {
                                            deleteItem(position)
                                        }

                                        val alertDialog = builder.create()
                                        alertDialog.show()

                                    } else {
                                        val builder: AlertDialog.Builder = AlertDialog.Builder(
                                            context
                                        )
                                        builder.setTitle("Errore")
                                        builder.setMessage("Qualcosa è andato storto... Contattaci in quanto potresti inavvertitamente bloccare delle ore libere!")

                                        builder.setPositiveButton("OK",
                                            object : DialogInterface.OnClickListener {
                                                override fun onClick(
                                                    dialog: DialogInterface?,
                                                    which: Int
                                                ) {

                                                }
                                            })

                                        val alertDialog = builder.create()
                                        alertDialog.show()

                                    }
                                }
                            })
                    }
                })
                builder.setNegativeButton("NO", object : DialogInterface.OnClickListener {
                    override fun onClick(dialog: DialogInterface?, which: Int) {

                    }
                })

                val alertDialog = builder.create()
                alertDialog.show()

            }
        })






    }



    override fun getItemCount(): Int {
        if (prenotazioni!=null){
            return prenotazioni.size
        }else{
            return 0
        }
    }

    fun deleteItem(position: Int){
        prenotazioni!!.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, prenotazioni.size)
    }


}