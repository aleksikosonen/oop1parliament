package com.example.oop1parliament

import android.app.Application
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.*
import com.bumptech.glide.Glide
import kotlinx.coroutines.handleCoroutineException
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

class MemberDetailsViewModel(application: Application, heteka: Int) : AndroidViewModel(application) {

    var selectedHeteka = heteka

    private val memberRepository = MemberRepository(ParliamentMemberDB.getInstance(application.applicationContext))
    val parliamentMembers: LiveData<List<ParliamentMember>>
        get() = memberRepository.members

    private val memberVoteDB = MemberVoteDB.getInstance(application.applicationContext)
    val membersToVote: LiveData<List<MemberVote>>
        get() = memberVoteDB.memberVoteDao.getAll()

    //var name = parliamentMembers.value?.find { it.hetekaId==selectedHeteka }?.firstname.toString()

    fun getMemberName() : String {
        val firstName = parliamentMembers.value?.find { it.hetekaId == selectedHeteka }?.firstname
        val lastName = parliamentMembers.value?.find { it.hetekaId == selectedHeteka }?.lastname
        return "$firstName $lastName"
    }

    fun getUrl() : String {
        val url = parliamentMembers.value?.find { it.hetekaId == selectedHeteka }?.pictureUrl
        return "$url"
    }

    fun getParty() : String {
        val party = parliamentMembers.value?.find { it.hetekaId == selectedHeteka }?.party
        return "$party"
    }
/*
    fun getMemberName(heteka: Int) : String {
        val firstName = parliamentMembers.value?.find { it.hetekaId == heteka }?.firstname
        val lastName = parliamentMembers.value?.find { it.hetekaId == heteka }?.lastname
        return "$firstName $lastName"
    }*/

    fun getMemberParty(heteka: Int) : String {
        val party = parliamentMembers.value?.find { it.hetekaId == heteka }?.party
        return "$party"
    }

    fun getMemberVotes() : String {
        val votes = membersToVote.value?.find { it.hetekaId == selectedHeteka }?.likeCount.toString()
        return "$votes"
    }

    fun voteMember(heteka: Int, likeCount: Int, addedComment : String) {
        viewModelScope.launch {
            memberVoteDB
                    .memberVoteDao
                    .insertOrUpdate(MemberVote(heteka, likeCount, addedComment))
        }
    }
}

class MemberViewModelFactory(private val application: Application, private val heteka: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemberDetailsViewModel::class.java)) {
            return MemberDetailsViewModel(application, heteka) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}