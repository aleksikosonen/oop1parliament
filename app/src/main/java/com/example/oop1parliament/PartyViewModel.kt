package com.example.oop1parliament

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class PartyViewModel: ViewModel() {

    private val _parliamentMembers = MutableLiveData<List<ParliamentMember>>()
    val parliamentMembers: LiveData<List<ParliamentMember>>
    get() = _parliamentMembers
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
    }
}