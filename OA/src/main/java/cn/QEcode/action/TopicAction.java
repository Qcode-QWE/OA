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

import cn.QEcode.domain.Forum;
import cn.QEcode.domain.Page;
import cn.QEcode.domain.Reply;
import cn.QEcode.domain.Topic;
import cn.QEcode.domain.User;
import cn.QEcode.service.ForumService;
import cn.QEcode.service.ReplyService;
import cn.QEcode.service.TopicService;
import cn.QEcode.solr.SolrUtils;

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
    @Autowired
    private SolrUtils solrUtils ;
    
    private Topic topic;
    private Forum forum;
    private List<Reply> replies;
    private int type;
    private int pageNum = 1;
    private Page page;
    
    //查询条件
    private String findName;
    
    
    @RequiresPermissions("forum/forumAction_list")
    public String show(){
	topic = topicService.findById(topic.getTopicId());
	//replies = replyService.findByTopic(topic);
	page = replyService.getPage(pageNum,topic);
	return "show";
    }
    @RequiresPermissions("forum/forumAction_list")
    public String addUI(){
	forum = forumService.findById(forum.getForumId());
	return "addUI";
    }
    
    /**
     * @Description:新增主题
     * @return
     */
    @RequiresPermissions("forum/forumAction_list")
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
    @RequiresPermissions("forumManage/forumManageAction_list")
    public String edit(){
	topic = topicService.findById(topic.getTopicId());
	topic.setType(type);
	topicService.update(topic);
	return "toShow";
    }
    
    public String find(){
	page = null;
	if(findName!=null&&findName.length()>0){
	    String[] condition = new String[3];
	    try {
		condition[0] = findName;
		condition[1] = forum.getForumId().toString();
		page = solrUtils.SolrSearch( condition,pageNum, 0);
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

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }
    public String getFindName() {
        return findName;
    }
    public void setFindName(String findName) {
        this.findName = findName;
    }
  

    
    
    
    
    
}
