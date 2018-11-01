package cn.QEcode.action;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.struts2.ServletActionContext;
import org.springframework.stereotype.Controller;

import cn.QEcode.domain.Reply;
import cn.QEcode.domain.Topic;
import cn.QEcode.domain.User;
import cn.QEcode.service.ReplyService;
import cn.QEcode.service.TopicService;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;

/**  
* <p>Title: ReplyAction.java</p>  
* <p>Description:论坛回帖Action </p>   
* @author Qcode  
* @date 2018年10月22日  
* @version 1.0  
*/ 
@Controller("replyAction")
public class ReplyAction extends ActionSupport {
    
    @Resource(name="ReplyService")
    private ReplyService replyService;
    @Resource(name="TopicService")
    private TopicService topicService;
    
    private Reply reply;
    private Topic topic;
    
    /**
     * @Description:增加回帖页面
     * @return
     */
    @RequiresPermissions("forum/forumAction_list")
    public String addUI(){
	topic = topicService.findById(topic.getTopicId());
	return "addUI";
    }
    @RequiresPermissions("forum/forumAction_list")
    public String add(){
	reply.setTopic(topicService.findById(topic.getTopicId()));
	User user = (User) ActionContext.getContext().getSession().get("user");
	reply.setAuthor(user);
	reply.setIpAddr(ServletActionContext.getRequest().getRemoteAddr());
	reply.setPostTime(new Date());
	replyService.save(reply);
	return "toShow";
    }

    public Reply getReply() {
        return reply;
    }

    public void setReply(Reply reply) {
        this.reply = reply;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

   
    
    
}
