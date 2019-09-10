package com.udf.showcase.repolist

import com.factorymarket.rxelm.cmd.Cmd
import com.factorymarket.rxelm.contract.State
import com.factorymarket.rxelm.msg.Msg
import org.eclipse.egit.github.core.Repository

data class RepoListState(
    val isLoading: Boolean = true,
    val userName: String,
    val reposList: List<Repository> = listOf()
) : State()


data class LoadReposCmd(val userName: String) : Cmd()

data class ReposLoadedMsg(val reposList: List<Repository>) : Msg()
object CancelMsg: Msg()
object RefreshMsg: Msg()