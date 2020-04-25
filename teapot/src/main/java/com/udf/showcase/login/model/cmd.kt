package com.udf.showcase.login.model

import dev.teapot.cmd.Cmd

object GetSavedUserCmd : Cmd()
data class SaveUserCredentialsCmd(val login: String, val pass: String) : Cmd()
data class LoginCmd(val login: String, val pass: String) : Cmd()
object GoToMainCmd : Cmd()