package org.imaginativeworld.simplemvvm.di

import dagger.Component
import org.imaginativeworld.simplemvvm.views.fragments.post.PostFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface ApplicationGraph {

    fun inject(fragment: PostFragment)

}