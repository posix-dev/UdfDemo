package com.udf.showcase.navigation

import org.eclipse.egit.github.core.Repository

interface Navigator {
    fun goToMainScreen()
    fun goToRepo(repository: Repository)
}