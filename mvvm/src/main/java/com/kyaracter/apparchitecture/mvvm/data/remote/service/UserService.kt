package com.kyaracter.apparchitecture.mvvm.data.remote.service

import com.kyaracter.apparchitecture.mvvm.data.remote.entity.RemoteRepo
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface UserService {

    @Headers(value = ["Accept: application/vnd.github.v3+json"])
    @GET("users/{owner}/repos")
    fun fetch(@Path("owner") user: String): Single<List<RemoteRepo>>
}