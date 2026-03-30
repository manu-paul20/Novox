package com.manu.novox.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.manu.novox.data.local.entity.InteractedUsers
import kotlinx.coroutines.flow.Flow

@Dao
interface InteractedUsersDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addUser(user: InteractedUsers)

    @Query("delete from interacted_users where userName=:userName")
    suspend fun deleteUser(userName: String)

    @Query("select * from interacted_users order by lastInteracted desc")
    fun getAllUsers(): Flow<List<InteractedUsers>>

    @Query("update interacted_users set lastInteracted=:lastInteractionTime, lastMessage = :lastMessage where userName=:userName")
    suspend fun updateLastInteractionDetails(
        userName: String,
        lastInteractionTime: Long,
        lastMessage: String
    )

    @Query("update interacted_users set name=:name , profilePhoto=:profilePhoto where userName=:userName")
    suspend fun updateInteractedUserDetails(userName: String, name: String, profilePhoto: String)

    @Query("select exists(select 1 from interacted_users where userName=:userName)")
    fun isUserExist(userName: String): Boolean
}