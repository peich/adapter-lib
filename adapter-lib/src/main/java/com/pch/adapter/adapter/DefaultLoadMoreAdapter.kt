package com.pch.adapter.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.LoadState
import com.pch.adapter.base.paging.BasePagingAdapter
import com.pch.adapter.base.paging.BasePagingLoadMoreAdapter
import com.pch.adapter.databinding.DefaultLoadMoreViewBinding
import com.pch.adapter.holder.paging.LoadStateViewHolder

class DefaultLoadMoreAdapter(private val pagingAdapter: BasePagingAdapter<*, *>, val size: Int) :
    BasePagingLoadMoreAdapter<DefaultLoadMoreViewBinding>() {

    override fun initViewDataBinding(
        parent: ViewGroup,
        loadState: LoadState
    ): DefaultLoadMoreViewBinding {
        return DefaultLoadMoreViewBinding.inflate(LayoutInflater.from(mContext), parent, false)
    }

    override fun convert(
        holder: LoadStateViewHolder<DefaultLoadMoreViewBinding>,
        loadState: LoadState
    ) {
        when (loadState) {
            is LoadState.NotLoading -> {
                //没有更多
                holder.binding.clLoading.visibility = View.GONE
                if (pagingAdapter.itemCount >= size) {
                    holder.binding.tvEnd.visibility = View.VISIBLE
                } else {
                    holder.binding.tvEnd.visibility = View.GONE
                }
            }
            is LoadState.Error -> {
                //加载失败
                holder.binding.clLoading.visibility = View.GONE
                holder.binding.tvEnd.visibility = View.GONE
            }
            is LoadState.Loading -> {
                //加载中
                holder.binding.clLoading.visibility = View.VISIBLE
                holder.binding.tvEnd.visibility = View.GONE
            }
        }
    }
}