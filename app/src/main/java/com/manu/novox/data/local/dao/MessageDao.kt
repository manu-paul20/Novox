package com.manu.novox.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.manu.novox.data.local.entity.Message
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMessage(message: Message)

    @Query("delete from messages where receiverId=:receiverId")
    suspend fun deleteMessage(receiverId: String)

    @Query("select * from messages where receiverId=:receiverId")
    fun getMessages(receiverId: String): Flow<List<Message>>
}