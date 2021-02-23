package com.example.oop1parliament

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.map
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MemberRepository(private val database: ParliamentMemberDB) {
    val members: LiveData<List<ParliamentMember>> = database.parliamentMemberDao.getAll()

    suspend fun getMembers() {
        withContext(Dispatchers.IO) {
            val memberList = MemberApi.retrofitService.getMembers()
            for (i in 1..memberList.size-1) database.parliamentMemberDao.insertOrUpdate(memberList[i])
        }
    }

}