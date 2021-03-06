package com.udf.showcase.login

import android.content.Context
import dev.teapot.cmd.Cmd
import dev.teapot.contract.Update
import dev.teapot.msg.Msg
import com.udf.showcase.core.BaseFragment
import com.udf.showcase.core.BaseRenderable
import com.udf.showcase.data.IApiService
import com.udf.showcase.data.IAppPrefs
import com.udf.showcase.inView
import com.udf.showcase.navigation.Navigator
import com.udf.showcase.noEffect
import dev.teapot.msg.Idle
import io.reactivex.Single
import javax.inject.Inject

class LoginFragment : BaseFragment<LoginState>(), LoginClickListener {

    @Inject lateinit var appPrefs: IAppPrefs
    @Inject lateinit var apiService: IApiService
    @Inject lateinit var navigator: Navigator

    override fun setupDI() {
        getActivityComponent()
            .inject(this)
    }

    override fun createRenderable(context: Context): BaseRenderable<LoginState> {
        val renderable = LoginRenderable(context)
        renderable.loginClickListener = this
        return renderable
    }

    override fun initialState(): LoginState = LoginState()

    override fun update(msg: Msg, state: LoginState): Update<LoginState> {
        return loginUpdate(msg, state)
    }

    override fun call(cmd: Cmd): Single<Msg> {
        return when (cmd) {
            is GetSavedUserCmd -> appPrefs.getUserSavedCredentials()
                .map { (login, pass) -> UserCredentialsLoadedMsg(login, pass) }
            is SaveUserCredentialsCmd -> appPrefs.saveUserSavedCredentials(cmd.login, cmd.pass)
                .map { UserCredentialsSavedMsg() }
            is LoginCmd -> apiService.login(cmd.login, cmd.pass)
                .map { logged -> LoginResponseMsg(logged) }
            is GoToMainCmd -> {
                inView {
                    navigator.goToMainScreen()
                }
            }
            else -> Single.just(Idle)
        }
    }

    override fun onLoginChanged(login: String) {
        program.accept(LoginInputMsg(login))
    }

    override fun onPasswordChanged(password: String) {
        program.accept(PassInputMsg(password))
    }

    override fun onLoginClicked() {
        program.accept(LoginClickMsg())
    }

    override fun onSaveLoginCheckClick(checked: Boolean) {
        program.accept(IsSaveCredentialsMsg(checked))
    }


}
