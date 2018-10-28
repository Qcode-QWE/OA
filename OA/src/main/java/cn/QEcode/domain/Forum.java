package cn.QEcode.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**  
* <p>Title: Forum.java</p>  
* <p>Description:论坛板块实体 </p>   
* @author Qcode  
* @date 2018年10月22日  
* @version 1.0  
*/ 
@Entity
@Table(name="forum")
public class Forum implements Serializable {
    
    	@Id
    	@Column(name="forum_id")
    	@GeneratedValue(strategy=GenerationType.IDENTITY)
    	private Long forumId;
    	
    	@Column(name="name")
	private String name;
    	
    	@Column(name="description")
	private String description;
    	
    	@Column(name="position")
	private int position; // 排序用的位置号
    	
    	//与帖子的关系是一对多关系
    	@OneToMany(mappedBy="forum")
    	private Set<Topic> topics = new HashSet<Topic>();
    	
    	@Column(name="topic_count")
	private int topicCount; // 主题数
	
    	@Column(name="article_count")
	private int articleCount; // 文章数（主题+回复）
	
    	@OneToOne(targetEntity=Topic.class)
    	@JoinColumn(name="last_topic",referencedColumnName="topic_id")
	private Topic lastTopic; // 最后发表的主题
	

	public String getName() {
	    return name;
	}
	public void setName(String name) {
	    this.name = name;
	}
	public String getDescription() {
	    return description;
	}
	public void setDescription(String description) {
	    this.description = description;
	}
	public int getPosition() {
	    return position;
	}
	public void setPosition(int position) {
	    this.position = position;
	}
	public Long getForumId() {
	    return forumId;
	}
	public void setForumId(Long forumId) {
	    this.forumId = forumId;
	}
	public Set<Topic> getTopics() {
	    return topics;
	}
	public void setTopics(Set<Topic> topics) {
	    this.topics = topics;
	}
	public int getTopicCount() {
	    return topicCount;
	}
	public void setTopicCount(int topicCount) {
	    this.topicCount = topicCount;
	}
	public int getArticleCount() {
	    return articleCount;
	}
	public void setArticleCount(int articleCount) {
	    this.articleCount = articleCount;
	}
	public Topic getLastTopic() {
	    return lastTopic;
	}
	public void setLastTopic(Topic lastTopic) {
	    this.lastTopic = lastTopic;
	}
}
