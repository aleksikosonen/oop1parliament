package com.example.oop1parliament.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Transformations
import com.example.oop1parliament.repository.MemberRepository
import com.example.oop1parliament.database.ParliamentMemberDB

class PartySelectViewModel(application: Application): AndroidViewModel(application) {
    //Getting live data list of parliament members from member repository
    private val memberRepository = MemberRepository(ParliamentMemberDB.getInstance(application.applicationContext))
    private val parliamentMembers = memberRepository.members

    //Filtering the parties from the members to own live data list
    val parliamentParties = Transformations.map(parliamentMembers) { parliamentMembers.value?.distinctBy { it.party }?.map { it.party } }

}