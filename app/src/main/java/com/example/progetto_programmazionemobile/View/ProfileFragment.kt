package com.example.progetto_programmazionemobile.View

import android.os.Bundle
import android.text.method.PasswordTransformationMethod
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.progetto_programmazionemobile.R
import com.example.progetto_programmazionemobile.ViewModel.Auth_Handler


class ProfileFragment : Fragment()
{
    private lateinit var password : EditText
    private lateinit var showpass : ImageView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_home_profile_fragment, container, false)

        val userText = v.findViewById<TextView>(R.id.txtUsername)
        val nameText = v.findViewById<TextView>(R.id.editNome)
        val emailText = v.findViewById<TextView>(R.id.editEmail)
        val cognomeText = v.findViewById<TextView>(R.id.editCognome)
        val cellulareText = v.findViewById<TextView>(R.id.editCellulare)
        userText.setText(Auth_Handler.CURRENT_USER?.username)
        nameText.setText(Auth_Handler.CURRENT_USER?.nome?.capitalize())
        cognomeText.setText(Auth_Handler.CURRENT_USER?.cognome?.capitalize())
        emailText.setText(Auth_Handler.CURRENT_USER?.email)
        cellulareText.setText(Auth_Handler.CURRENT_USER?.telefono)



        val btnEdit: Button = v.findViewById(R.id.btnConferma)
        btnEdit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //v!!.findNavController().navigate(R.id.editProfileFragment)
                var fr = getFragmentManager()?.beginTransaction()
                fr?.replace(R.id.fragment_container, EditProfileFragment(),"MODIFICA_PROFILO")
                fr?.commit()
            }
        })
        return v
    }

}

