package com.alexandergorin.footballtask

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.alexandergorin.domain.Repository
import com.alexandergorin.domain.exceptions.FootballException
import com.alexandergorin.domain.models.Team
import com.alexandergorin.footballtask.team.TeamState
import com.alexandergorin.footballtask.team.TeamViewModel
import com.alexandergorin.footballtask.utils.ExceptionTransformer
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test

private const val COMPETITION_ID = 21
private const val ERROR_MESSAGE = "Error"
private val team = Team(1, "", "", emptyList())
private val exception = FootballException.NoTopTeamInStandings(COMPETITION_ID)

class TeamViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    private val exceptionTransformer: ExceptionTransformer = mockk(relaxed = true)
    private val repository: Repository = mockk(relaxed = true)
    private val observerState: Observer<TeamState?> = mockk(relaxed = true)
    private val observerErrorEvent: Observer<String?> = mockk(relaxed = true)
    private lateinit var viewModel: TeamViewModel

    @Before
    fun before() {
        viewModel = TeamViewModel(
            repository,
            exceptionTransformer,
            Schedulers.trampoline(),
            Schedulers.trampoline()
        )
        viewModel.teamState.observeForever(observerState)
        viewModel.errorEvent.observeForever(observerErrorEvent)
    }

    @Test
    fun load_should_start_loading_event() {

        every { repository.getTopTeam(any()) } returns Observable.just(team)

        viewModel.load()

        verify { observerState.onChanged(TeamState.Loading) }
    }

    @Test
    fun when_error_returns_should_post_error_event() {

        every { exceptionTransformer.getMessage(any()) } returns ERROR_MESSAGE
        every { repository.getTopTeam(any()) } returns Observable.error(exception)

        viewModel.load()

        verify {
            observerErrorEvent.onChanged(ERROR_MESSAGE)
        }
        verify {
            observerState.onChanged(
                TeamState.Error(
                    FootballException.NoTopTeamInStandings(
                        COMPETITION_ID
                    )
                )
            )
        }
    }

    @Test
    fun when_team_returns_should_post_result_event() {

        every { repository.getTopTeam(any()) } returns Observable.just(team)

        viewModel.load()

        verify { observerState.onChanged(TeamState.Loaded(team)) }
    }
}
