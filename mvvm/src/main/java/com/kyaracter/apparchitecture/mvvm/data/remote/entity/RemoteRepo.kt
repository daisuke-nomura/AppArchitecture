package com.kyaracter.apparchitecture.mvvm.data.remote.entity

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RemoteRepo(
    @Json(name = "id") val id: Long,
    @Json(name = "name") val name: String,
    @Json(name = "owner") val owner: Owner,
    @Json(name = "description") val description: String?,
    @Json(name = "html_url") val url: String
)


@JsonClass(generateAdapter = true)
data class Owner(@Json(name = "login") val login: String)