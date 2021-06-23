package com.example.progetto_programmazionemobile.View

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.progetto_programmazionemobile.R
import com.example.progetto_programmazionemobile.ViewModel.DB_Handler
import java.util.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [RegisterFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RegisterFragment : Fragment() {
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
        val v : View =  inflater.inflate(R.layout.fragment_register, container, false)


        val datePicker = v.findViewById<DatePicker>(R.id.datePicker)
        val today = Calendar.getInstance()
        datePicker.init(today.get(Calendar.YEAR), today.get(Calendar.MONTH),
            today.get(Calendar.DAY_OF_MONTH)

        ) { view, year, month, day ->


        }

        // Inflate the layout for this fragment
        val goToHomePage = Intent(v.context,HomePage_Activity::class.java)

        val userText = v.findViewById<EditText>(R.id.nomeutenteInputReg)
        val nomeText = v.findViewById<EditText>(R.id.nomeInputReg)
        val cognomeText = v.findViewById<EditText>(R.id.cognomeInputReg)
        val passwordText = v.findViewById<EditText>(R.id.passwordInputReg)
        val emailText = v.findViewById<EditText>(R.id.emailInputReg)
        val telefonoText = v.findViewById<EditText>(R.id.telefonoInputReg)

        val confermabtn : Button = v.findViewById(R.id.confermaReg)

        confermabtn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                //Metodo che controlla la validita delle credenziali


                if((nomeText.text.toString()=="")||(userText.text.toString()=="")||(cognomeText.text.toString()=="")||(passwordText.text.toString()=="")||(emailText.text.toString()=="")||(telefonoText.text.toString()=="")){
                    Toast.makeText(context,"Inserire tutti i campi",Toast.LENGTH_SHORT).show()
                }else{
                        if(android.util.Patterns.EMAIL_ADDRESS.matcher(emailText.text.toString()).matches()) {


                            //-----------------------Conferma mail da implementare-------------------------//



                            //Parsing data di nascita
                            val cal = Calendar.getInstance()
                            cal[Calendar.YEAR] = datePicker.year
                            cal[Calendar.MONTH] = datePicker.month
                            cal[Calendar.DAY_OF_MONTH] = datePicker.dayOfMonth
                            val dataNascita = cal.time

                            //Metodo per la memeorizzaione dei dati

                            DB_Handler.newUser(userText.text.toString(),
                                passwordText.text.toString(),
                                nomeText.text.toString(),
                                cognomeText.text.toString(),
                                emailText.text.toString(),
                                telefonoText.text.toString(),
                                dataNascita)


                            val intent = Intent(context, registrationCompletedPopUp::class.java)
                            startActivity(intent)

                        }else{
                            Toast.makeText(context,"Inserire una mail valida",Toast.LENGTH_SHORT).show()
                        }
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
         * @return A new instance of fragment RegisterFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegisterFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }


}