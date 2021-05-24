package com.example.progetto_programmazionemobile.View

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.progetto_programmazionemobile.Model.Utente
import com.example.progetto_programmazionemobile.R
import com.example.progetto_programmazionemobile.ViewModel.Auth_Handler
import com.example.progetto_programmazionemobile.ViewModel.DB_Handler

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

        val userNameEditText = v.findViewById<EditText>(R.id.txtUsername)
        val passWordEditText = v.findViewById<EditText>(R.id.txtPassword)

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
                                startActivity(goToHomePage)
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