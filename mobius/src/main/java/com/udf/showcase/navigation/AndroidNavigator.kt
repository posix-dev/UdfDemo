package com.udf.showcase.navigation

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import com.udf.showcase.MAIN_TAG
import com.udf.showcase.R
import com.udf.showcase.REPO_TAG
import com.udf.showcase.main.view.MainFragment
import com.udf.showcase.repo.view.RepoFragment
import com.udf.showcase.repo.view.RepoFragment.Companion.REPO_ID_KEY
import org.eclipse.egit.github.core.Repository
import org.eclipse.egit.github.core.RepositoryId


class AndroidNavigator(private val activity: FragmentActivity) : Navigator {

    override fun goToRepo(repository: Repository) {
        val fragment = RepoFragment()
        val bundle = Bundle()
        bundle.putString(REPO_ID_KEY, repository.htmlUrl)
        fragment.arguments = bundle
        activity
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment, fragment, REPO_TAG)
                .addToBackStack(null)
                .commit()
    }

    override fun goToMainScreen() {
        activity
                .supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment, MainFragment(), MAIN_TAG)
                .commitNow()
    }

}