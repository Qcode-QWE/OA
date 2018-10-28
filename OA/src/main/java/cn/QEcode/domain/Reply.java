package cn.QEcode.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**  
* <p>Title: Reply.java</p>  
* <p>Description:论坛回复帖子 实体 </p>   
* @author Qcode  
* @date 2018年10月22日  
* @version 1.0  
*/ 
@Entity
@Table(name="reply")
public class Reply extends Article implements Serializable {
    
    @Id
    @Column(name="reply_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long replyId;
    
    //与主题的属性是多对一的关系
    @ManyToOne(targetEntity=Topic.class)
    @JoinColumn(name="topic",referencedColumnName="topic_id")
    private Topic topic;// 所属的主题
    
    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Long getReplyId() {
        return replyId;
    }

    public void setReplyId(Long replyId) {
        this.replyId = replyId;
    }

    
    
    
}
