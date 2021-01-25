package com.alexandergorin.data.models


import com.google.gson.annotations.SerializedName
import org.threeten.bp.LocalDateTime

data class Squad(
    @SerializedName("countryOfBirth")
    val countryOfBirth: String,
    @SerializedName("dateOfBirth")
    val dateOfBirth: LocalDateTime,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("nationality")
    val nationality: String,
    @SerializedName("position")
    val position: String,
    @SerializedName("role")
    val role: String,
    @SerializedName("shirtNumber")
    val shirtNumber: Any?
)