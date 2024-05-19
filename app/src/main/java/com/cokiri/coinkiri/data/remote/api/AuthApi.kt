package com.cokiri.coinkiri.data.remote.api

import com.cokiri.coinkiri.data.AuthRequired
import com.cokiri.coinkiri.data.remote.model.SignUpRequest
import com.cokiri.coinkiri.data.remote.model.SignUpResponse
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST

interface AuthApi {

    @AuthRequired
    @Headers("Content-Type: application/json")
    @POST("/api/v1/auth/signup")
    suspend fun signUpUser(@Body request: SignUpRequest) : SignUpResponse

    @AuthRequired
    @Headers("Content-Type: application/json")
    @POST("/api/v1/auth/logout")
    suspend fun logoutUser(@Header("Authorization") accessToken: String)

}