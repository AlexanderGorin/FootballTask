package com.alexandergorin.domain.exceptions

sealed class FootballException : Exception() {
    data class NoTopTeamInStandings(val competitionId: Int) : FootballException()
}