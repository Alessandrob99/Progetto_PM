package com.example.progetto_programmazionemobile.View

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RelativeLayout
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.PagerAdapter
import com.example.progetto_programmazionemobile.R

class MyAdapterViewPager(var context: FragmentActivity?) : RecyclerView.Adapter<MyAdapterViewPager.MyViewHolder>()
{

    var color_icon = arrayOf<IntArray>(
        intArrayOf(android.R.color.white, R.drawable.domatore),
        intArrayOf(android.R.color.white, R.drawable.caparezza))

    class MyViewHolder(itemView:View) :RecyclerView.ViewHolder(itemView){
        lateinit var img_view:ImageView
        lateinit var container:RelativeLayout

        init {
            img_view = itemView.findViewById(R.id.imageViewClub) as ImageView
            container = itemView.findViewById(R.id.lezzo) as RelativeLayout
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
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
