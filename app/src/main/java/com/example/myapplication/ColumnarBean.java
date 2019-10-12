package com.example.myapplication;

/**
 * @author : created by ZWH
 * Date : 2019/10/9
 */
public class ColumnarBean {

    private int narCount ;
    private String narContent;

    public ColumnarBean() {
    }

    public ColumnarBean(int narCount, String narContent) {
        this.narCount = narCount;
        this.narContent = narContent;
    }

    public int getNarCount() {
        return narCount;
    }

    public void setNarCount(int narCount) {
        this.narCount = narCount;
    }

    public String getNarContent() {
        return narContent;
    }

    public void setNarContent(String narContent) {
        this.narContent = narContent;
    }
}
