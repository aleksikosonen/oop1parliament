package com.example.oop1parliament.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET


//Base url for api
private const val BASE_URL = "https://avoindata.eduskunta.fi/"

//variable for Moshi
private val moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

//Variable for retrofit
private val retrofit = Retrofit.Builder()
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .baseUrl(BASE_URL)
        .build()

//MemberApiService interface for getting list of ParliamentMembers from api
interface MemberApiService {
    @GET("api/v1/seating/")
    suspend fun getMembers(): List<ParliamentMember>
}

//Object for MemberApi
object MemberApi {
    val retrofitService : MemberApiService by lazy {
        retrofit.create(MemberApiService::class.java) }
}