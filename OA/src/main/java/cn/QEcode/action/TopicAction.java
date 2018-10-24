 package cn.QEcode.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import cn.QEcode.domain.Forum;
import cn.QEcode.domain.Reply;
import cn.QEcode.domain.Topic;
import cn.QEcode.domain.User;
import cn.QEcode.service.ForumService;
import cn.QEcode.service.ReplyService;
import cn.QEcode.service.TopicService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**  
* <p>Title: TopicAction.java</p>  
* <p>Description: 论坛主题Action</p>   
* @author Qcode  
* @date 2018年10月23日  
* @version 1.0  
*/ 

@Controller("topicAction")
public class TopicAction  extends ActionSupport{
    
    @Resource(name="TopicService")
    private TopicService topicService;
    @Resource(name="ForumService")
    private ForumService forumService;
    @Resource(name="ReplyService")
    private ReplyService replyService;
    
    private Topic topic;
    private Forum forum;
    private List<Reply> replies;
    private int type;
    
    
    public String show(){
	topic = topicService.findById(topic.getTopicId());
	replies = replyService.findByTopic(topic);
	return "show";
    }
    
    public String addUI(){
	forum = forumService.findById(forum.getForumId());
	return "addUI";
    }
    
    /**
     * @Description:新增主题
     * @return
     */
    public String add(){
	
	topic.setForum(forumService.findById(forum.getForumId()));
	//作者
	User user = (User) ActionContext.getContext().getSession().get("user");
	topic.setAuthor(user);
	//ip地址
	topic.setIpAddr(ServletActionContext.getRequest().getRemoteAddr());
	//发表时间
	topic.setPostTime(new Date());
	
	topicService.save(topic);
	return "toShow";
    }
    
    /**
     * @Description:修改为精华帖,置顶帖,普通贴
     * @return
     */
    public String edit(){
	topic = topicService.findById(topic.getTopicId());
	topic.setType(type);
	topicService.update(topic);
	return "toShow";
    }
    

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }

    public List<Reply> getReplies() {
        return replies;
    }

    public void setReplies(List<Reply> replies) {
        this.replies = replies;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    
    
    
    
    
}
