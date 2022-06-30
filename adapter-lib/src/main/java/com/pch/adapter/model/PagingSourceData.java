package com.pch.adapter.model;

import java.util.List;

public class PagingSourceData<V> {
    //当前页数据长度
    private int size;
    //列表数据总数
    private int total;
    //数据列表
    private List<V> list;

    public PagingSourceData(int size, int total, List<V> list) {
        this.size = size;
        this.total = total;
        this.list = list;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<V> getList() {
        return list;
    }

    public void setList(List<V> list) {
        this.list = list;
    }
}
