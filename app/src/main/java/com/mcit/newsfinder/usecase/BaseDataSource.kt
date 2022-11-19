package com.mcit.newsfinder.usecase

import retrofit2.Response


abstract class BaseDataSource {

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): Resource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                if (response.body() == null) return error(" ${response.code()} ${"Return Data Empty"}")
                val body = response.body()
                response.headers()
                if (body != null) return Resource.success(body)

                if (call().code() == 406) {
                    return error(" ${response.code()} ${response.message()}")
                }
            }
            return error(" ${response.code()} ${response.message()}")
        } catch (e: Exception) {
            return error(e.message ?: e.toString())
        }
    }

    private fun <T> error(message: String): Resource<T> {

        return Resource.error("Network call has failed for a following reason: $message")
    }


}