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

    /*
    fun selectMember(heteka: Int) : String? {
        return parliamentMembers.value?.find {it.hetekaId == heteka}?.firstname
    }*/

    /*
    init {
        getMembers()
    }
    fun getMembers() {
        viewModelScope.launch {
            try {
                memberRepository.getMembers()

            } catch (e: Exception) {
                Log.d("***", e.toString())
            }
        }
    }
*/

    //val p = Parliament(ParliamentMembersData.members)

    val likeCounter = memberLikeCounter()

    val positiveSum: LiveData<Int> = Transformations.distinctUntilChanged(
            Transformations.map(likeCounter.likesList) { it.sum() }
    )
}