package cn.QEcode.domain;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**  
* <p>Title: Topic.java</p>  
* <p>Description:论坛主题  实体 </p>   
* @author Qcode  
* @date 2018年10月22日  
* @version 1.0  
*/ 

@Entity
@Table(name="topic")
public class Topic extends Article {
    	
    	/** 普通帖 */
	public static final int TYPE_NORMAL = 0;

	/** 精华帖 */
	public static final int TYPE_BEST = 1;

	/** 置顶帖 */
	public static final int TYPE_TOP = 2;
	
	 @Id
	 @Column(name="topic_id")
	 @GeneratedValue(strategy=GenerationType.IDENTITY)
	 private Long topicId;
	
	//主题和板块的关系是多对一的关系
	@ManyToOne(targetEntity=Forum.class)
	@JoinColumn(name="forum",referencedColumnName="forum_id")
	private Forum forum;// 所属版块
	
	//主题与回帖的关系是一对多的关系
	@OneToMany(mappedBy="topic")
	private Set<Reply> replies = new HashSet<Reply>();
	
	@Column(name="type")
	private int type;// 类型
	
	@Column(name="reply_count")
	private int replyCount;// 回复数量
	
	@OneToOne(targetEntity=Reply.class)
	@JoinColumn(name="last_reply",referencedColumnName="reply_id") 	
	private Reply lastReply;// 最后回复
	
	@Column(name="last_update_time",columnDefinition="DATETIME")
	private Date lastUpdateTime;// 最后更新时间（主题发表时间或最后回复的时间）

	public Forum getForum() {
	    return forum;
	}

	public void setForum(Forum forum) {
	    this.forum = forum;
	}

	public Set<Reply> getReplies() {
	    return replies;
	}

	public void setReplies(Set<Reply> replies) {
	    this.replies = replies;
	}

	public int getType() {
	    return type;
	}

	public void setType(int type) {
	    this.type = type;
	}

	public int getReplyCount() {
	    return replyCount;
	}

	public void setReplyCount(int replyCount) {
	    this.replyCount = replyCount;
	}

	public Reply getLastReply() {
	    return lastReply;
	}

	public void setLastReply(Reply lastReply) {
	    this.lastReply = lastReply;
	}

	public Date getLastUpdateTime() {
	    return lastUpdateTime;
	}

	public void setLastUpdateTime(Date lastUpdateTime) {
	    this.lastUpdateTime = lastUpdateTime;
	}

	public Long getTopicId() {
	    return topicId;
	}

	public void setTopicId(Long topicId) {
	    this.topicId = topicId;
	}
}
