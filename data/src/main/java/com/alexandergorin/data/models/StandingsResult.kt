package com.alexandergorin.data.models


import com.google.gson.annotations.SerializedName

data class StandingsResult(
    @SerializedName("competition")
    val competition: Competition,
    @SerializedName("filters")
    val filters: Filters,
    @SerializedName("season")
    val season: Season,
    @SerializedName("standings")
    val standings: List<Standing>
)