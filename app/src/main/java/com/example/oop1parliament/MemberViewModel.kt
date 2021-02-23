package com.example.oop1parliament

import android.app.Application
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.*
import kotlinx.coroutines.handleCoroutineException
import kotlinx.coroutines.launch

class MemberViewModel(application: Application) : AndroidViewModel(application) {

    private val memberRepository = MemberRepository(ParliamentMemberDB.getInstance(application.applicationContext))
    val parliamentMembers: LiveData<List<ParliamentMember>>
        get() = memberRepository.members

    fun getMemberName(heteka: Int) : String {
        val firstName = parliamentMembers.value?.find { it.hetekaId == heteka }?.firstname
        val lastName = parliamentMembers.value?.find { it.hetekaId == heteka }?.lastname
        return "$firstName $lastName"
    }

    fun getMemberParty(heteka: Int) : String {
        val party = parliamentMembers.value?.find { it.hetekaId == heteka }?.party
        return "$party"
    }

    val abc = MemberVoteDB.getInstance(application.applicationContext)
    val membersToVote: LiveData<List<MemberVote>>
        get() = abc.memberVoteDao.getAll()

    fun voteMember(heteka: Int, likeCount: Int) {
        val context = getApplication<Application>().applicationContext
        viewModelScope.launch {
            MemberVoteDB.getInstance(context)
                    .memberVoteDao
                    .insertOrUpdate(MemberVote(heteka, likeCount))
        }
    }
}