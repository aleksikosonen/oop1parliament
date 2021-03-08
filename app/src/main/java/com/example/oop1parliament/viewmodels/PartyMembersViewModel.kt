package com.example.oop1parliament.viewmodels

import android.app.Application
import androidx.lifecycle.*
import com.example.oop1parliament.repository.MemberRepository
import com.example.oop1parliament.database.ParliamentMemberDB

class PartyMembersViewModel(application: Application): AndroidViewModel(application) {
    //Getting live data list of parliament members from member repository
    private val memberRepository = MemberRepository(ParliamentMemberDB.getInstance(application.applicationContext))
    val parliamentMembers = memberRepository.members
}