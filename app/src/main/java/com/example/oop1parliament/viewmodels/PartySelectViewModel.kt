package com.example.oop1parliament.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.Transformations
import com.example.oop1parliament.repository.MemberRepository
import com.example.oop1parliament.database.ParliamentMemberDB

class PartySelectViewModel(application: Application): AndroidViewModel(application) {
    val memberRepository = MemberRepository(ParliamentMemberDB.getInstance(application.applicationContext))
    val parliamentMembers = memberRepository.members

    val parliamentParties = Transformations.map(parliamentMembers) {
        parliamentMembers.value?.distinctBy { it.party }?.map { it.party }
    }

}