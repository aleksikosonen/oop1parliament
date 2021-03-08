package com.example.oop1parliament.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.ListAdapter
import com.example.oop1parliament.R

class PartyListAdapter(private val context: Context): ListAdapter<String, MemberViewHolder>(PartyDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        //Inflate the party list view layout to recyclerview
        val itemView = LayoutInflater.from(context).inflate(R.layout.party_list_view, parent, false)
        return MemberViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        //When-clause to select correct party-image
        when (getItem(position).toString()) {
            "vihr" -> holder.itemView.findViewById<ImageView>(R.id.partyImage).setImageResource(R.drawable.vihrea)
            "vas" -> holder.itemView.findViewById<ImageView>(R.id.partyImage).setImageResource(R.drawable.vas)
            "kd" -> holder.itemView.findViewById<ImageView>(R.id.partyImage).setImageResource(R.drawable.kd)
            "liik" -> holder.itemView.findViewById<ImageView>(R.id.partyImage).setImageResource(R.drawable.liik)
            "kok" -> holder.itemView.findViewById<ImageView>(R.id.partyImage).setImageResource(R.drawable.kokoomus)
            "kesk" -> holder.itemView.findViewById<ImageView>(R.id.partyImage).setImageResource(R.drawable.keskusta_logo_2020)
            "ps" -> holder.itemView.findViewById<ImageView>(R.id.partyImage).setImageResource(R.drawable.peruss_logo_rgb)
            "r" -> holder.itemView.findViewById<ImageView>(R.id.partyImage).setImageResource(R.drawable.rkp)
            "sd" -> holder.itemView.findViewById<ImageView>(R.id.partyImage).setImageResource(R.drawable.sdp)
        }

        //OnClick-listener to send selected party as a bundle to PartyMembers fragment
        holder.itemView.setOnClickListener{
            val selectedParty = getItem(position)
            val bundle = bundleOf("party" to selectedParty)
            it.findNavController().navigate(R.id.action_global_partyMembersFragment, bundle)
        }
    }
}