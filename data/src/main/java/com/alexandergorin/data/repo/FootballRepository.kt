package com.alexandergorin.data.repo

import com.alexandergorin.data.api.FootballApi
import com.alexandergorin.domain.Repository
import com.alexandergorin.domain.exceptions.FootballException
import com.alexandergorin.domain.models.Member
import com.alexandergorin.domain.models.Team
import io.reactivex.Observable
import javax.inject.Inject

class FootballRepository @Inject constructor(
    private val service: FootballApi
) : Repository {

    override fun getTopTeam(competitionId: Int): Observable<Team> {
        return service.getStandings(competitionId)
            .map { result ->
                result.standings.firstOrNull()?.table?.firstOrNull()?.team
                    ?: throw FootballException.NoTopTeamInStandings(competitionId)
            }
            .flatMap { team -> service.getTeamInfo(team.id) }
            .map { info ->
                Team(
                    id = info.id,
                    name = info.name,
                    logoURL = info.crestUrl,
                    teamMembers = info.squad.map {
                        Member(
                            name = it.name,
                            position = it.position,
                            nationality = it.nationality,
                            dateOfBirth = it.dateOfBirth.toLocalDate()
                        )
                    })
            }
    }
}