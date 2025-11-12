package com.crunchry.animusicplayer.network.data

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class UserBody(
    @SerialName("user_id")
    private val userId: Int = 1
)
