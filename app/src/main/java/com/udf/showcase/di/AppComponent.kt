package com.udf.showcase.di

import android.content.Context
import com.factorymarket.rxelm.log.LogType
import com.factorymarket.rxelm.log.RxElmLogger
import com.factorymarket.rxelm.program.ProgramBuilder
import com.udf.showcase.SampleApp
import com.udf.showcase.data.AppPrefs
import com.udf.showcase.data.GitHubService
import com.udf.showcase.data.IApiService
import com.udf.showcase.data.IAppPrefs
import dagger.BindsInstance
import dagger.Component
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import timber.log.Timber
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppComponent.AppModule::class]
)
interface AppComponent {

    fun plusActivityComponent(module: ActivityModule): ActivityComponent

    @Component.Builder
    interface Builder {

        fun build(): AppComponent

        @BindsInstance
        fun application(app: SampleApp): Builder
    }

    @Module
    class AppModule {

        @Provides
        @Singleton
        fun provideContext(app: SampleApp): Context = app

        @Provides
        @Singleton
        fun githubService(): IApiService {
            return GitHubService(Schedulers.io())
        }

        @Provides
        @Singleton
        fun programBuilder(): ProgramBuilder {
            return ProgramBuilder()
                .outputScheduler(AndroidSchedulers.mainThread())
                .handleCmdErrors(true)
                .logger(object : RxElmLogger {

                    override fun logType(): LogType {
                        return LogType.UpdatesAndCommands
                    }

                    override fun error(stateName: String, t: Throwable) {
                        Timber.tag(stateName).e(t)
                    }

                    override fun log(stateName: String, message: String) {
                        Timber.tag(stateName).d(message)
                    }

                })
        }

        @Provides
        @Singleton
        fun appPrefs(context: Context): IAppPrefs {
            return AppPrefs(context.getSharedPreferences("prefs", Context.MODE_PRIVATE))
        }

    }

}