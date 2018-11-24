package com.kyaracter.apparchitecture.mvvm.presentation.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.kyaracter.apparchitecture.R
import com.kyaracter.apparchitecture.databinding.MainFragmentBinding
import com.kyaracter.apparchitecture.mvvm.presentation.main.entity.RepoItem
import dagger.android.support.DaggerFragment
import io.reactivex.Scheduler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

class MainFragment : DaggerFragment(),
    androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener,
    MainAdapter.MainAdapterInteractionListener,
    View.OnClickListener {

    private lateinit var binding: MainFragmentBinding

    @Inject
    internal lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var viewModel: MainViewModel

    @Inject
    lateinit var compositeDisposable: CompositeDisposable

    @field:[Inject Named("ioScheduler")]
    lateinit var ioScheduler: Scheduler

    @field:[Inject Named("uiScheduler")]
    lateinit var uiScheduler: Scheduler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.main_fragment, container, false)
        binding.viewModel = viewModel
        binding.swipe.setOnRefreshListener(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.list.apply {
            this.adapter = MainAdapter(
                viewModel.list,
                uiScheduler,
                this@MainFragment
            )
            this.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(
                this.context,
                RecyclerView.VERTICAL,
                false
            )
            this.addItemDecoration(
                androidx.recyclerview.widget.DividerItemDecoration(
                    this.context,
                    androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
                )
            )
        }
    }

    override fun onStart() {
        super.onStart()

        viewModel
            .get()
            .subscribeOn(ioScheduler)
            .observeOn(uiScheduler)
            .subscribeBy { /* No Action */ }
            .addTo(compositeDisposable)

        fetch()
    }

    private fun fetch() {
        viewModel
            .fetch()
            .delay(2, TimeUnit.SECONDS)// 分かりやすくするため、わざとdelay
            .doFinally {
                binding.swipe.isRefreshing = false
            }
            .subscribeOn(ioScheduler)
            .observeOn(uiScheduler)
            .subscribeBy(
                onError = {
                    Timber.e(it)
                }
            )
            .addTo(compositeDisposable)
    }

    override fun onRefresh() {
        fetch()
    }

    override fun show(repoItem: RepoItem) {
        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(repoItem.url)))
    }

    override fun onClick(v: View?) {
        if (v?.id == R.id.error) {
            fetch()
        }
    }

    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }

    companion object {
        fun newInstance() = MainFragment()
    }
}
