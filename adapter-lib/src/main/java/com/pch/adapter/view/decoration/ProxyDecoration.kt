package com.pch.adapter.view.decoration

import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pch.adapter.base.BaseListAdapter
import com.pch.adapter.base.paging.BasePagingAdapter
import com.pch.adapter.view.decoration.api.ProxyDecorationInterface

class ProxyDecoration(private val itemDecoration: ProxyDecorationInterface) :
    RecyclerView.ItemDecoration() {

    override fun getItemOffsets(
        outRect: Rect,
        view: View,
        parent: RecyclerView,
        state: RecyclerView.State
    ) {
        super.getItemOffsets(outRect, view, parent, state)
        parent.adapter?.let {
            val childLayoutPosition = parent.getChildLayoutPosition(view)
            when (it) {
                is ConcatAdapter -> {
                    //paging3  网格布局 间隔
                    it.adapters.forEach { adapter ->
                        if (adapter is BasePagingAdapter<*, *>) {
                            if (adapter.headerAdapter != null) {
                                //头布局显示
                                if (adapter.isHasData) {
                                    //有数据
                                    itemDecoration.setShowHeaderAndHasDataItemSpacing(
                                        adapter.itemCount,
                                        childLayoutPosition,
                                        outRect
                                    )
                                } else {
                                    //没有数据
                                    setItemNotSpacing(outRect)
                                }
                            } else {
                                //没有头布局
                                if (adapter.isHasData) {
                                    //有数据
                                    itemDecoration.setItemSpacing(
                                        adapter.itemCount,
                                        childLayoutPosition,
                                        outRect
                                    )
                                } else {
                                    //没有数据
                                    setItemNotSpacing(outRect)
                                }
                            }
                        } else if (adapter is BaseListAdapter<*, *>) {
                            if (adapter.headerAdapter != null) {
                                //头布局显示
                                if (adapter.isHasData) {
                                    //有数据
                                    itemDecoration.setShowHeaderAndHasDataItemSpacing(
                                        adapter.itemCount,
                                        childLayoutPosition,
                                        outRect
                                    )
                                } else {
                                    //没有数据
                                    setItemNotSpacing(outRect)
                                }
                            } else {
                                //没有头布局
                                if (adapter.isHasData) {
                                    //有数据
                                    itemDecoration.setItemSpacing(
                                        adapter.itemCount,
                                        childLayoutPosition,
                                        outRect
                                    )
                                } else {
                                    //没有数据
                                    setItemNotSpacing(outRect)
                                }
                            }
                        }
                    }
                }
                else -> {
                    itemDecoration.setItemSpacing(it.itemCount, childLayoutPosition, outRect)
                }
            }
        }
    }

    fun setItemNotSpacing(outRect: Rect) {
        outRect.top = 0
        outRect.bottom = 0
        outRect.left = 0
        outRect.right = 0
    }

    fun transverseSpacing(outRect: Rect, left: Int, right: Int) {
        outRect.left = left
        outRect.right = right
    }

    fun portraitSpacing(outRect: Rect, top: Int, bottom: Int) {
        outRect.top = top
        outRect.bottom = bottom
    }

}