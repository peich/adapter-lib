package com.pch.adapter.paging.utils

import androidx.paging.PagingSource
import com.pch.adapter.model.PagingSourceData

class PagingManager {

    companion object {

        /**
         * 获取下个页码
         */
        @JvmStatic
        fun countNextPageKey(
            params: PagingSource.LoadParams<Int>,
            list: PagingSourceData<*>
        ): Int? {
            val key = params.key
            return if (key == null) {
                //如果当前key 是null，当前是第一页
                if (list.total != 0) {
                    //且列表总长度不是0，说明有数据
                    if (list.size >= list.total) {
                        null
                    } else {
                        2
                    }
                } else {
                    //说明当前没有数据
                    null
                }
            } else {
                //如果当前非第一页
                val listSize = (key - 1) * params.loadSize + list.size
                if (listSize < list.total) {
                    key + 1
                } else {
                    null
                }
            }
        }

        /**
         * 网络请求成功后，实例分页数据
         */
        @JvmStatic
        fun <V> getPagingSourceData(size: Int, total: Int, list: List<V>): PagingSourceData<V> {
            return PagingSourceData(
                size,
                total,
                list
            )
        }

        /**
         * 判断当前列表是否有数据
         * true:当前列表有数据
         * false：当前列表没有数据
         */
        @JvmStatic
        fun isListHasData(key: Int?, size: Int): Boolean {
            return !((key == null || key == 1) && size == 0)
        }

        /**
         * 获取当前加载列表
         */
        @JvmStatic
        fun getCurrentKey(key: Int?): Int {
            return key ?: 1
        }

    }
}