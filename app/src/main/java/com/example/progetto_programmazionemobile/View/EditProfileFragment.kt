package com.example.progetto_programmazionemobile.View

import android.app.Activity
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import com.example.progetto_programmazionemobile.R
import com.example.progetto_programmazionemobile.ViewModel.Auth_Handler
import com.example.progetto_programmazionemobile.ViewModel.DB_Handler_Users
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import kotlinx.android.synthetic.main.fragment_home_editprofile.profileImg
import java.math.RoundingMode

class EditProfileFragment: Fragment()
{
    var imgUri : Uri? = null
    lateinit var storage : FirebaseStorage
    lateinit var storageRef : StorageReference

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        storage = FirebaseStorage.getInstance()
        storageRef = storage.reference



        val v: View = inflater.inflate(R.layout.fragment_home_editprofile, container, false)

        val nameText = v.findViewById<TextView>(R.id.editNome)
        val cognomeText = v.findViewById<TextView>(R.id.editCognome)
        val cellulareText = v.findViewById<TextView>(R.id.editCellulare)
        val passwordText = v.findViewById<TextView>(R.id.passwordText)
        val confermaPasswordText = v.findViewById<TextView>(R.id.confermapasswordText)


        nameText.setText(Auth_Handler.CURRENT_USER?.nome!!.capitalize())
        cognomeText.setText(Auth_Handler.CURRENT_USER?.cognome!!.capitalize())
        cellulareText.setText(Auth_Handler.CURRENT_USER?.telefono)
        passwordText.setText(Auth_Handler.CURRENT_USER?.password)
        confermaPasswordText.setText(Auth_Handler.CURRENT_USER?.password)

        val btnConferma: Button = v.findViewById(R.id.btnConferma)
        btnConferma.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //CONTROLLO PASSWORD Abbastanza lunghe

                if ((nameText.text.toString() != "") || (cognomeText.text.toString() != "") || (cellulareText.text.toString() != "")) {
                    if(passwordText.text.length<6) {
                        Toast.makeText(context, "Password troppo corta (6 caratteri minimo)", Toast.LENGTH_SHORT).show()
                    }else{
                        //CONTROLLO PASSWORD UGUALI
                        if (passwordText.text.toString().equals(confermaPasswordText.text.toString())) {


                            //SALVA MODIFICHE FATTE
                            DB_Handler_Users.updateUserByEmail(
                                nameText.text.toString(),
                                cognomeText.text.toString(),
                                Auth_Handler.CURRENT_USER!!.email,
                                cellulareText.text.toString(),
                                passwordText.text.toString()
                            )

                            //Upload image selected
                            if (imgUri != null) {
                                uploadPicture()

                            } else {
                                val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())

                                builder.setTitle("Modifiche effettuate")
                                builder.setMessage("Operazione completata con successo")

                                builder.setPositiveButton("OK",
                                    object : DialogInterface.OnClickListener {
                                        override fun onClick(dialog: DialogInterface?, which: Int) {

                                            var fr = getFragmentManager()?.beginTransaction()
                                            fr?.replace(R.id.fragment_container, InfoFragment())
                                            fr?.commit()
                                        }
                                    })

                                val alertDialog = builder.create()
                                alertDialog.show()
                            }

                        } else {
                            Toast.makeText(context, "Conferma password errata", Toast.LENGTH_SHORT)
                                .show()
                        }

                    }
                } else {
                    Toast.makeText(context, "Inserire tutti i campi", Toast.LENGTH_SHORT).show()
                }
            }
        })
        val imgButton = v.findViewById<ImageView>(R.id.profileImg)
        imgButton.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                choosePicture()
            }
        })

        return v
    }

    private fun choosePicture() {
        val int = Intent(Intent.ACTION_GET_CONTENT)
        int.type="image/*"
        startActivityForResult(int,1)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==1 && resultCode==Activity.RESULT_OK && data!=null){
            imgUri = data.data
            profileImg.setImageURI(imgUri)
        }
    }

    private fun uploadPicture() {

        val progress : ProgressDialog = ProgressDialog(context)
        progress.setTitle("Caricamento...")
        progress.show()
        val picRef = storageRef.child("usersPics/"+Auth_Handler.CURRENT_USER!!.email)
        picRef.putFile(imgUri!!).addOnSuccessListener {
            progress.dismiss()

            val builder : AlertDialog.Builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Modifiche effettuate")
            builder.setMessage("Operazione completata con successo")

            builder.setPositiveButton("OK",object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {
                    var fr = getFragmentManager()?.beginTransaction()
                    fr?.replace(R.id.fragment_container, ProfileFragment())
                    fr?.commit()
                }
            })

            val alertDialog = builder.create()
            alertDialog.show()


        }.addOnFailureListener{
            progress.dismiss()
            val builder : AlertDialog.Builder = AlertDialog.Builder(requireContext())
            builder.setTitle("Errore")
            builder.setMessage("Qualcosa è andato storto...")

            builder.setPositiveButton("Riprova",object : DialogInterface.OnClickListener{
                override fun onClick(dialog: DialogInterface?, which: Int) {

                }
            })
        }.addOnProgressListener {
            var progPercent : Double = (it.bytesTransferred*100.00)/it.totalByteCount
            progress.setMessage("Completato : "+progPercent.toBigDecimal().setScale(1,RoundingMode.UP)+"%")
        }
    }
}