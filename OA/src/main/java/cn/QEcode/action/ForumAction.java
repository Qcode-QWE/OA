package cn.QEcode.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;

import cn.QEcode.domain.Forum;
import cn.QEcode.domain.Page;
import cn.QEcode.domain.Topic;
import cn.QEcode.service.ForumService;
import cn.QEcode.service.TopicService;

import com.opensymphony.xwork2.ActionSupport;

/**  
* <p>Title: ForumAction.java</p>  
* <p>Description:论坛板块Action </p>   
* @author Qcode  
* @date 2018年10月22日  
* @version 1.0  
*/ 
@Controller("forumAction")
public class ForumAction extends ActionSupport {
    
    @Resource(name="ForumService")
    private ForumService forumService;
    @Resource(name="TopicService")
    private TopicService topicService;
    
    private Forum forum;
    private List<Forum> forums;
    private List<Topic> topics;
    private int pageNum = 1;
    private Page page;
    /**
     * @Description:列表页面
     * @return
     */
    public String listUI(){
	forums = forumService.findAll();
	return "listUI";
    }
    
    /**
     * @Description:显示单个模块
     * @return		
     */
    public String show(){
	forum = forumService.findById(forum.getForumId());
	//topics =  topicService.findByForum(forum);
	//分页列表
	page = topicService.getPage(pageNum,forum);
	
	return "show";
    }
    
    
    
    public Forum getForum() {
        return forum;
    }

    public void setForum(Forum forum) {
        this.forum = forum;
    }

    public List<Forum> getForums() {
        return forums;
    }

    public void setForums(List<Forum> forums) {
        this.forums = forums;
    }

    public List<Topic> getTopics() {
        return topics;
    }

    public void setTopics(List<Topic> topics) {
        this.topics = topics;
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
    
    
}
