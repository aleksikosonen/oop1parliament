package com.example.oop1parliament.recyclerview

import androidx.recyclerview.widget.DiffUtil

class PartyDiffCallback : DiffUtil.ItemCallback<String>() {
    //Callback for recyclerview
    override fun areItemsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: String, newItem: String): Boolean {
        return oldItem == newItem
    }
}