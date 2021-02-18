package com.example.oop1parliament

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import kotlinx.coroutines.launch

class MemberViewModel(application: Application) : AndroidViewModel(application) {
    private val memberRepository = MemberRepository(ParliamentMemberDB.getInstance(application.applicationContext))
    val memberList = memberRepository.members

    private val _members = MutableLiveData<List<ParliamentMember>>()
    val members : LiveData<List<ParliamentMember>>
        get() = _members

    init {
        insertMembers()
    }

    private fun insertMembers() {
        viewModelScope.launch {
            try {
                memberRepository.getMembers()

            } catch (e: Exception) {
                Log.d("***", e.toString())
            }
        }
    }

    val p = Parliament(ParliamentMembersData.members)

    val likeCounter = memberLikeCounter()

    val positiveSum: LiveData<Int> = Transformations.distinctUntilChanged(
            Transformations.map(likeCounter.likesList) { it.sum() }
    )
}