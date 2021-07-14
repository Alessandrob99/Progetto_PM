package com.example.progetto_programmazionemobile.View

import android.os.Bundle
import android.view.*
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.progetto_programmazionemobile.R
import com.example.progetto_programmazionemobile.ViewModel.Auth_Handler
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_home_profile_fragment.*


class ProfileFragment : Fragment()
{
    private lateinit var password : EditText
    private lateinit var showpass : ImageView


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val v: View = inflater.inflate(R.layout.fragment_home_profile_fragment, container, false)
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        val picRef = storageRef.child("usersPics/"+Auth_Handler.CURRENT_USER!!.username)





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

        picRef.downloadUrl.addOnSuccessListener{
            Glide.with(context).load(it).into(editProfileImgBtn)
        }



        val btnEdit: Button = v.findViewById(R.id.btnConferma)
        btnEdit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                var fr = getFragmentManager()?.beginTransaction()
                fr?.replace(R.id.fragment_container, EditProfileFragment(),"MODIFICA_PROFILO")
                fr?.commit()
            }
        })
        return v



    }

}

