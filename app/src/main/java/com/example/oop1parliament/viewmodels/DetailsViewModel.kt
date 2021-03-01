package com.example.oop1parliament.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.oop1parliament.MemberVote
import com.example.oop1parliament.MemberVoteDB

class DetailsViewModel (application: Application) : AndroidViewModel(application) {

    private val memberVoteDB = MemberVoteDB.getInstance(application.applicationContext)
    val membersToVote: LiveData<List<MemberVote>>
        get() = memberVoteDB.memberVoteDao.getAll()

    fun getComments(heteka: Int) : String {
        val comments = membersToVote.value?.find { it.hetekaId == heteka }?.comments
        return "$comments"
    }
}