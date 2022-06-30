package com.pch.adapter.base

import android.content.Context
import android.view.View
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.ConcatAdapter
import androidx.recyclerview.widget.RecyclerView
import com.pch.adapter.adapter.DefaultEmptyAdapter
import com.pch.adapter.holder.ItemViewHolder

abstract class BaseListAdapter<D : Any, B : ViewDataBinding>(data: MutableList<D>) :
    RecyclerView.Adapter<ItemViewHolder<B>>() {

    private val adapterBuild = ConcatAdapter.Config.Builder().setIsolateViewTypes(false).build()

    //上下文
    protected lateinit var context: Context

    //当前所在的RecyclerView
    protected var mRecyclerView: RecyclerView? = null

    //当前列表数据
    var dataList: MutableList<D> = data

    //头布局、空布局
    var headerAdapter: BaseHeaderAdapter<*, *>? = null
    var emptyAdapter: BaseEmptyAdapter<*>? = null

    //是否有数据，默认有数据
    private var hasDataEnable = true
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

    override fun getItemCount(): Int {
        return dataList.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder<B> {
        return ItemViewHolder(initViewDataBinding(parent, viewType))
    }

    override fun onBindViewHolder(holder: ItemViewHolder<B>, position: Int) {
        val item = dataList[position]

        holder.itemView.setOnClickListener {
            onItemClickListener?.onClick(holder.itemView, position, item)
        }

        convert(holder, position, item)
    }

    //布局设置
    protected abstract fun initViewDataBinding(parent: ViewGroup, viewType: Int): B

    //item设置在这里
    protected abstract fun convert(holder: ItemViewHolder<B>, position: Int, itemData: D?)

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
     * 设置给RecyclerView 的Adapter
     */
    fun getRecyclerViewAdapter(): ConcatAdapter {
        return if (hasDataEnable) {
            //有数据
            if (headerAdapter != null) {
                ConcatAdapter(adapterBuild, headerAdapter, this)
            } else {
                ConcatAdapter(adapterBuild, this)
            }
        } else {
            //没有数据
            if (headerAdapter != null) {
                if (headerWithEmptyEnable) {
                    if (emptyAdapter != null) {
                        ConcatAdapter(adapterBuild, headerAdapter, emptyAdapter)
                    } else {
                        ConcatAdapter(adapterBuild, headerAdapter, this)
                    }
                } else {
                    if (emptyAdapter != null) {
                        ConcatAdapter(adapterBuild, emptyAdapter)
                    } else {
                        ConcatAdapter(adapterBuild, this)
                    }
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

    //item 点击
    var onItemClickListener: OnItemClickListener<D>? = null

    interface OnItemClickListener<D> {
        fun onClick(itemView: View, position: Int, itemData: D)
    }


    /**
     * 加载更多数据
     *
     * @param data
     */
    open fun addNewData(data: MutableList<D>) {
        val startIndex = dataList.size
        this.dataList.addAll(data)
        val itemCount = (dataList.size - startIndex).coerceAtLeast(0)
        notifyItemRangeInserted(startIndex, itemCount)
    }

    /**
     * 清空数据
     */
    open fun removeAllData() {
        notifyItemRangeRemoved(0, dataList.size)
        this.dataList.clear()
    }

    /**
     * 通过下标获取到item的Data
     */
    open fun getDataForPos(pos: Int): D {
        return dataList[pos]
    }
}