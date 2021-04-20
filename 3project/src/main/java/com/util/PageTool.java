package com.util;

//接收从浏览器发来的page信息，springMVC会自动通过name属性自动填充
public class PageTool {
    //当前页码
    private  int page;
    //当前页条数
    private int rows;
    //查询起始页
    private int limitStart;

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getLimitStart() {
        return limitStart;
    }

    public void setLimitStart(int limitStart) {
        this.limitStart = limitStart;
    }

    @Override
    public String toString() {
        return "PageTool{" +
                "page=" + page +
                ", rows=" + rows +
                ", limitStart=" + limitStart +
                '}';
    }
}
