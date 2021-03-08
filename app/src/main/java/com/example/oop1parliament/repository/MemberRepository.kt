package com.example.oop1parliament.repository

import androidx.lifecycle.LiveData
import com.example.oop1parliament.database.ParliamentMemberDB
import com.example.oop1parliament.network.MemberApi
import com.example.oop1parliament.network.ParliamentMember
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