package com.example.oop1parliament.network

import androidx.room.*

@Entity
data class MemberVote(
        @PrimaryKey
        val hetekaId: Int,
        val likeCount: Int = 0,
        val comments: String = ""
)