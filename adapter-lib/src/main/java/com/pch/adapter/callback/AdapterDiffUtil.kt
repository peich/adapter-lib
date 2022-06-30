package com.pch.adapter.callback

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil

class AdapterDiffUtil<D : Any> : DiffUtil.ItemCallback<D>() {

    override fun areItemsTheSame(oldItem: D, newItem: D): Boolean {
        return false
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: D, newItem: D): Boolean {
        return oldItem == newItem
    }
}