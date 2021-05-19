package com.example.progetto_programmazionemobile.View

import android.os.Bundle
import android.view.*
import android.widget.Button
import androidx.fragment.app.Fragment
import com.example.progetto_programmazionemobile.R


class ProfileFragment : Fragment()
{
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val v: View = inflater.inflate(R.layout.fragment_home_profile_fragment, container, false)

        val btnEdit: Button = v.findViewById(R.id.btnConferma)
        btnEdit.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                //v!!.findNavController().navigate(R.id.editProfileFragment)
                var fr = getFragmentManager()?.beginTransaction()
                fr?.replace(R.id.fragment_container, EditProfileFragment())
                fr?.commit()

            }
        })
        return v
    }

}