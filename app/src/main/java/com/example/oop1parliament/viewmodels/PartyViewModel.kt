package com.example.oop1parliament.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.oop1parliament.MemberRepository
import com.example.oop1parliament.ParliamentMemberDB

class PartyViewModel(application: Application): AndroidViewModel(application) {
    val memberRepository = MemberRepository(ParliamentMemberDB.getInstance(application.applicationContext))
    val parliamentMembers = memberRepository.members

}