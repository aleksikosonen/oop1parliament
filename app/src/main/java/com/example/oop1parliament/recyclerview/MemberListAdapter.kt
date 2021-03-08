package com.example.oop1parliament.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.example.oop1parliament.R
import com.example.oop1parliament.network.ParliamentMember

class MemberListAdapter(private val context: Context): ListAdapter<ParliamentMember, MemberViewHolder>(MemberDiffCallback()) {

    private var selectedHeteka : Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        //Inflates the member item view layout to recyclerview
        val itemView = LayoutInflater.from(context).inflate(R.layout.member_item_view, parent, false)
        return MemberViewHolder(itemView)
    }

    //Binds name, party and picture to recyclerview and sends selected heteka as bundle to member details fragment
    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.membername).text = getItem(position).firstname + " " +  getItem(position).lastname
        holder.itemView.findViewById<TextView>(R.id.memberparty).text =
                when (getItem(position).party) {
                    "vihr" -> "Vihreä liitto"
                    "vas" -> "Vasemmistoliitto"
                    "kd" -> "Suomen Kristillisdemokraatit"
                    "liik" -> "Liike Nyt"
                    "r" -> "Ruotsalainen kansanpuolue"
                    "kesk" -> "Suomen Keskusta"
                    "kok" -> "Kansallinen Kokoomus"
                    "ps" -> "Perussuomalaiset"
                    "sd" -> "Sosiaalidemokraattinen Puolue"
                    else -> "Ei puoluetta"
                }
        Glide.with(context).load("https://avoindata.eduskunta.fi/" + getItem(position).pictureUrl).into(holder.itemView.findViewById(R.id.thumbnail))
        holder.itemView.setOnClickListener{
            selectedHeteka = getItem(position).hetekaId
            val bundle = bundleOf("heteka" to selectedHeteka)
            it.findNavController().navigate(R.id.action_global_memberDetailsFragment, bundle)
        }
    }
}