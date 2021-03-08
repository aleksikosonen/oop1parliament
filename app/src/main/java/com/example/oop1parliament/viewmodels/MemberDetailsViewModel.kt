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

    private var selectedHeteka = heteka

    //Getting live data list of parliament members from member repository
    private val memberRepository = MemberRepository(ParliamentMemberDB.getInstance(application.applicationContext))
    val parliamentMembers: LiveData<List<ParliamentMember>>
        get() = memberRepository.members

    //Getting live data list of parliament members to vote from membervote database
    private val memberVoteDB = MemberVoteDB.getInstance(application.applicationContext)
    val membersToVote: LiveData<List<MemberVote>>
        get() = memberVoteDB.memberVoteDao.getAll()

    //Returns selected member name for fragment
    fun getMemberName() : String {
        val firstName = parliamentMembers.value?.find { it.hetekaId == selectedHeteka }?.firstname
        val lastName = parliamentMembers.value?.find { it.hetekaId == selectedHeteka }?.lastname
        return "$firstName $lastName"
    }

    //Returns selected member url for fragment
    fun getUrl() : String {
        val url = parliamentMembers.value?.find { it.hetekaId == selectedHeteka }?.pictureUrl
        return "$url"
    }

    //Returns selected member party for fragment
    fun getParty() : String {
        val party = parliamentMembers.value?.find { it.hetekaId == selectedHeteka }?.party
        return "$party"
    }

    //Function for adding likes and comments for member
    fun voteMember(heteka: Int, likeCount: Int, addedComment : String) {
        viewModelScope.launch {
            memberVoteDB
                    .memberVoteDao
                    .insertOrUpdate(MemberVote(heteka, likeCount, addedComment))
        }
    }
}

//Factory for providing selected heteka to Viewmodel
class MemberViewModelFactory(private val application: Application, private val heteka: Int) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MemberDetailsViewModel::class.java)) {
            return MemberDetailsViewModel(application, heteka) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}