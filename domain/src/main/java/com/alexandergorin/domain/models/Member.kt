package com.alexandergorin.domain.models

import org.threeten.bp.LocalDate

data class Member(
    val name: String,
    val position: String,
    val nationality: String,
    val dateOfBirth: LocalDate
)