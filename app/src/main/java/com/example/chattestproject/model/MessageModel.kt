package com.example.chattestproject.model


data class MessageModel(
    val message: String = "",
    val senderId: String = "",
    val timestamp: String = "",
    val imageUrl: String? = null
)

