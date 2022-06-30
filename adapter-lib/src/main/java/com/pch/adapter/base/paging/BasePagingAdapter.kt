package com.pch.adapter.base.paging

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pch.adapter.adapter.DefaultEmptyAdapter
import com.pch.adapter.adapter.DefaultLoadMoreAdapter
import com.pch.adapter.base.BaseEmptyAdapter
import com.pch.adapter.base.BaseHeaderAdapter
import com.pch.adapter.callback.AdapterDiffUtil
import com.pch.adapter.holder.ItemViewHolder
import com.pch.adapter.paging.PagingHelper

/**
 * 底部加载更多Adapter
 */
abstract class BasePagingAdapter<D : Any, B : ViewDataBinding>
@JvmOverloads constructor(
    diffCallback: DiffUtil.ItemCallback<D> = AdapterDiffUtil()
) : PagingDataAdapter<D, ItemViewHolder<B>>(diffCallback) {

    private val adapterBuild = ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build()

    //上下文
    protected lateinit var context: Context

    //当前所在的RecyclerView
    internal var mRecyclerView: RecyclerView? = null

    //头布局、空布局、加载更多布局
    var headerAdapter: BaseHeaderAdapter<*, *>? = null
    var emptyAdapter: BaseEmptyAdapter<*>? = null
    var footerAdapter: BasePagingLoadMoreAdapter<*>? = null

    //是否有数据，默认有数据
    protected var hasDataEnable = true
    open var isHasData: Boolean
        get() {
            return hasDataEnable
        }
        set(value) {
            if (value != hasDataEnable) {
                hasDataEnable = value
                mRecyclerView?.adapter = getRecyclerViewAdapter()
            }
        }

    //当列表没有数据时，是否显示 Header，默认显示：true。
    var headerWithEmptyEnable = true

    companion object {
        const val LIST_TYPE = 0x10000222
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
        super.onAttachedToRecyclerView(recyclerView)
        this.context = recyclerView.context
        mRecyclerView = recyclerView
    }

    override fun getItemViewType(position: Int): Int {
        return LIST_TYPE
    }

    protected abstract fun initViewDataBinding(parent: ViewGroup, viewType: Int): B

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder<B> {
        return ItemViewHolder(initViewDataBinding(parent, viewType))
    }

    override fun onBindViewHolder(holder: ItemViewHolder<B>, position: Int) {
        val item = getItem(position)
        holder.itemView.setOnClickListener {
            if (item != null) {
                onItemClickListener?.onClick(holder.itemView, position, item)
            }

        }
        convert(holder, position, item)
    }

    /**
     * 添加头布局Adapter
     */
    fun addHeaderAdapter(
        header: BaseHeaderAdapter<*, *>
    ) {
        headerAdapter = header
    }

    /**
     * 添加空布局Adapter
     * 不传属性使用默认类型
     */
    @JvmOverloads
    fun addEmptyAdapter(
        empty: BaseEmptyAdapter<*> = DefaultEmptyAdapter()
    ) {
        emptyAdapter = empty
    }

    /**
     * 添加底部加载更多Adapter
     * 自带的withLoadStateFooter，设置类型index无效
     * 无法适配Grid类型
     * 不传属性使用默认类型
     * size:列表个数大于等于这个数字，没有更多才会显示，如果不设置默认是1
     */
    @JvmOverloads
    open fun addSateFooterAdapter(
        size: Int = PagingHelper.getDefault().pageSize,
        footer: BasePagingLoadMoreAdapter<*> = DefaultLoadMoreAdapter(this, size)
    ) {
        addLoadStateListener { loadStates ->
            footer.loadState = loadStates.append
        }

        footerAdapter = footer
    }

    /**
     * 设置给RecyclerView 的Adapter
     */
    fun getRecyclerViewAdapter(): ConcatAdapter {
        footerAdapter?.isEmptyData = !hasDataEnable
        return if (hasDataEnable) {
            //有数据
            if (headerAdapter != null) {
                if (footerAdapter != null) {
                    ConcatAdapter(adapterBuild, headerAdapter, this, footerAdapter)
                } else {
                    ConcatAdapter(adapterBuild, headerAdapter, this)
                }
            } else {
                if (footerAdapter != null) {
                    ConcatAdapter(adapterBuild, this, footerAdapter)
                } else {
                    ConcatAdapter(adapterBuild, this)
                }
            }
        } else {
            if (headerAdapter != null) {
                if (headerWithEmptyEnable) {
                    if (footerAdapter != null) {
                        if (emptyAdapter != null) {
                            ConcatAdapter(adapterBuild, headerAdapter, emptyAdapter, footerAdapter)
                        } else {
                            ConcatAdapter(adapterBuild, headerAdapter, this, footerAdapter)
                        }
                    } else {
                        if (emptyAdapter != null) {
                            ConcatAdapter(adapterBuild, headerAdapter, emptyAdapter)
                        } else {
                            ConcatAdapter(adapterBuild, headerAdapter, this)
                        }
                    }
                } else {
                    if (footerAdapter != null) {
                        if (emptyAdapter != null) {
                            ConcatAdapter(adapterBuild, emptyAdapter, footerAdapter)
                        } else {
                            ConcatAdapter(adapterBuild, this, footerAdapter)
                        }
                    } else {
                        if (emptyAdapter != null) {
                            ConcatAdapter(adapterBuild, emptyAdapter)
                        } else {
                            ConcatAdapter(adapterBuild, this)
                        }
                    }
                }
            } else {
                if (footerAdapter != null) {
                    if (emptyAdapter != null) {
                        ConcatAdapter(adapterBuild, emptyAdapter, footerAdapter)
                    } else {
                        ConcatAdapter(adapterBuild, this, footerAdapter)
                    }
                } else {
                    if (emptyAdapter != null) {
                        ConcatAdapter(adapterBuild, emptyAdapter)
                    } else {
                        ConcatAdapter(adapterBuild, this)
                    }
                }
            }
        }
    }

    //item设置在这里
    protected abstract fun convert(holder: ItemViewHolder<B>, position: Int, itemData: D?)

    var onItemClickListener: OnItemClickListener<D>? = null

    interface OnItemClickListener<D> {
        fun onClick(itemView: View, position: Int, itemData: D)
    }
}