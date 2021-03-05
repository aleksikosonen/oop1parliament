package com.example.oop1parliament.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.oop1parliament.R

class PartyListAdapter(private val context: Context): ListAdapter<String, ViewHolder>(PartyDiffCallback()) {
    override fun onCreateViewHolder(vg: ViewGroup, vt: Int): ViewHolder {
        val itemView = LayoutInflater.from(context).inflate(R.layout.party_list_view, vg, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(vh: ViewHolder, pos: Int) {
        vh.itemView.findViewById<ImageView>(R.id.partyImage).setImageResource(R.drawable.kokoomus)
        when (getItem(pos).toString()) {
            "vihr" -> vh.itemView.findViewById<ImageView>(R.id.partyImage).setImageResource(R.drawable.vihrea)
            "vas" -> vh.itemView.findViewById<ImageView>(R.id.partyImage).setImageResource(R.drawable.vas)
            "kd" -> vh.itemView.findViewById<ImageView>(R.id.partyImage).setImageResource(R.drawable.kd)
            "liik" -> vh.itemView.findViewById<ImageView>(R.id.partyImage).setImageResource(R.drawable.liik)
            "kok" -> vh.itemView.findViewById<ImageView>(R.id.partyImage).setImageResource(R.drawable.kokoomus)
            "kesk" -> vh.itemView.findViewById<ImageView>(R.id.partyImage).setImageResource(R.drawable.keskusta_logo_2020)
            "ps" -> vh.itemView.findViewById<ImageView>(R.id.partyImage).setImageResource(R.drawable.peruss_logo_rgb)
            "r" -> vh.itemView.findViewById<ImageView>(R.id.partyImage).setImageResource(R.drawable.rkp)
            "sd" -> vh.itemView.findViewById<ImageView>(R.id.partyImage).setImageResource(R.drawable.sdp)
        }
        vh.itemView.setOnClickListener{
            val selectedParty = getItem(pos)
            val bundle = bundleOf("party" to selectedParty)
            it.findNavController().navigate(R.id.action_global_partyMembersFragment, bundle)
        }
    }
}