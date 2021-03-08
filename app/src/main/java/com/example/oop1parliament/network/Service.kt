package com.example.oop1parliament.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

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