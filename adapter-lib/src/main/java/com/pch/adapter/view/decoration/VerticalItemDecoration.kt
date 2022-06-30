package com.pch.adapter.view.decoration

import android.graphics.Rect
import com.pch.adapter.view.decoration.api.ProxyDecorationInterface

class VerticalItemDecoration(
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
                proxyDecoration.portraitSpacing(outRect, topSpacing.toInt(), (itemSpacing / 2).toInt())
                proxyDecoration.transverseSpacing(outRect, leftSpacing.toInt(), rightSpacing.toInt())
            }
            itemCount.minus(1) -> {
                proxyDecoration.portraitSpacing(outRect, (itemSpacing / 2).toInt(), bottomSpacing.toInt())
                proxyDecoration.transverseSpacing(outRect, leftSpacing.toInt(), rightSpacing.toInt())
            }
            else -> {
                proxyDecoration.portraitSpacing(outRect, (itemSpacing / 2).toInt(), (itemSpacing / 2).toInt())
                proxyDecoration.transverseSpacing(outRect, leftSpacing.toInt(), rightSpacing.toInt())
            }
        }
    }

    /**
     * 设置item间距
     */
    override fun setItemSpacing(itemCount: Int, childLayoutPosition: Int, outRect: Rect) {
        when (childLayoutPosition) {
            0 -> {
                proxyDecoration.portraitSpacing(outRect, topSpacing.toInt(), (itemSpacing / 2).toInt())
            }
            itemCount.minus(1) -> {
                proxyDecoration.portraitSpacing(outRect, (itemSpacing / 2).toInt(), bottomSpacing.toInt())
            }
            else -> {
                proxyDecoration.portraitSpacing(outRect, (itemSpacing / 2).toInt(), (itemSpacing / 2).toInt())
            }
        }
        proxyDecoration.transverseSpacing(outRect, leftSpacing.toInt(), rightSpacing.toInt())
    }

}