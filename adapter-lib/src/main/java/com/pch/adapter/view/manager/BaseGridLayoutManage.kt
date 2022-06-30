package com.pch.adapter.view.manager

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.pch.adapter.base.BaseEmptyAdapter
import com.pch.adapter.base.BaseHeaderAdapter
import com.pch.adapter.base.paging.BasePagingLoadMoreAdapter

class BaseGridLayoutManage : GridLayoutManager {

    constructor(
        context: Context?,
        attrs: AttributeSet?,
        defStyleAttr: Int,
        defStyleRes: Int
    ) : super(context, attrs, defStyleAttr, defStyleRes)

    constructor(context: Context?, spanCount: Int) : super(context, spanCount)
    constructor(
        context: Context?,
        spanCount: Int,
        orientation: Int,
        reverseLayout: Boolean
    ) : super(context, spanCount, orientation, reverseLayout)


    override fun onAttachedToWindow(view: RecyclerView?) {
        super.onAttachedToWindow(view)

        view?.let {
            spanSizeLookup = MySpanSizeLookup(it)
        }

    }

    inner class MySpanSizeLookup(val view: RecyclerView) : SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            val itemViewType = view.adapter?.getItemViewType(position)
            if (itemViewType == BaseEmptyAdapter.Empty_TYPE ||
                itemViewType == BaseHeaderAdapter.HEADER_TYPE ||
                itemViewType == BasePagingLoadMoreAdapter.LOAD_MORE_TYPE
            ) {
                return spanCount
            }
            return 1
        }
    }

}