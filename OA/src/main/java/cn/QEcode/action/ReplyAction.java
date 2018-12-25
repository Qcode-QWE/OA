package cn.QEcode.action;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.struts2.ServletActionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import cn.QEcode.domain.Page;
import cn.QEcode.domain.Reply;
import cn.QEcode.domain.Topic;
import cn.QEcode.domain.User;
import cn.QEcode.service.ReplyService;
import cn.QEcode.service.TopicService;
import cn.QEcode.solr.SolrUtils;

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
    @Autowired
    private SolrUtils solrUtils ;
    
    private Reply reply;
    private Topic topic;
    
    private Page page;
    private int type;
    private int pageNum = 1;
    private String findName;
   
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
    
    public String find(){
	page = null;
	if(findName!=null&&findName.length()>0){
	    topic = topicService.findById(topic.getTopicId());
	    String[] condition = new String[3];
	    try {
		condition[0] = findName;
		condition[1] = topic.getTopicId().toString();
		page = solrUtils.SolrSearch( condition,pageNum, 1);
	    } catch (SolrServerException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	    } catch (IOException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	    }
	}
	return "find";
    }
    
    /**
     * @Description:返回查询结果
     * @return
     */
    public String show(){
	reply = replyService.findById(reply.getReplyId());
	page = replyService.getFindPage(reply);
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
    public Page getPage() {
        return page;
    }
    public void setPage(Page page) {
        this.page = page;
    }
    public int getType() {
        return type;
    }
    public void setType(int type) {
        this.type = type;
    }
    public int getPageNum() {
        return pageNum;
    }
    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
    public String getFindName() {
        return findName;
    }
    public void setFindName(String findName) {
        this.findName = findName;
    }

   
    
    
}
