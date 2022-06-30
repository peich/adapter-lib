package com.pch.adapter.view.decoration

import android.graphics.Rect
import com.pch.adapter.view.decoration.api.ProxyDecorationInterface

class HorizontalItemDecoration(
        private val itemSpacing: Float,
        private val leftSpacing: Float,
        private val topSpacing: Float,
        private val rightSpacing: Float,
        private val bottomSpacing: Float
) : ProxyDecorationInterface {

    val proxyDecoration: ProxyDecoration = ProxyDecoration(this)

    /**
     * 设置头显示，且有数据的item间距
     */
    override fun setShowHeaderAndHasDataItemSpacing(itemCount: Int, childLayoutPosition: Int, outRect: Rect) {
        when (childLayoutPosition) {
            0 -> {
                proxyDecoration.setItemNotSpacing(outRect)
            }
            1 -> {
                proxyDecoration.transverseSpacing(outRect, leftSpacing.toInt(), (itemSpacing / 2).toInt())
                proxyDecoration.portraitSpacing(outRect, topSpacing.toInt(), bottomSpacing.toInt())
            }
            itemCount.minus(1) -> {
                proxyDecoration.transverseSpacing(outRect, (itemSpacing / 2).toInt(), rightSpacing.toInt())
                proxyDecoration.portraitSpacing(outRect, topSpacing.toInt(), bottomSpacing.toInt())
            }
            else -> {
                proxyDecoration.transverseSpacing(outRect, (itemSpacing / 2).toInt(), (itemSpacing / 2).toInt())
                proxyDecoration.portraitSpacing(outRect, topSpacing.toInt(), bottomSpacing.toInt())
            }
        }
    }

    override fun setItemSpacing(itemCount: Int, childLayoutPosition: Int, outRect: Rect) {
        when (childLayoutPosition) {
            0 -> {
                proxyDecoration.transverseSpacing(outRect, leftSpacing.toInt(), (itemSpacing / 2).toInt())
            }
            itemCount.minus(1) -> {
                proxyDecoration.transverseSpacing(outRect, (itemSpacing / 2).toInt(), rightSpacing.toInt())
            }
            else -> {
                proxyDecoration.transverseSpacing(outRect, (itemSpacing / 2).toInt(), (itemSpacing / 2).toInt())
            }
        }
        proxyDecoration.portraitSpacing(outRect, topSpacing.toInt(), bottomSpacing.toInt())
    }
}