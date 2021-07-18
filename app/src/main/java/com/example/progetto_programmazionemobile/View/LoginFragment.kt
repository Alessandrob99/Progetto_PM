package com.example.progetto_programmazionemobile.View

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.progetto_programmazionemobile.R
import com.example.progetto_programmazionemobile.ViewModel.Auth_Handler


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [LoginFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
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




    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val v : View = inflater.inflate(R.layout.fragment_login, container, false)
        val goToHomePage = Intent(v.context, HomePage_Activity::class.java)

        val confermabtn : Button = v.findViewById(R.id.confermaLog)
        val flagRicordami = v.findViewById<CheckBox>(R.id.ricordami)
        val emailEditText = v.findViewById<EditText>(R.id.txtEmail)
        val passWordEditText = v.findViewById<EditText>(R.id.txtPassword)

        //Controllo se ci sono valori user e pass salvati nelle shared pref'
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




        confermabtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //Metodo che controlla la validita delle credenziali
                val email = emailEditText.text.toString().replace(" ", "")
                val password = passWordEditText.text.toString()
                val progress: ProgressDialog = ProgressDialog(context)
                progress.setTitle("Controllando le credenziali...")
                progress.show()
                confermabtn.isEnabled = false

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
