package com.example.progetto_programmazionemobile.View

import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.progetto_programmazionemobile.R
import com.example.progetto_programmazionemobile.ViewModel.Auth_Handler
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


class LoginFragment : Fragment() {
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


    /**
     *
     * Fragment Accedi con controllo delle credenziali dell'utente + shared preferences
     */



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v : View = inflater.inflate(R.layout.fragment_login, container, false)

        val confermabtn : Button = v.findViewById(R.id.confermaLog)
        val flagRicordami = v.findViewById<CheckBox>(R.id.ricordami)
        val emailEditText = v.findViewById<EditText>(R.id.txtEmail)
        val passWordEditText = v.findViewById<EditText>(R.id.txtPassword)
        val link = v.findViewById<TextView>(R.id.link)
        val linkCircoli = v.findViewById<TextView>(R.id.linkCircoli)

        /**
         * Controllo se ci sono valori user e pass salvati nelle shared pref'
         */

        var sharedPreferences : SharedPreferences? = activity?.getSharedPreferences(
            "remember",
            Context.MODE_PRIVATE
        )
        if (sharedPreferences != null) {
            if(sharedPreferences.getBoolean("remember", false)){
                var savedUser = sharedPreferences?.getString("email", "")
                var savedPass = sharedPreferences?.getString("password", "")

                //Preimposto i campi email e password
                flagRicordami.isChecked = true
                emailEditText.setText(savedUser)
                passWordEditText.setText(savedPass)

            }

        }

        /**
         * Link accesso form di registrazione
         * per i circoli
         */

        val PLACE_PICKER_REQUEST : Int = 1
        linkCircoli.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?){
                val intent = Intent(context, FormCircoli::class.java)
                startActivity(intent)
            }
        })






        /**
         * Link recupero password gestito da Firebase
         */
        link.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                var emailAddress: String

                val builder = AlertDialog.Builder(context!!)
                val popUp = inflater.inflate(R.layout.popup_mail, null)
                val emailText = popUp.findViewById<TextInputEditText>(R.id.email)
                val btnInvia = popUp.findViewById<Button>(R.id.inviaEmail)

                builder.setView(popUp)
                val alertDialogPopUp = builder.create()

                btnInvia.setOnClickListener(object : View.OnClickListener {
                    override fun onClick(v: View?) {
                        val builder1: AlertDialog.Builder = AlertDialog.Builder(context!!)
                        emailAddress = emailText.text.toString()
                        emailAddress = emailAddress.replace(" ","")
                        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches()) {
                            Toast.makeText(context, "Inserire una mail valida", Toast.LENGTH_SHORT)
                                .show()
                        } else {
                            Firebase.auth.sendPasswordResetEmail(emailAddress)
                                .addOnCompleteListener { task ->
                                    if (task.isSuccessful) {
                                        builder1.setTitle("E-mail inviata")
                                        builder1.setMessage(
                                            "Abbiamo inviato una mail all'indirizzo :" + System.getProperty(
                                                "line.separator"
                                            ) + emailAddress + System.getProperty("line.separator") + "Nella mail è presente un link che vi permetterà di impostare una nuova passoword di accesso." + System.getProperty(
                                                "line.separator"
                                            ) + "(La mail potrebbe essere finita nello Spam!)"
                                        )
                                        builder1.setPositiveButton("OK",
                                            object : DialogInterface.OnClickListener {
                                                override fun onClick(
                                                    dialog: DialogInterface?,
                                                    which: Int
                                                ) {
                                                    alertDialogPopUp.dismiss()
                                                }
                                            })
                                        builder1.setOnDismissListener {
                                            alertDialogPopUp.dismiss()
                                        }
                                        val alertDialog = builder1.create()
                                        alertDialog.show()
                                    }
                                }.addOnFailureListener {
                                    builder1.setTitle("Errore!")

                                    when(it.message){
                                        "There is no user record corresponding to this identifier. The user may have been deleted."->
                                            builder1.setMessage("L'e-mail inserita non risulta legata a nessun account registrato.")
                                        "A network error (such as timeout, interrupted connection or unreachable host) has occurred."->
                                            builder1.setMessage("Assicurarsi che il dispositivo sia connesso alla rete")
                                        else ->builder1.setMessage("Qualcosa è andato storto nella procedura di invio della mail"+ System.getProperty("line.separator") +"Assicurati che il dispositivo sia connesso alla rete e che la mail sia ben scritta.")
                                    }


                                    builder1.setPositiveButton("OK",
                                        object : DialogInterface.OnClickListener {
                                            override fun onClick(
                                                dialog: DialogInterface?,
                                                which: Int
                                            ) {
                                                alertDialogPopUp.dismiss()
                                            }
                                        })
                                    builder1.setOnDismissListener {
                                        alertDialogPopUp.dismiss()
                                    }
                                    val alertDialog = builder1.create()
                                    alertDialog.show()

                                }
                        }
                    }
                })
                alertDialogPopUp.show()
            }
        })

        flagRicordami.setOnCheckedChangeListener { buttonView, isChecked ->

            if(flagRicordami.isChecked==false){
                var sharedPreferences : SharedPreferences? = activity?.getSharedPreferences(
                    "remember",
                    Context.MODE_PRIVATE
                )
                var editor : SharedPreferences.Editor? = sharedPreferences?.edit()
                if (editor != null) {
                    editor.putString("email", "")
                    editor.putString("password", "")
                    editor.putBoolean("remember", false)
                    editor.apply()
                }

            }

        }


        /**
         * Metodo che controlla la validita delle credenziali
         */
        confermabtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {

                val email = emailEditText.text.toString().replace(" ", "")
                val password = passWordEditText.text.toString()
                val progress: ProgressDialog = ProgressDialog(context)
                progress.setTitle("Controllando le credenziali...")
                progress.show()
                confermabtn.isEnabled = false

                //Controllo se sono vuote
                if (((TextUtils.equals(email, "")) || ((TextUtils.equals(password, ""))))) {
                    progress.dismiss()

                    val builder: AlertDialog.Builder = AlertDialog.Builder(context!!)
                    builder.setTitle("Errore")
                    builder.setMessage("Fornire sia email che password")
                    builder.setPositiveButton("OK", object : DialogInterface.OnClickListener {
                        override fun onClick(dialog: DialogInterface?, which: Int) {

                        }
                    })
                    val alertDialog = builder.create()
                    alertDialog.show()

                    confermabtn.isEnabled = true
                } else {
                    Auth_Handler.FireBaseLogin(
                        flagRicordami.isChecked,
                        context!!,
                        email,
                        password,
                        object : Auth_Handler.Companion.MyCallBackResult {
                            override fun onCallBack(result: Boolean, message: String) {
                                progress.dismiss()
                                confermabtn.isEnabled = true
                                if (result) {

                                    val intent = Intent(context, HomePage_Activity::class.java)
                                    startActivity(intent)
                                } else {
                                    val builder: AlertDialog.Builder =
                                        AlertDialog.Builder(context!!)
                                    builder.setTitle("Errore")
                                    builder.setMessage(message)
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
         * @return A new instance of fragment LoginFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
