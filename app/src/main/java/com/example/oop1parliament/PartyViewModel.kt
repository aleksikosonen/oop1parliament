package com.example.oop1parliament

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class PartyViewModel(application: Application): AndroidViewModel(application) {
    val memberRepository = MemberRepository(ParliamentMemberDB.getInstance(application.applicationContext))
    val parliamentMembers = memberRepository.members

/*

    private val _parliamentMembers = MutableLiveData<List<ParliamentMember>>()

    init {
        getMembers()
    }

    fun getMembers() {
        viewModelScope.launch {
            try {
                _parliamentMembers.value = MemberApi.retrofitService.getMembers() //use here the covidApi to get the values


            } catch (e: Exception) {
                Log.d("***", e.toString())
                _parliamentMembers.value = ArrayList()
            }
        }
    }*/
}