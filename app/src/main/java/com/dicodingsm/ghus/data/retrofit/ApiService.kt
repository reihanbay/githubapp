package com.dicodingsm.ghus.data.retrofit

import com.dicodingsm.ghus.data.response.DetailUserResponse
import com.dicodingsm.ghus.data.response.Items
import com.dicodingsm.ghus.data.response.SearchResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    fun searchUser(@Query("q") query : String) : Call<SearchResponse>

    @GET("/users")
    fun getListUsers() : Call<List<Items>>

    @GET("users/{username}")
    fun detailUser(@Path("username") username : String) : Call<DetailUserResponse>

    @GET("users/{username}/{follow}")
    fun getFollowUser(@Path("username") username : String, @Path("follow") followPath: String) : Call<List<Items>>


}