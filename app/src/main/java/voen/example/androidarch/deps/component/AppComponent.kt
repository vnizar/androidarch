package voen.example.androidarch.deps.component

import dagger.Component
import voen.example.androidarch.deps.module.DatabaseModule
import voen.example.androidarch.section.ListActivity
import voen.example.androidarch.section.MainActivity
import voen.example.androidarch.section.RepoActivity
import javax.inject.Singleton

/**
 * Created by voen on 10/01/18.
 */

@Singleton
@Component(modules = [DatabaseModule::class])
interface AppComponent {
    fun inject(mainActivity: MainActivity)
    fun inject(mainActivity: RepoActivity)
    fun inject(listActivity: ListActivity)
}