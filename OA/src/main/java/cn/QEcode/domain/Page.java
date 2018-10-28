package cn.QEcode.domain;

import java.io.Serializable;
import java.util.List;

/**  
* <p>Title: Page.java</p>  
* <p>Description: 分页的类</p>   
* @author Qcode  
* @date 2018年10月12日  
* @version 1.0  
*/ 
public class Page implements Serializable {
    
    private int currentNum;//当前页数
    private int pageSize = 5;//每页显示的页数
    private int totalRecords;//总记录数
    private int startIndex;//查询开始的索引
    private int totalPageNum;//总页数
    private int prePageNum;//上一页
    private int nextPageNum;//下一页
    private List records;//每页的记录集
    private int beginPageIndex; // 页码列表的开始索引（包含）
    private int endPageIndex; // 页码列表的结束索引（包含）
    
    /*
     * 一定要提供当前页数和总记录数
     */
    public Page(int currentNum,int totalRecords){
	this.currentNum =currentNum;
	this.totalRecords = totalRecords;
	//获取索引
	startIndex = (currentNum - 1) * pageSize;
	//计算总页数
	if(totalRecords==0){
	    totalPageNum = 1;
	}else{
	    totalPageNum = totalRecords%pageSize==0? totalRecords/pageSize : (totalRecords/pageSize)+1;
	}
	//计算页码,当总页数小于等于10,全部显示
	if(totalRecords <= 10){
	    beginPageIndex = 1;
	    endPageIndex = totalPageNum;
	}else{
	    //当总页数大于10,显示当前页附件页数
	    beginPageIndex = currentNum - 4;
	    endPageIndex = currentNum + 5;
	    //如果currentNum小于4,则显示前10页
	    if(beginPageIndex < 1){
		beginPageIndex = 1;
		endPageIndex = 10;
	    }else if(endPageIndex > totalPageNum){
		//如果后面不足5页,显示后10页
		endPageIndex = totalPageNum;
		beginPageIndex = totalPageNum - 9;
		
	    }
	
	}
	
	
	
    }

    //上一页
    public int getPrePageNum() {
	prePageNum -= 1;
	if(prePageNum<1){
	    prePageNum = 1;
	}
        return prePageNum;
    }
    
    //下一页
    public int getNextPageNum() {
	nextPageNum += 1;
	if (nextPageNum>totalPageNum) {
	    nextPageNum = totalPageNum;
	}
        return nextPageNum;
    }

    public int getCurrentNum() {
        return currentNum;
    }

    public void setCurrentNum(int currentNum) {
        this.currentNum = currentNum;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public int getStartIndex() {
        return startIndex;
    }

    public void setStartIndex(int startIndex) {
        this.startIndex = startIndex;
    }

    public int getTotalPageNum() {
        return totalPageNum;
    }

    public void setTotalPageNum(int totalPageNum) {
        this.totalPageNum = totalPageNum;
    }

    public List getRecords() {
        return records;
    }

    public void setRecords(List records) {
        this.records = records;
    }

    public void setPrePageNum(int prePageNum) {
        this.prePageNum = prePageNum;
    }

    public void setNextPageNum(int nextPageNum) {
        this.nextPageNum = nextPageNum;
    }

    public int getBeginPageIndex() {
        return beginPageIndex;
    }

    public void setBeginPageIndex(int beginPageIndex) {
        this.beginPageIndex = beginPageIndex;
    }

    public int getEndPageIndex() {
        return endPageIndex;
    }

    public void setEndPageIndex(int endPageIndex) {
        this.endPageIndex = endPageIndex;
    }
    
    
    
    
    
    
    
    
    
}
