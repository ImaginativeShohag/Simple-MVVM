package org.imaginativeworld.simplemvvm.di

import dagger.Component
import org.imaginativeworld.simplemvvm.views.activities.main.MainActivity
import org.imaginativeworld.simplemvvm.views.fragments.home.HomeFragment
import org.imaginativeworld.simplemvvm.views.fragments.post.PostFragment
import org.imaginativeworld.simplemvvm.views.fragments.user.UserFragment
import org.imaginativeworld.simplemvvm.views.fragments.userpaged.UserPagedFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface ApplicationGraph {

    fun inject(activity: MainActivity)

    fun inject(fragment: HomeFragment)

    fun inject(fragment: PostFragment)

    fun inject(fragment: UserFragment)

    fun inject(fragment: UserPagedFragment)

}