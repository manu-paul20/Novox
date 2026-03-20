package com.manu.novox.domain.model

import com.manu.novox.data.local.entity.Message

sealed interface FirebaseUpdate {
    data class MessageAdded(val message: Message): FirebaseUpdate
    data class MessageRemoved(val messageId: String): FirebaseUpdate
}