package com.alexandergorin.data.models


import com.google.gson.annotations.SerializedName

data class TeamInfo(
    @SerializedName("crestUrl")
    val crestUrl: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String
)