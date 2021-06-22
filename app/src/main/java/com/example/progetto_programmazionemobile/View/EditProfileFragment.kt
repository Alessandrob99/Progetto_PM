package com.example.progetto_programmazionemobile.View

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.progetto_programmazionemobile.R
import com.example.progetto_programmazionemobile.ViewModel.Auth_Handler
import com.example.progetto_programmazionemobile.ViewModel.DB_Handler

class EditProfileFragment: Fragment()
{
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        val v: View = inflater.inflate(R.layout.fragment_home_editprofile, container, false)

        val userText = v.findViewById<TextView>(R.id.txtUsername)
        val nameText = v.findViewById<TextView>(R.id.editNome)
        val emailText = v.findViewById<TextView>(R.id.editEmail)
        val cognomeText = v.findViewById<TextView>(R.id.editCognome)
        val cellulareText = v.findViewById<TextView>(R.id.editCellulare)
        val passwordText = v.findViewById<TextView>(R.id.passwordText)
        val confermaPasswordText = v.findViewById<TextView>(R.id.confermapasswordText)


        userText.setText(Auth_Handler.CURRENT_USER?.username)
        nameText.setText(Auth_Handler.CURRENT_USER?.nome)
        cognomeText.setText(Auth_Handler.CURRENT_USER?.cognome)
        emailText.setText(Auth_Handler.CURRENT_USER?.email)
        cellulareText.setText(Auth_Handler.CURRENT_USER?.telefono)
        passwordText.setText(Auth_Handler.CURRENT_USER?.password)
        confermaPasswordText.setText(Auth_Handler.CURRENT_USER?.password)

        val btnConferma: Button = v.findViewById(R.id.btnConferma)
        btnConferma.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //CONTROLLO PASSWORD UGUALI
                if((nameText.text.toString()!="")||(cognomeText.text.toString()!="")||(emailText.text.toString()!="")||(cellulareText.text.toString()!="")){
                    if(passwordText.text.toString().equals(confermaPasswordText.text.toString())){
                        //SALVA MODIFICHE FATTE
                        DB_Handler.updateUserByUsername(Auth_Handler.CURRENT_USER?.username,nameText.text.toString(),cognomeText.text.toString(),emailText.text.toString(),cellulareText.text.toString(),passwordText.text.toString())
                        var fr = getFragmentManager()?.beginTransaction()
                        fr?.replace(R.id.fragment_container, infoFragment())
                        fr?.commit()
                    }else{
                        Toast.makeText(context,"Conferma password errata",Toast.LENGTH_SHORT).show()
                    }
                }else{
                    Toast.makeText(context,"Inserire tutti i campi",Toast.LENGTH_SHORT).show()
                }






                //v!!.findNavController().navigate(R.id.editProfileFragment)

            }
        })

        return v
    }

}