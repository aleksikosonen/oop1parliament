package com.example.oop1parliament

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.selects.select

@Dao
interface ParliamentMemberDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(parliamentMember: ParliamentMember)

    @Query("select * from ParliamentMember order by party")
    fun getAll(): LiveData<List<ParliamentMember>>
}


@Database(entities = [ParliamentMember::class], version = 1, exportSchema = false)
abstract class ParliamentMemberDB: RoomDatabase() {
    abstract val parliamentMemberDao : ParliamentMemberDao
    companion object {
        @Volatile
        private var INSTANCE: ParliamentMemberDB? = null
        fun getInstance(context: Context): ParliamentMemberDB {
            synchronized(this) {
                var instance =
                    INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(
                        context,
                        ParliamentMemberDB::class.java,
                        "member_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}

@Dao
interface MemberVoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdate(memberVote: MemberVote)

    @Query("select * from MemberVote order by hetekaId")
    fun getAll(): LiveData<List<MemberVote>>

    @Query("select likeCount from MemberVote where hetekaId == :heteka")
    fun getMember(heteka: Int): Int
}

@Database(entities = [MemberVote::class], version = 1, exportSchema = false)
abstract class MemberVoteDB: RoomDatabase() {
    abstract val memberVoteDao : MemberVoteDao
    companion object {
        @Volatile
        private var INSTANCE: MemberVoteDB? = null
        fun getInstance(context: Context): MemberVoteDB {
            synchronized(this) {
                var instance =
                        INSTANCE
                if(instance == null) {
                    instance = Room.databaseBuilder(
                            context,
                            MemberVoteDB::class.java,
                            "member_vote_database"
                    ).fallbackToDestructiveMigration().build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}