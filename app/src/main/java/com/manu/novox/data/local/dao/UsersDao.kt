package com.manu.novox.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.manu.novox.data.local.entity.InteractedUsers
import kotlinx.coroutines.flow.Flow

@Dao
interface UsersDao {

    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addUser(user: InteractedUsers)

    @Query("delete from interacted_users where id=:receiverId")
    suspend fun deleteUser(receiverId: String)

    @Query("select * from interacted_users")
    suspend fun getAllUsers(): Flow<List<InteractedUsers>>
}