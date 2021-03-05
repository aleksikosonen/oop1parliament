package com.example.oop1parliament

import android.app.Application
import android.util.Log
import android.view.View
import androidx.lifecycle.*
import androidx.room.*
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

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

@Entity
data class MemberVote(
        @PrimaryKey
        val hetekaId: Int,
        val likeCount: Int = 0,
        val comments: String = ""
)

private const val BASE_URL = "https://avoindata.eduskunta.fi/"
private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

interface MemberApiService {
    @GET("api/v1/seating/") //add here the end point
    suspend fun getMembers(): List<ParliamentMember>
}

object MemberApi {
    val retrofitService : MemberApiService by lazy {
        retrofit.create(MemberApiService::class.java) }
}