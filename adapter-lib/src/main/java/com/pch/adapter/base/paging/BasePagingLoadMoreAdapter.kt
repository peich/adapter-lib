package com.pch.adapter.base.paging

import android.content.Context
import android.view.ViewGroup
import androidx.paging.LoadState
import androidx.paging.LoadStateAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding
import com.pch.adapter.holder.paging.LoadStateViewHolder

abstract class BasePagingLoadMoreAdapter<B : ViewBinding> :
    LoadStateAdapter<LoadStateViewHolder<B>>() {

    protected lateinit var mContext: Context

    companion object {
        const val LOAD_MORE_TYPE = 0x10000444
    }

    //当前是否空数据，默认非空
    var isEmptyData = false

    override fun onCreateViewHolder(
        parent: ViewGroup,
        loadState: LoadState
    ): LoadStateViewHolder<B> {
        return LoadStateViewHolder(initViewDataBinding(parent, loadState))
    }

    override fun onBindViewHolder(holder: LoadStateViewHolder<B>, loadState: LoadState) {
        convert(holder, loadState)
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.mContext = recyclerView.context
    }

    /**
     * 设置底部没有更多显示
     */
    override fun displayLoadStateAsItem(loadState: LoadState): Boolean {
        return if (isEmptyData)
            false
        else (super.displayLoadStateAsItem(loadState) || (loadState is LoadState.NotLoading && loadState.endOfPaginationReached))
    }

    /**
     * 设置该条目类型
     */
    override fun getStateViewType(loadState: LoadState): Int {
        return LOAD_MORE_TYPE
    }

    protected abstract fun initViewDataBinding(parent: ViewGroup, loadState: LoadState): B

    protected abstract fun convert(holder: LoadStateViewHolder<B>, loadState: LoadState)
}