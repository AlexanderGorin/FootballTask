package com.alexandergorin.domain

import com.alexandergorin.domain.models.Team
import io.reactivex.Observable

interface Repository {
    fun getTopTeam(competitionId: Int): Observable<Team>
}