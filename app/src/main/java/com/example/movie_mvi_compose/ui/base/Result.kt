package com.example.movie_mvi_compose.ui.base

import androidx.annotation.NonNull

data class Result<T>(val status: Int, val data: T?) {
    fun isError() = status == STATUS_ERROR
    fun isLoading() = status == STATUS_LOADING
    fun isNot() = status == STATUS_LOADING
    fun isSuccess() = status == STATUS_SUCCESS

    companion object {
        private const val STATUS_LOADING = 0
        private const val STATUS_SUCCESS = 1
        private const val STATUS_ERROR = -1
        private const val STATUS_NOT = 2
        /**
         * Helper method to create fresh state result
         */
        fun <T> success(@NonNull data: T): Result<T> {
            return Result(STATUS_SUCCESS, data)
        }
        fun <T> not(@NonNull data: T): Result<T> {
            return Result(STATUS_NOT, data)
        }

        /**
         * Helper method to create error state Result. Error state might also have the current data, if any
         */
        fun <T> error(item: T? = null): Result<T> {
            return Result(STATUS_ERROR, item)
        }

        /**
         * Helper method to create loading state Result.
         */
        fun <T> loading(data: T? = null): Result<T> {
            return Result(STATUS_LOADING, data)
        }
    }
}