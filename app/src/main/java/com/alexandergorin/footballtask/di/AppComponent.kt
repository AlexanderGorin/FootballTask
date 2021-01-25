package com.alexandergorin.footballtask.di

import android.content.Context
import com.alexandergorin.footballtask.team.TeamFragment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [AppServiceModule::class, AppModule::class, AppBindsModule::class])
interface AppComponent {

    fun inject(teamFragment: TeamFragment)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): AppComponent
    }
}
