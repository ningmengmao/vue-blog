package com.ningmeng.vueblog.vo;

import org.springframework.context.annotation.Lazy;

import java.util.List;

public class Page<T> {

    private List<T> data;
    public final static int PAGE_SIZE = 8;
    private int total;

    public List<T> getOnePage(int no){
        if (data.size() == 0)
            throw new RuntimeException("page内没有数据");
        if (no < 1)
            throw new RuntimeException(" no 必须大于0");
        if (data.size() <= 8)
            return data;
        if ((no-1)*8 + 8 > total)
            return data.subList((no-1)*8, total);
        return data.subList((no-1)*8, 8);
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
        this.total = data.size() / PAGE_SIZE;
    }

    public int getTotal() {
        return total;
    }

    public static int getPAGESIZE() {
        return PAGE_SIZE;
    }

    public int getElementTotal(){
        return data.size();
    }
}
