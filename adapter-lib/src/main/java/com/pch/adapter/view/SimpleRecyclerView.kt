package com.pch.adapter.view

import android.content.Context
import android.util.AttributeSet
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import com.pch.adapter.R
import com.pch.adapter.view.decoration.GridItemDecoration
import com.pch.adapter.view.decoration.HorizontalItemDecoration
import com.pch.adapter.view.decoration.VerticalItemDecoration
import com.pch.adapter.view.manager.BaseGridLayoutManage

/**
 * 初始化添加 行间距和 列表类型的RecyclerView
 * 如果无法满足需求，建议使用RecyclerView
 * 只有在xml中设置有效，不支持在代码中修改
 * 如果想修改可以使用RecyclerView原始设置
 *
 * bug：ConstraintLayout：如果使用了 固定大小（非wrap_content），adapter中会onBindViewHolder会一直掉用
 * 这是ConstraintLayout  的bug，而非自己View的bug。如果遇到需要注意
 *
 * 网格布局行间距 待优化
 */
class SimpleRecyclerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RecyclerView(context, attrs, defStyleAttr) {

    private val adapterType: Int
    private val itemSpacing: Float
    private val leftSpacing: Float
    private val rightSpacing: Float
    private val topSpacing: Float
    private val bottomSpacing: Float
    private val gridSpanCount: Int

    init {
        val array = context.obtainStyledAttributes(attrs, R.styleable.SimpleRecyclerView)

        //列表类型，默认是纵向列表
        adapterType = array.getInt(R.styleable.SimpleRecyclerView_adapter_type, 0)

        //item行间距，如果不设置是0
        itemSpacing = array.getDimension(R.styleable.SimpleRecyclerView_adapter_item_spacing, 0f)

        //左侧行间距，不设置是0
        leftSpacing = array.getDimension(R.styleable.SimpleRecyclerView_adapter_left_spacing, 0f)
        //右侧行间距，不设置是0
        rightSpacing = array.getDimension(R.styleable.SimpleRecyclerView_adapter_right_spacing, 0f)
        //上侧行间距，不设置是0
        topSpacing = array.getDimension(R.styleable.SimpleRecyclerView_adapter_top_spacing, 0f)
        //下侧行间距，不设置是0
        bottomSpacing =
            array.getDimension(R.styleable.SimpleRecyclerView_adapter_bottom_spacing, 0f)

        //网格布局列数，如果没有设置默认一列
        gridSpanCount = array.getInt(R.styleable.SimpleRecyclerView_grid_span_count, 1)

        array.recycle()

        //取消刷新动画，让其刷新不闪烁
        if (itemAnimator is SimpleItemAnimator) {
            (itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        }

        //设置列表类型和行间距
        setAdapterType()
    }

    private fun setAdapterType() {
        when (adapterType) {
            0 -> {
                //纵向列表
                layoutManager = LinearLayoutManager(context)

                //设置列表间距
                addItemDecoration(
                    VerticalItemDecoration(
                        itemSpacing,
                        leftSpacing,
                        topSpacing,
                        rightSpacing,
                        bottomSpacing
                    ).proxyDecoration
                )
            }
            1 -> {
                //横向列表
                layoutManager = LinearLayoutManager(context, HORIZONTAL, false)

                //设置列表间距
                addItemDecoration(
                    HorizontalItemDecoration(
                        itemSpacing,
                        leftSpacing,
                        topSpacing,
                        rightSpacing,
                        bottomSpacing
                    ).proxyDecoration
                )
            }
            2 -> {
                //网格列表
                layoutManager = BaseGridLayoutManage(context, gridSpanCount)

                //设置列表间距
                addItemDecoration(
                    GridItemDecoration(
                        gridSpanCount,
                        itemSpacing,
                        leftSpacing,
                        topSpacing,
                        rightSpacing,
                        bottomSpacing
                    ).proxyDecoration
                )
            }
        }
    }

}