package com.manu.novox.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.manu.novox.data.local.entity.Message
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Upsert
    suspend fun insertMessage(message: Message)

    @Query("delete from messages where messageId=:messageId")
    suspend fun deleteMessage(messageId: String)

    @Query("delete from messages where chatId=:chatId")
    suspend fun deleteAllMessages(chatId: String)

    @Query("select * from messages where chatId=:chatId order by timeStamp asc")
    fun getMessages(chatId: String): Flow<List<Message>>
}