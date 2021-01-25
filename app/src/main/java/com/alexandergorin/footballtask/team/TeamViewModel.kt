package com.alexandergorin.footballtask.team

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.alexandergorin.domain.Repository
import com.alexandergorin.domain.models.Team
import com.alexandergorin.footballtask.utils.ExceptionTransformer
import com.alexandergorin.footballtask.utils.Result
import com.alexandergorin.footballtask.utils.SingleLiveEvent
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject
import javax.inject.Named

class TeamViewModel @Inject constructor(
    private val repository: Repository,
    private val exceptionTransformer: ExceptionTransformer,
    @Named("IOScheduler") private val ioScheduler: Scheduler,
    @Named("UIScheduler") private val uiScheduler: Scheduler,
) : ViewModel() {

    private val bag = CompositeDisposable()

    val errorEvent: LiveData<String?>
        get() = _errorEvent
    private val _errorEvent = SingleLiveEvent<String?>(null)

    val teamState: LiveData<TeamState?>
        get() = _teamState
    private val _teamState = MutableLiveData<TeamState?>(null)

    fun load() {
        bag += repository.getTopTeam(COMPETITION_ID)
            .subscribeOn(ioScheduler)
            .observeOn(uiScheduler)
            .map<Result<Team>> { Result.Success(it) }
            .onErrorReturn { Result.Error(it) }
            .startWith(Result.Processing)
            .subscribeBy {
                when (it) {
                    is Result.Success -> _teamState.value = TeamState.Loaded(it.data)
                    is Result.Error -> {
                        _teamState.value = TeamState.Error(it.throwable)
                        _errorEvent.value = exceptionTransformer.getMessage(it.throwable)
                    }
                    Result.Processing -> _teamState.value = TeamState.Loading
                }
            }
    }

    override fun onCleared() {
        super.onCleared()
        bag.clear()
    }

    companion object {
        private const val COMPETITION_ID = 2021
    }
}
