package com.pch.adapter.paging;

import android.annotation.SuppressLint;

import androidx.paging.PagingState;
import androidx.paging.rxjava2.RxPagingSource;

import com.pch.adapter.model.PagingSourceData;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.reactivex.Single;
import io.reactivex.SingleEmitter;

/**
 * java 中使用  PagingSource
 *
 * @param <T>        ：分页参数类型
 * @param <V>：分页数据类型
 */
public class SimplePagingSource<T, V> extends RxPagingSource<T, V> {
    LoadParams<T> params;
    PagingSourceRequest<T, V> sourceRequest;

    public SimplePagingSource(PagingSourceRequest<T, V> sourceRequest) {
        this.sourceRequest = sourceRequest;
    }

    /**
     * 网络数据请求
     *
     * @param loadParams ：可以获取到Pager 设置的参数
     * @return ：网络请求成功后 RxJava格式的数据源
     */
    @SuppressLint("CheckResult")
    @NotNull
    @Override
    public Single<LoadResult<T, V>> loadSingle(@NotNull LoadParams<T> loadParams) {
        params = loadParams;
        return request(loadParams, sourceRequest)
                .map(this::loadResult)
                .onErrorReturn(LoadResult.Error::new);
    }

    /**
     * RxJava 外部网络请求，向下发送
     */
    private Single<PagingSourceData<V>> request(final LoadParams<T> loadParams, PagingSourceRequest<T, V> callBack) {
        return Single.create(emitter -> {
            callBack.handleRequest(emitter, loadParams);
        });
    }

    /**
     * 请求成功后返回PagingData
     */
    private LoadResult<T, V> loadResult(PagingSourceData<V> list) {
        T nextKey = sourceRequest.getNextPageKey(params, list);
        return new LoadResult.Page<>(
                list.getList(),
                null,
                nextKey,
                LoadResult.Page.COUNT_UNDEFINED,
                LoadResult.Page.COUNT_UNDEFINED);
    }

    /**
     * 在refresh
     * <p>
     * 官网介绍： 实现必须定义如何从已加载分页数据的中间恢复刷新。
     * 为此，您可以实现 getRefreshKey()，
     * 以使用 state.anchorPosition 作为最近访问的索引来映射正确的初始键（没看懂！）
     * </p>
     *
     * @param pagingState :当前加载更多的状态
     * @return ：
     */
    @Nullable
    @Override
    public T getRefreshKey(@NotNull PagingState<T, V> pagingState) {
//        PagingConfig config = pagingState.getConfig();
//        List<LoadResult.Page<T, V>> pages = pagingState.getPages();
//        pagingState.anchorPosition
        return null;
    }


    public interface PagingSourceRequest<T, V> {
        /**
         * 处理网络请求（暴露给外部调用）
         *
         * @param emitter 把请求结果通过emitter返回给pagingSource
         * @param params  加载参数（分页相关的）
         */
        void handleRequest(SingleEmitter<PagingSourceData<V>> emitter, LoadParams<T> params);

        /**
         * 下一页分页参数
         *
         * @param params 加载参数
         * @param list   返回的数据列表
         * @return 下一页分页参数
         */
        T getNextPageKey(LoadParams<T> params, PagingSourceData<V> list);
    }
}
