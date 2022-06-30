package com.pch.adapter.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.pch.adapter.R
import com.pch.adapter.base.BaseEmptyAdapter
import com.pch.adapter.databinding.DefaultWidgetEmptyBinding
import com.pch.adapter.holder.paging.EmptyPagingViewHolder

class DefaultEmptyAdapter @JvmOverloads constructor(
    val src: Int = R.mipmap.not_data,
    val text: String = "暂无数据"
) : BaseEmptyAdapter<DefaultWidgetEmptyBinding>() {

    override fun initViewDataBinding(parent: ViewGroup, viewType: Int): DefaultWidgetEmptyBinding {
        return DefaultWidgetEmptyBinding.inflate(LayoutInflater.from(mContext), parent, false)
    }

    override fun convert(holder: EmptyPagingViewHolder<DefaultWidgetEmptyBinding>) {
        holder.binding.imageView.setImageResource(src)
        holder.binding.textView.text = text
    }
}