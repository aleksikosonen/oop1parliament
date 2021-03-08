package com.example.oop1parliament.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.oop1parliament.database.MemberVoteDB
import com.example.oop1parliament.network.MemberVote

class CommentsViewModel (application: Application) : AndroidViewModel(application) {

    private val memberVoteDB = MemberVoteDB.getInstance(application.applicationContext)
    val membersToVote: LiveData<List<MemberVote>>
        get() = memberVoteDB.memberVoteDao.getAll()
}