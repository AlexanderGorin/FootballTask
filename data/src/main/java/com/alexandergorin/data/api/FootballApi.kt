package com.alexandergorin.data.api

import com.alexandergorin.data.models.StandingsResult
import com.alexandergorin.data.models.TeamResult
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path

interface FootballApi {

    @GET("competitions/{id}/standings")
    fun getStandings(@Path("id") competitionId: Int): Observable<StandingsResult>

    @GET("teams/{id}/")
    fun getTeamInfo(@Path("id") teamId: Int): Observable<TeamResult>
}