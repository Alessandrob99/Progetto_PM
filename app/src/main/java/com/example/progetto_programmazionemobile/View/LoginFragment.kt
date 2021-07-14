package com.example.progetto_programmazionemobile.View

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.example.progetto_programmazionemobile.R
import com.example.progetto_programmazionemobile.ViewModel.Auth_Handler
import org.json.JSONException


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
        val goToHomePage = Intent(v.context,HomePage_Activity::class.java)

        val confermabtn : Button = v.findViewById(R.id.confermaLog)
        val flagRicordami = v.findViewById<CheckBox>(R.id.ricordami)
        val userNameEditText = v.findViewById<EditText>(R.id.txtUsername)
        val passWordEditText = v.findViewById<EditText>(R.id.txtPassword)

        //Controllo se ci sono valori user e pass salvati nelle shared pref'
        var sharedPreferences : SharedPreferences? = activity?.getSharedPreferences("remember", Context.MODE_PRIVATE)
        if (sharedPreferences != null) {
            if(sharedPreferences.getBoolean("remember",false)){
                var savedUser = sharedPreferences?.getString("username","")
                var savedPass = sharedPreferences?.getString("password","")

                //Preimposto i campi username e password
                flagRicordami.isChecked = true
                userNameEditText.setText(savedUser)
                passWordEditText.setText(savedPass)

            }

        }


        flagRicordami.setOnCheckedChangeListener { buttonView, isChecked ->

            if(flagRicordami.isChecked==false){
                var sharedPreferences : SharedPreferences? = activity?.getSharedPreferences("remember", Context.MODE_PRIVATE)
                var editor : SharedPreferences.Editor? = sharedPreferences?.edit()
                if (editor != null) {
                    editor.putString("username","")
                    editor.putString("password","")
                    editor.putBoolean("remember",false)
                    editor.apply()
                }

            }

        }




        confermabtn.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                //Metodo che controlla la validita delle credenziali
                val userName = userNameEditText.text.toString()
                val password = passWordEditText.text.toString()

                if(((TextUtils.equals(userName,""))||((TextUtils.equals(password,""))))){
                    Toast.makeText(this@LoginFragment.context, "Fornire sia username che password", Toast.LENGTH_SHORT).show()
                }else{
                    Auth_Handler.checkCredentials(userName,password,object : Auth_Handler.Companion.MyCallback{
                        override fun onCallback() {
                            if(Auth_Handler.isLOGGED_IN()==true){
                                val goToHomePage = Intent(v?.context,HomePage_Activity::class.java)

                                //Se Ricordami == true  allora scrivi su file
                                Auth_Handler.setLOGGED_IN(context!!,flagRicordami.isChecked,userName,password)

                                //GO HOME INTENT
                                startActivityForResult(goToHomePage, 1)
                            }else{
                                Toast.makeText(this@LoginFragment.context, "Credenziali non valide", Toast.LENGTH_SHORT).show()
                            }
                        }
                    })
                }

                //----------
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
