package com.pch.adapter.base

import android.content.Context
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.pch.adapter.holder.paging.EmptyPagingViewHolder

/**
 * 空布局
 */
abstract class BaseEmptyAdapter<B : ViewDataBinding> :
    RecyclerView.Adapter<EmptyPagingViewHolder<B>>() {

    //上下文
    protected lateinit var mContext: Context

    //当前所在的RecyclerView
    internal var mRecyclerView: RecyclerView? = null


    companion object {
        const val Empty_TYPE = 0x10000333
    }

    protected abstract fun initViewDataBinding(parent: ViewGroup, viewType: Int): B

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmptyPagingViewHolder<B> {
        return EmptyPagingViewHolder(initViewDataBinding(parent, viewType))
    }

    override fun onBindViewHolder(holder: EmptyPagingViewHolder<B>, position: Int) {
        convert(holder)
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.mContext = recyclerView.context
        this.mRecyclerView = recyclerView
    }


    override fun getItemViewType(position: Int): Int {
        return Empty_TYPE
    }

    //item设置在这里
    protected abstract fun convert(holder: EmptyPagingViewHolder<B>)
}