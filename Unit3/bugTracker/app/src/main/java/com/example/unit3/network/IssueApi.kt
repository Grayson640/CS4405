package com.example.unit3.network

import retrofit2.http.*
interface IssueApi {

    @GET("issues")
    suspend fun getIssues(): List<RemoteIssue>

    @POST("issues")
    suspend fun addIssue(
        @Body issue: RemoteIssue
    ): RemoteIssue

    @PUT("issues/{id}")
    suspend fun updateIssue(
        @Path("id") id: String,
        @Body issue: RemoteIssue
    ): RemoteIssue

    @DELETE("issues/{id}")
    suspend fun deleteIssue(
        @Path("id") id: String
    ): RemoteIssue

}