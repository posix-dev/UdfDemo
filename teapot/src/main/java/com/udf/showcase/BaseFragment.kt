package com.udf.showcase

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import butterknife.Unbinder
import com.udf.showcase.main.di.ActivityComponent
import com.udf.showcase.main.view.MainActivity
import io.reactivex.disposables.CompositeDisposable

abstract class BaseFragment : Fragment() {

    lateinit var unbinder: Unbinder
    var viewDisposables: CompositeDisposable = CompositeDisposable()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutRes(), container, false)
        unbinder = ButterKnife.bind(this, view)
        return view
    }

    abstract fun getLayoutRes(): Int

    override fun onDestroyView() {
        super.onDestroyView()
        if (!viewDisposables.isDisposed) {
            viewDisposables.dispose()
            viewDisposables = CompositeDisposable()
        }
        unbinder.unbind()
    }

    fun getActivityComponent(): ActivityComponent {
        return (activity as? MainActivity)?.activityComponent!!
    }
}