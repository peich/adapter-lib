package com.pch.adapter.base.paging

import androidx.databinding.ViewDataBinding
import androidx.paging.LoadState
import androidx.recyclerview.widget.DiffUtil
import com.pch.adapter.callback.AdapterDiffUtil
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.scwang.smart.refresh.layout.api.RefreshLayout

/**
 * 可以设置刷新控件的Paging adapter
 */
abstract class RefreshPagingAdapter<D : Any, B : ViewDataBinding>
@JvmOverloads constructor(
    diffCallback: DiffUtil.ItemCallback<D> = AdapterDiffUtil()
) : BasePagingAdapter<D, B>(diffCallback) {

    private var mSwipeRefreshLayout: SmartRefreshLayout? = null

    //没有数据时，刷新是否停用，默认停用
    var notHasDataRefreshDisable = true
    override var isHasData: Boolean
        get() {
            return hasDataEnable
        }
        set(value) {
            if (value != hasDataEnable) {
                hasDataEnable = value
                //设置加载刷新是否使用
                if (hasDataEnable) {
                    mSwipeRefreshLayout?.isEnabled = true
                }else{
                    mSwipeRefreshLayout?.isEnabled = !notHasDataRefreshDisable
                }
                mRecyclerView?.adapter = getRecyclerViewAdapter()
            }
        }

    override fun addSateFooterAdapter(
        size: Int,
        footer: BasePagingLoadMoreAdapter<*>
    ) {
        addLoadStateListener { loadStates ->
            footer.loadState = loadStates.append

            //设置加载中时，刷新不可用。
            if (loadStates.append is LoadState.Loading) {
                mSwipeRefreshLayout?.isEnabled = false
            } else {
                if (hasDataEnable) {
                    mSwipeRefreshLayout?.isEnabled = true
                }else{
                    mSwipeRefreshLayout?.isEnabled = !notHasDataRefreshDisable
                }
            }

            //设置刷新结束，关闭刷新控件
            if (loadStates.source.refresh is LoadState.Loading) {
                mSwipeRefreshLayout?.finishRefresh()
            }
        }
        footerAdapter = footer
    }

    /**
     * 设置刷新控件
     */
    fun setRefreshView(swipeRefreshLayout: SmartRefreshLayout) {
        this.mSwipeRefreshLayout = swipeRefreshLayout
        if (hasDataEnable) {
            mSwipeRefreshLayout?.isEnabled = true
        }else{
            mSwipeRefreshLayout?.isEnabled = !notHasDataRefreshDisable
        }
        mSwipeRefreshLayout?.setOnRefreshListener {
            refresh()
            //刷新中的其他操作
            refreshClickListener?.onClick(it)
        }
    }

    var refreshClickListener: RefreshClickListener? = null

    interface RefreshClickListener {
        fun onClick(refreshLayout: RefreshLayout)
    }
}