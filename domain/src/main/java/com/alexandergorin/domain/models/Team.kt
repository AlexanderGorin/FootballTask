package com.alexandergorin.domain.models

data class Team(
    val id: Int,
    val name: String,
    val logoURL: String,
    val teamMembers: List<Member>
)