package com.pch.adapter.paging;

import androidx.paging.Pager;
import androidx.paging.PagingConfig;
import androidx.paging.PagingData;
import androidx.paging.rxjava2.PagingRx;

import io.reactivex.Flowable;

public class PagingHelper {

    private static PagingHelper instance;
    /***分页大小*/
    private int pageSize = 20;
    /***预加载距离*/
    private int prefetchDistance = 1;

    private PagingHelper() {
    }

    /**
     * 单例
     *
     * @return ：实例
     */
    public static PagingHelper getDefault() {
        if (instance == null) {
            synchronized (PagingHelper.class) {
                if (instance == null) {
                    instance = new PagingHelper();
                }
            }
        }
        return instance;
    }

    /**
     * 全局设置，分页大小，和预加载距离
     *
     * @param pageSize         ：全局分页大小
     * @param prefetchDistance ：全局预加载距离
     */
    public void init(int pageSize, int prefetchDistance) {
        this.pageSize = pageSize;
        this.prefetchDistance = prefetchDistance;
    }

    public <T, V> Flowable<PagingData<V>> getFlowable(SimplePagingSource.PagingSourceRequest<T, V> sourceRequest) {
        return getFlowable(sourceRequest, pageSize, prefetchDistance);
    }

    /**
     * 获取flowable（每个特殊的请求需要不同的分页大小和预加载距离时，可以调用此方法）
     *
     * @param sourceRequest    分页请求
     * @param <T>              分页参数类型
     * @param <V>              分页数据类型
     * @param pageSize         分页大小
     * @param prefetchDistance 预加载大小
     * @return : PagingRxJava 的数据源
     */
    public <T, V> Flowable<PagingData<V>> getFlowable(SimplePagingSource.PagingSourceRequest<T, V> sourceRequest, int pageSize, int prefetchDistance) {
        return PagingRx.getFlowable(new Pager<>(new PagingConfig(pageSize, prefetchDistance, false, pageSize), () -> new SimplePagingSource<>(sourceRequest)));
    }

    //获取默认一页加载数量
    public int getPageSize() {
        return pageSize;
    }
}
