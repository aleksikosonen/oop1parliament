package com.example.oop1parliament.viewmodels

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.oop1parliament.MyApp
import com.example.oop1parliament.ParliamentMemberDB
import com.example.oop1parliament.repository.MemberRepository
import kotlinx.coroutines.launch

class TitleViewModel(application: Application) : AndroidViewModel(application) {
    private val memberRepository = MemberRepository(ParliamentMemberDB.getInstance(application.applicationContext))


    /*init {
        insertMembers()
    }*/

/*
    private fun insertMembers() {
        viewModelScope.launch {
            try {
                memberRepository.getMembers()

            } catch (e: Exception) {
                Log.d("***", e.toString())
            }
        }
    }*/
}