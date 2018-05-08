package voen.example.androidarch

import android.app.Application
import voen.example.androidarch.deps.component.AppComponent
import voen.example.androidarch.deps.component.DaggerAppComponent
import voen.example.androidarch.deps.module.DatabaseModule

/**
 * Created by voen on 10/01/18.
 */
class MyApp : Application(){
    val appComponent: AppComponent by lazy {
        DaggerAppComponent
                .builder()
                .databaseModule(DatabaseModule(this))
                .build()
    }
}