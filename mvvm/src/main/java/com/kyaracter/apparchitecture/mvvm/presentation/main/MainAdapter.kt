package com.kyaracter.apparchitecture.mvvm.presentation.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.kyaracter.apparchitecture.R
import com.kyaracter.apparchitecture.databinding.RepoItemBinding
import com.kyaracter.apparchitecture.mvvm.presentation.main.entity.RepoItem
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<RepoItem>() {
    override fun areItemsTheSame(p0: RepoItem, p1: RepoItem): Boolean {
        return p0 == p1
    }

    override fun areContentsTheSame(p0: RepoItem, p1: RepoItem): Boolean {
        return p0.name == p1.name && p0.description == p1.description
    }

}

class MainAdapter @Inject constructor(
    list: Observable<List<RepoItem>>,
    scheduler: Scheduler,
    private val listener: MainAdapterInteractionListener
) : ListAdapter<RepoItem, MainAdapter.RepoItemViewHolder>(
    DIFF_CALLBACK
) {

    init {
        list
            .observeOn(scheduler)
            .subscribeBy { setItems(it) }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepoItemViewHolder {
        return RepoItemViewHolder.create(
            LayoutInflater.from(
                parent.context
            ), parent, false
        )
    }

    override fun onBindViewHolder(holder: RepoItemViewHolder, position: Int) {
        holder.binding.item = this.getItem(position)
        holder.binding.listener = listener
    }

    private fun setItems(list: List<RepoItem>) {
        this.submitList(list)
    }

    class RepoItemViewHolder(val binding: RepoItemBinding) :
        androidx.recyclerview.widget.RecyclerView.ViewHolder(binding.root) {

        companion object {
            fun create(inflater: LayoutInflater, parent: ViewGroup, attachToParent: Boolean): RepoItemViewHolder {
                val binding = DataBindingUtil.inflate<RepoItemBinding>(
                    inflater,
                    R.layout.repo_item, parent, attachToParent
                )
                return RepoItemViewHolder(binding)
            }
        }
    }

    interface MainAdapterInteractionListener {
        fun show(repoItem: RepoItem)
    }
}