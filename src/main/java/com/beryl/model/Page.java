package com.beryl.model;

/**
 * Created by qjnup on 2016/12/7.
 */
public class Page {
    private int pageStart=0;
    private int pageCount=10;
    private int totalPage;
    private int amount;
    private int pageNum;

    public int getPageStart() {
        return pageStart;
    }

    public void setPageStart() {
        this.pageStart = (this.pageNum-1)*this.pageCount;
    }

    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public int getTotalPage() {
        return totalPage;
    }

    public void setTotalPage() {
        if(this.amount%this.pageCount==0){
            this.totalPage=this.amount/this.pageCount;
        }else {
            this.totalPage=this.amount/this.pageCount+1;
        }
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        if(pageNum<1){
            this.pageNum=1;
        }else if(pageNum>this.pageCount){
            this.pageNum=this.pageCount;
        }else{
            this.pageNum = pageNum;
        }
    }
}
