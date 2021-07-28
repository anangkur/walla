package com.anangkur.wallpaper.presentation.model

data class BaseResult<out T>(
    val status: Int,
    val data: T?,
    val message: String?,
    val isLoading: Boolean?
) {

    companion object {

        const val STATE_SUCCESS = 1
        const val STATE_ERROR = -1
        const val STATE_LOADING = 0

        fun <T> success(data: T): BaseResult<T> {
            return BaseResult(
                STATE_SUCCESS,
                data,
                null,
                null
            )
        }

        fun <T> error(message: String): BaseResult<T> {
            return BaseResult(
                STATE_ERROR,
                null,
                message,
                null
            )
        }

        fun <T> loading(isLoading: Boolean): BaseResult<T> {
            return BaseResult(
                STATE_LOADING,
                null,
                null,
                isLoading
            )
        }
    }
}