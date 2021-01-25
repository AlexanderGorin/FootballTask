package com.alexandergorin.footballtask.utils

import android.content.Context
import com.alexandergorin.domain.exceptions.FootballException
import com.alexandergorin.footballtask.R
import javax.inject.Inject

class ExceptionTransformer @Inject constructor(private val context: Context) {

    fun getMessage(error: Throwable): String {
        return if (error is FootballException) {
            when (error) {
                is FootballException.NoTopTeamInStandings -> context.getString(
                    R.string.error_no_standings_for_id,
                    error.competitionId.toString()
                )
            }
        } else {
            error.localizedMessage ?: context.getString(R.string.error_unknown)
        }
    }
}