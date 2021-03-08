package com.example.oop1parliament.recyclerview

import androidx.recyclerview.widget.DiffUtil
import com.example.oop1parliament.network.ParliamentMember

class MemberDiffCallback: DiffUtil.ItemCallback<ParliamentMember>() {
    override fun areItemsTheSame(oldItem: ParliamentMember, newItem: ParliamentMember): Boolean {
        return oldItem.hetekaId == newItem.hetekaId
    }
    override fun areContentsTheSame(oldItem: ParliamentMember, newItem: ParliamentMember): Boolean {
        return oldItem.firstname == newItem.firstname
    }
}