package com.anangkur.wallpaper.presentation.model

data class BaseResult<out T>(
    val status: Status,
    val data: T?,
    val message: String?,
    val isLoading: Boolean?
) {

    companion object {

        sealed class Status {
            object Success: Status()
            object Error: Status()
            object Loading: Status()
        }

        fun <T> success(data: T): BaseResult<T> {
            return BaseResult(
                Status.Success,
                data,
                null,
                null
            )
        }

        fun <T> error(message: String): BaseResult<T> {
            return BaseResult(
                Status.Error,
                null,
                message,
                null
            )
        }

        fun <T> loading(isLoading: Boolean): BaseResult<T> {
            return BaseResult(
                Status.Loading,
                null,
                null,
                isLoading
            )
        }
    }
}