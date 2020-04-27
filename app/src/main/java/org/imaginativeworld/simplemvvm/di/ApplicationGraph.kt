package org.imaginativeworld.simplemvvm.di

import dagger.Component
import org.imaginativeworld.simplemvvm.views.activities.main.MainActivity
import org.imaginativeworld.simplemvvm.views.fragments.demo_home.DemoHomeFragment
import org.imaginativeworld.simplemvvm.views.fragments.demo_post.DemoPostFragment
import org.imaginativeworld.simplemvvm.views.fragments.demo_postpaged.DemoPostPagedFragment
import org.imaginativeworld.simplemvvm.views.fragments.demo_user.DemoUserFragment
import javax.inject.Singleton

@Singleton
@Component(modules = [AppModule::class])
interface ApplicationGraph {

    fun inject(activity: MainActivity)

    fun inject(fragment: DemoHomeFragment)

    fun inject(fragment: DemoPostFragment)

    fun inject(fragment: DemoUserFragment)

    fun inject(fragment: DemoPostPagedFragment)

}