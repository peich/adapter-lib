package com.pch.adapter.view.decoration.api

import android.graphics.Rect

interface ProxyDecorationInterface {

    fun setShowHeaderAndHasDataItemSpacing(itemCount: Int, childLayoutPosition: Int, outRect: Rect)

    fun setItemSpacing(itemCount: Int, childLayoutPosition: Int, outRect: Rect)
}