package cn.QEcode.action;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
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
    
    //条件变量
    private Integer viewType;
    private Integer orderBy;
    private Boolean asc;
    
    
    /**
     * @Description:列表页面
     * @return
     */
    @RequiresPermissions("forum/forumAction_list")
    public String listUI(){
	forums = forumService.findAll();
	return "listUI";
    }
    
    /**
     * @Description:显示单个模块
     * @return		
     */
    @RequiresPermissions("forum/forumAction_show")
    public String show(){
	forum = forumService.findById(forum.getForumId());
	
	Object[] objects = new Object[4];
	objects[0] = forum;
	if(viewType==null){
	    viewType = 0;
	}
	objects[1] = viewType;
	if(orderBy==null){
	    orderBy = 0;
	}
	objects[2] = orderBy;
	if(asc==null||orderBy==0){
	    asc = false;
	}
	objects[3] = asc;
	//分页列表
	page = topicService.getPage(pageNum,objects);
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

    public int getViewType() {
        return viewType;
    }

    public void setViewType(int viewType) {
        this.viewType = viewType;
    }

    public int getOrderBy() {
        return orderBy;
    }

    public void setOrderBy(int orderBy) {
        this.orderBy = orderBy;
    }

    public Boolean getAsc() {
        return asc;
    }

    public void setAsc(Boolean asc) {
        this.asc = asc;
    }
    
    
}
