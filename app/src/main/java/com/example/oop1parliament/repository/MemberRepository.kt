package com.example.oop1parliament.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.example.oop1parliament.MemberApi
import com.example.oop1parliament.ParliamentMember
import com.example.oop1parliament.ParliamentMemberDB
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MemberRepository(private val database: ParliamentMemberDB) {
    val members: LiveData<List<ParliamentMember>> = database.parliamentMemberDao.getAll()

    suspend fun getMembers() {
        withContext(Dispatchers.IO) {
            val memberList = MemberApi.retrofitService.getMembers()
            for (member in 0..memberList.size) database.parliamentMemberDao.insertOrUpdate(memberList[member])
        }
    }

}