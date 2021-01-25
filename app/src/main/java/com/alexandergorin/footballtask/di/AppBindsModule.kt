package com.alexandergorin.footballtask.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.alexandergorin.data.repo.FootballRepository
import com.alexandergorin.domain.Repository
import com.alexandergorin.footballtask.team.TeamViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class AppBindsModule {

    @Singleton
    @Binds
    abstract fun bindFootballRepository(repository: FootballRepository): Repository

    @Binds
    @IntoMap
    @ViewModelKey(TeamViewModel::class)
    abstract fun bindViewModel(viewModel: TeamViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(viewModelProviderFactory: ViewModelFactory): ViewModelProvider.Factory
}
