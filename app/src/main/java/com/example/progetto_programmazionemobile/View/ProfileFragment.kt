package com.example.progetto_programmazionemobile.View

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import com.example.progetto_programmazionemobile.R


class ProfileFragment : Fragment()
{
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v : View =  inflater.inflate(R.layout.fragment_home_profile_fragment, container, false)

        return v
    }
}