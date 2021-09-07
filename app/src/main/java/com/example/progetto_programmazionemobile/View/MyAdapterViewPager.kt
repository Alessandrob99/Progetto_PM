package com.example.progetto_programmazionemobile.View

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RelativeLayout
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.progetto_programmazionemobile.Model.Club
import com.example.progetto_programmazionemobile.R
import com.google.firebase.storage.FirebaseStorage

class MyAdapterViewPager(var context: FragmentActivity?, var circolo : Club) : RecyclerView.Adapter<MyAdapterViewPager.MyViewHolder>()
{

    var color_icon = arrayOf<IntArray>(
        intArrayOf(android.R.color.white, R.drawable.domatore),
        intArrayOf(android.R.color.white, R.drawable.caparezza))

    class MyViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView){
        lateinit var img_view:ImageView
        lateinit var container:RelativeLayout

        init {
            img_view = itemView.findViewById(R.id.imageViewClub) as ImageView
            container = itemView.findViewById(R.id.RelativeLayout1) as RelativeLayout
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val storage = FirebaseStorage.getInstance()
        val storageRef = storage.reference
        val picRef = storageRef.child("clubsPics/"+circolo.id)

        picRef.downloadUrl.addOnSuccessListener{
            //--------------------
        }


        return MyViewHolder(LayoutInflater.from(context).inflate(R.layout.image_club, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.img_view.setImageResource(color_icon[position][1])
        holder.container.setBackgroundResource(color_icon[position][0])
    }

    override fun getItemCount(): Int {
        return color_icon.size
    }


}
