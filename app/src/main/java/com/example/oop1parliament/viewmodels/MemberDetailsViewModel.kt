package com.example.oop1parliament

import android.app.Application
import androidx.lifecycle.*
import com.example.oop1parliament.database.MemberVoteDB
import com.example.oop1parliament.database.ParliamentMemberDB
import com.example.oop1parliament.network.MemberVote
import com.example.oop1parliament.network.ParliamentMember
import com.example.oop1parliament.repository.MemberRepository
import kotlinx.coroutines.launch

class MemberDetailsViewModel(application: Application, heteka: Int) : AndroidViewModel(application) {

    var selectedHeteka = heteka

    private val memberRepository = MemberRepository(ParliamentMemberDB.getInstance(application.applicationContext))
    val parliamentMembers: LiveData<List<ParliamentMember>>
        get() = memberRepository.members

    private val memberVoteDB = MemberVoteDB.getInstance(application.applicationContext)
    val membersToVote: LiveData<List<MemberVote>>
        get() = memberVoteDB.memberVoteDao.getAll()

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