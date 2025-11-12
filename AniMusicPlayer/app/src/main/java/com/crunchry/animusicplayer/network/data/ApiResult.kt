package com.crunchry.animusicplayer.network.data

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

sealed class ApiResult<out T> {
    data class Success<out T>(val data: T, val code: Int) : ApiResult<T>()
    data class Error(val code: Int, val message: String) : ApiResult<Nothing>()
}

inline fun <T> ApiResult<T>.onSuccess(block: (value: T) -> Unit): ApiResult<T> {
    if (this is ApiResult.Success) {
        block(this.data)
    }
    return this
}

/**
 * Return [Success.data] if [ApiResult] is [ApiResult.Success] else null.
 */
fun <T> ApiResult<T>.getSuccessOrNull(): T? {
    return when (this) {
        is ApiResult.Success -> data
        is ApiResult.Error -> null
    }
}

fun <T> ApiResult<T>.asFlow(): Flow<T> {
    return flow {
        if (this@asFlow is ApiResult.Success) {
            emit(this@asFlow.data)
        }
    }
}