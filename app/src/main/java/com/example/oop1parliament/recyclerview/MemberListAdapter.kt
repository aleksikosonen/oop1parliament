package com.example.oop1parliament.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import com.bumptech.glide.Glide
import com.example.oop1parliament.R
import com.example.oop1parliament.network.ParliamentMember

class MemberListAdapter(private val context: Context): ListAdapter<ParliamentMember, ViewHolder>(MemberDiffCallback()) {

    var selectedHeteka : Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.member_item_view, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.itemView.findViewById<TextView>(R.id.membername).text = getItem(position).firstname + " " +  getItem(position).lastname
        holder.itemView.findViewById<TextView>(R.id.memberparty).text = getItem(position).party
        holder.itemView.findViewById<ImageView>(R.id.thumbnail).setImageResource(R.drawable.vihrea)
        Glide.with(context).load("https://avoindata.eduskunta.fi/" + getItem(position).pictureUrl).into(holder.itemView.findViewById<ImageView>(R.id.thumbnail))
        holder.itemView.setOnClickListener{
            selectedHeteka = getItem(position).hetekaId
            val bundle = bundleOf("heteka" to selectedHeteka)
            it.findNavController().navigate(R.id.action_global_memberDetailsFragment, bundle)
        }
    }
}