package space.iqbalsyafiq.movieapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import space.iqbalsyafiq.core.di.databaseModule
import space.iqbalsyafiq.core.di.networkModule
import space.iqbalsyafiq.core.di.repositoryModule
import space.iqbalsyafiq.movieapp.di.useCaseModule
import space.iqbalsyafiq.movieapp.di.viewModelModule

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.NONE)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    databaseModule,
                    networkModule,
                    repositoryModule,
                    useCaseModule,
                    viewModelModule
                )
            )
        }
    }
}