package com.pch.adapter.base

import android.content.Context
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.pch.adapter.holder.paging.HeadPagingViewHolder

/**
 * 列表头的adapter
 * 头部只有一个View，
 * 暂时不支持头部出现和列表方向一致的列表，如果必须要这样做，自定义
 */
abstract class BaseHeaderAdapter<D, B : ViewDataBinding>(val headerData: D) :
    RecyclerView.Adapter<HeadPagingViewHolder<B>>() {

    //上下文
    protected lateinit var context: Context

    //当前所在的RecyclerView
    internal var mRecyclerView: RecyclerView? = null

    companion object {
        const val HEADER_TYPE = 0x10000111
    }

    protected abstract fun initViewDataBinding(parent: ViewGroup, viewType: Int): B

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HeadPagingViewHolder<B> {
        return HeadPagingViewHolder(initViewDataBinding(parent, viewType))
    }

    override fun onBindViewHolder(holder: HeadPagingViewHolder<B>, position: Int) {
        convert(holder, headerData)
    }

    override fun getItemCount(): Int {
        return 1
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.context = recyclerView.context
        this.mRecyclerView = recyclerView
    }

    override fun getItemViewType(position: Int): Int {
        return HEADER_TYPE
    }

    protected abstract fun convert(holder: HeadPagingViewHolder<B>, headerData: D)
}