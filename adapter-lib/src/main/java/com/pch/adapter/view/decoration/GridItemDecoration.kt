package com.pch.adapter.view.decoration

import android.graphics.Rect
import com.pch.adapter.view.decoration.api.ProxyDecorationInterface


class GridItemDecoration(
    private val gridSpanCount: Int,
    private val itemSpacing: Float,
    private val leftSpacing: Float,
    private val topSpacing: Float,
    private val rightSpacing: Float,
    private val bottomSpacing: Float
) : ProxyDecorationInterface {

    val proxyDecoration: ProxyDecoration = ProxyDecoration(this)

    override fun setShowHeaderAndHasDataItemSpacing(
        itemCount: Int,
        childLayoutPosition: Int,
        outRect: Rect
    ) {
        val lastLineNumber =
            if (itemCount.minus(1) % gridSpanCount == 0) gridSpanCount else itemCount.minus(1) % gridSpanCount
        when {
            childLayoutPosition == 0 -> {
                proxyDecoration.portraitSpacing(outRect, 0, 0)
            }
            childLayoutPosition < gridSpanCount + 1 -> {
                //第一行
                proxyDecoration.portraitSpacing(
                    outRect,
                    topSpacing.toInt(),
                    (itemSpacing / 2).toInt()
                )
            }
            childLayoutPosition >= itemCount.minus(lastLineNumber) -> {
                //最后一行
                proxyDecoration.portraitSpacing(
                    outRect,
                    (itemSpacing / 2).toInt(),
                    if (bottomSpacing.toInt() > (itemSpacing / 2).toInt()) {
                        bottomSpacing.toInt()
                    }else{
                        (itemSpacing / 2).toInt()
                    }
                )
            }
            else -> {
                proxyDecoration.portraitSpacing(
                    outRect,
                    (itemSpacing / 2).toInt(),
                    (itemSpacing / 2).toInt()
                )
            }
        }

        when {
            childLayoutPosition == 0 -> {
                proxyDecoration.transverseSpacing(outRect, 0, 0)
            }
            (childLayoutPosition - 1) % gridSpanCount == 0 -> {
                //最左侧一列
                proxyDecoration.transverseSpacing(
                    outRect,
                    leftSpacing.toInt(),
                    (itemSpacing / 2).toInt()
                )
            }
            (childLayoutPosition - 1) % gridSpanCount == gridSpanCount - 1 -> {
                //最右侧一列
                proxyDecoration.transverseSpacing(
                    outRect,
                    (itemSpacing / 2).toInt(),
                    rightSpacing.toInt()
                )
            }
            else -> {
                //中间的列
                proxyDecoration.transverseSpacing(
                    outRect,
                    (itemSpacing / 2).toInt(),
                    (itemSpacing / 2).toInt()
                )
            }
        }
    }

    override fun setItemSpacing(itemCount: Int, childLayoutPosition: Int, outRect: Rect) {
        val lastLineNumber =
            if (itemCount % gridSpanCount == 0) gridSpanCount else itemCount % gridSpanCount
        when {
            childLayoutPosition < gridSpanCount -> {
                //第一行
                proxyDecoration.portraitSpacing(
                    outRect,
                    topSpacing.toInt(),
                    (itemSpacing / 2).toInt()
                )
            }
            childLayoutPosition >= itemCount.minus(lastLineNumber) -> {
                //最后一行
                proxyDecoration.portraitSpacing(
                    outRect,
                    (itemSpacing / 2).toInt(),
                    if (bottomSpacing.toInt() > (itemSpacing / 2).toInt()) {
                        bottomSpacing.toInt()
                    }else{
                        (itemSpacing / 2).toInt()
                    }
                )
            }
            else -> {
                proxyDecoration.portraitSpacing(
                    outRect,
                    (itemSpacing / 2).toInt(),
                    (itemSpacing / 2).toInt()
                )
            }
        }

        when {
            childLayoutPosition % gridSpanCount == 0 -> {
                //最左侧一列
                proxyDecoration.transverseSpacing(
                    outRect,
                    leftSpacing.toInt(),
                    (itemSpacing / 2).toInt()
                )
            }
            childLayoutPosition % gridSpanCount == gridSpanCount - 1 -> {
                //最右侧一列
                proxyDecoration.transverseSpacing(
                    outRect,
                    (itemSpacing / 2).toInt(),
                    rightSpacing.toInt()
                )
            }
            else -> {
                //中间的列
                proxyDecoration.transverseSpacing(
                    outRect,
                    (itemSpacing / 2).toInt(),
                    (itemSpacing / 2).toInt()
                )
            }
        }
    }

}