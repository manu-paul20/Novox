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

    @Query("select * from interacted_users")
    fun getAllUsers(): Flow<List<InteractedUsers>>
}