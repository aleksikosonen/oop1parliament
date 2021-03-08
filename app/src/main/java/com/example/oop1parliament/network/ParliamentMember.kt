package com.example.oop1parliament.network

import androidx.room.*

//Data class for ParliamentMember-object

@Entity
data class ParliamentMember(
    @PrimaryKey
    val hetekaId: Int,
    val seatNumber: Int = 0,
    val lastname: String,
    val firstname: String,
    val party: String,
    val minister: Boolean,
    val pictureUrl: String = "",
)