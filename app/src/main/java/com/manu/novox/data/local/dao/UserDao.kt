package com.manu.novox.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Update
import com.manu.novox.data.local.entity.User
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Query("select * from user")
    fun getUserDetails(): Flow<User>

    @Update
    suspend fun updateUserDetails(user: User)
}