package com.alexandergorin.footballtask.team

import com.alexandergorin.domain.models.Team

sealed class TeamState {
    object Loading : TeamState()
    data class Loaded(val team: Team) : TeamState()
    data class Error(val throwable: Throwable) : TeamState()
}
