package com.example.progetto_programmazionemobile.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.progetto_programmazionemobile.R
import com.example.progetto_programmazionemobile.ViewModel.Auth_Handler

class EditProfileFragment: Fragment()
{
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_home_editprofile, container, false)

        val userText = v.findViewById<TextView>(R.id.txtUsername)
        userText.setText(Auth_Handler.CURRENT_USER?.username)

        val userNome = v.findViewById<TextView>(R.id.editNome)
        userText.setHint(Auth_Handler.CURRENT_USER?.nome)
        //userText.setText(Auth.CURRENT_USER?.username)


        val btnConferma: Button = v.findViewById(R.id.btnConferma)
        btnConferma.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //v!!.findNavController().navigate(R.id.editProfileFragment)
                var fr = getFragmentManager()?.beginTransaction()
                fr?.replace(R.id.fragment_container, ProfileFragment())
                fr?.commit()
            }
        })

        return v
    }


}