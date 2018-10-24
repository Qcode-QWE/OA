package cn.QEcode.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;
import javax.persistence.Table;

/**  
* <p>Title: Article.java</p>  
* <p>Description: 帖子 实体</p>   
* @author Qcode  
* @date 2018年10月22日  
* @version 1.0  
*/ 
@MappedSuperclass
public class Article {
    	@Column(name="title")
	private String title; // 标题
    	
    	@Column(name="content",columnDefinition="text",length=50000)
	private String content;// 内容
    	
    	@Column(name="faceIcon")
	private String faceIcon; // 表情符号
	
	//与作者是多对一的关系
	@ManyToOne(targetEntity=User.class)
	@JoinColumn(name="author",referencedColumnName="user_id")
	private User author;// 作者
	
	@Column(name="post_time",columnDefinition="TIMESTAMP")
	private Date postTime;// 发表时间
	
	@Column(name="ip_addr")
	private String ipAddr;// 发表文章时所用的IP地址

	public String getTitle() {
	    return title;
	}

	public void setTitle(String title) {
	    this.title = title;
	}

	public String getContent() {
	    return content;
	}

	public void setContent(String content) {
	    this.content = content;
	}

	public String getFaceIcon() {
	    return faceIcon;
	}

	public void setFaceIcon(String faceIcon) {
	    this.faceIcon = faceIcon;
	}

	public User getAuthor() {
	    return author;
	}

	public void setAuthor(User author) {
	    this.author = author;
	}

	public Date getPostTime() {
	    return postTime;
	}

	public void setPostTime(Date postTime) {
	    this.postTime = postTime;
	}

	public String getIpAddr() {
	    return ipAddr;
	}

	public void setIpAddr(String ipAddr) {
	    this.ipAddr = ipAddr;
	}
	
}
