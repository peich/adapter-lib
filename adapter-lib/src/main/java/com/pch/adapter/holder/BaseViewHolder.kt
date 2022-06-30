package com.pch.adapter.holder

import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView

open class BaseViewHolder<B : ViewDataBinding>(view: View) : RecyclerView.ViewHolder(view)