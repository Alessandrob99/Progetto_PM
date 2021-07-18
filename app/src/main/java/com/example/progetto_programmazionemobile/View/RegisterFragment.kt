package com.example.progetto_programmazionemobile.View

import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.progetto_programmazionemobile.R
import com.example.progetto_programmazionemobile.ViewModel.Auth_Handler
import com.example.progetto_programmazionemobile.ViewModel.DB_Handler_Users
import kotlinx.android.synthetic.main.fragment_register.*
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
class RegisterFragment : Fragment(){
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




        // Inflate the layout for this fragment
        val goToHomePage = Intent(v.context,HomePage_Activity::class.java)

        val nomeText = v.findViewById<EditText>(R.id.nomeInputReg)
        val cognomeText = v.findViewById<EditText>(R.id.cognomeInputReg)
        val passwordText = v.findViewById<EditText>(R.id.passwordInputReg)
        val confermaPassword = v.findViewById<EditText>(R.id.confermaPassword)
        val emailText = v.findViewById<EditText>(R.id.emailInputReg)
        val telefonoText = v.findViewById<EditText>(R.id.telefonoInputReg)

        val confermabtn : Button = v.findViewById(R.id.confermaReg)


        confermabtn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {

                val progress : ProgressDialog = ProgressDialog(context)
                val emailSafe = emailText.text.replace("\\s".toRegex(), "")
                //Metodo che controlla la validita delle credenziali e la password


                //-------
                if (confermaPassword.text.toString() != passwordText.text.toString()) {
                    Toast.makeText(context, "Le password non coincidono", Toast.LENGTH_SHORT).show()
                } else {
                    if(passwordText.text.length<6){
                        Toast.makeText(context, "Password troppo corta (6 caratteri minimo)", Toast.LENGTH_SHORT).show()
                    }else{

                        if ((nomeText.text.toString() == "") || (cognomeText.text.toString() == "") || (passwordText.text.toString() == "") || (emailSafe == "") || (telefonoText.text.toString() == "")) {
                            Toast.makeText(context, "Inserire tutti i campi", Toast.LENGTH_SHORT).show()
                        } else {
                            if (telefonoText.text.length != 10) {
                                Toast.makeText(
                                    context,
                                    "Numero di telefono non valido",
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {

                                if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailSafe.toString())
                                        .matches()
                                ) {

                                    Toast.makeText(
                                        context,
                                        "Inserire una mail valida",
                                        Toast.LENGTH_SHORT
                                    ).show()


                                } else {
                                    //-----------------------Conferma mail da implementare-------------------------//
                                    //Disabilito il pulsante di richiesta registrazione
                                    progress.setTitle("Registrazione in corso...")
                                    progress.show()
                                    confermabtn.isEnabled = false

                                    //Controlli per credenziali già esistenti
                                    Auth_Handler.FireBaseRegistration(
                                        emailSafe,
                                        passwordText.text.toString(),
                                        nomeText.text.toString(),
                                        cognomeText.text.toString(),
                                        telefonoText.text.toString(),
                                        object : Auth_Handler.Companion.MyCallBackResult {
                                            override fun onCallBack(result: Boolean, message: String) {
                                                progress.dismiss()
                                                confermabtn.isEnabled = true
                                                val builder: AlertDialog.Builder = AlertDialog.Builder(context!!)
                                                builder.setMessage(message)
                                                builder.setPositiveButton("OK",
                                                    object : DialogInterface.OnClickListener {
                                                        override fun onClick(
                                                            dialog: DialogInterface?,
                                                            which: Int
                                                        ) {
                                                            if (result) {
                                                                val intent = Intent(context, MainActivity::class.java)
                                                                startActivity(intent)
                                                            }
                                                        }
                                                    })
                                                builder.setOnDismissListener {
                                                    if (result) {
                                                        val intent =
                                                            Intent(context, MainActivity::class.java)
                                                        startActivity(intent)
                                                    }
                                                }
                                                val alertDialog = builder.create()
                                                alertDialog.show()
                                            }

                                        }
                                    )

                                }
                            }
                        }
                }
                //_---------
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