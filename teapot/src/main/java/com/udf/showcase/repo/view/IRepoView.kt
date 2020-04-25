package com.udf.showcase.repo.view

import org.eclipse.egit.github.core.Repository

interface IRepoView {
    fun showLoading(loading: Boolean)
    fun showRepo(repo: Repository)
}