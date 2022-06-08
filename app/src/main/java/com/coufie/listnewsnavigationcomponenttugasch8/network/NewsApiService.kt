@file:Suppress("UnusedImport", "unused")

package com.coufie.listnewsnavigationcomponenttugasch8.network

import com.coufie.listnewsnavigationcomponenttugasch8.model.GetNewsItem
import com.coufie.listnewsnavigationcomponenttugasch8.model.GetUserItem
import com.coufie.listnewsnavigationcomponenttugasch8.model.PostUser
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface NewsApiService {

    @GET("news")
    fun getNews() : Call<List<GetNewsItem>>

    //register
    @POST("user")
    fun postUser(@Body reqUser: PostUser) : Call<GetUserItem>

    //Login
    @GET("user")
    fun getUser(
        @Query("username") username : String
    ) : Call<List<GetUserItem>>


}