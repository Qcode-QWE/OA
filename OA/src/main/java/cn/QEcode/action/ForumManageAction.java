package cn.QEcode.action;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import cn.QEcode.domain.Forum;
import cn.QEcode.service.ForumService;
import cn.QEcode.solr.SolrUtils;

import com.opensymphony.xwork2.ActionSupport;


/**  
* <p>Title: ForumManageAction.java</p>  
* <p>Description:论坛板块管理Action   
* @author Qcode  
* @date 2018年10月22日  
* @version 1.0  
*/ 

@Controller("forumManageAction")
public class ForumManageAction extends ActionSupport {
    
    @Resource(name="ForumService")
    private ForumService forumService;
    
    private Forum forum;
    
    private List<Forum> forums;
    
    /**
     * @Description:论坛板块列表
     * @return
     */
    @RequiresPermissions("forumManage/forumManageAction_list")
    public String listUI(){
	forums = forumService.findAll();
	return "listUI";
    }
    
    
    /**
     * @Description:添加页面
     * @return
     */
    @RequiresPermissions("forumManage/forumManageAction_list")
    public String addUI(){
	//forum = new Forum();
	return "addUI";
    }
    
    /**
     * @Description:新增板块
     * @return
     */
    @RequiresPermissions("forumManage/forumManageAction_list")
    public String add(){
	forumService.save(forum);
	return "list";
    }
    
    /**
     * @Description:修改论坛板块页面
     * @return
     */
    @RequiresPermissions("forumManage/forumManageAction_list")
    public String editUI(){
	forum = forumService.findById(forum.getForumId());
	return "editUI";
    }
    
    /**
     * @Description:修改论坛板块
     * @return
     */
    @RequiresPermissions("forumManage/forumManageAction_list")
    public String edit(){
	Forum f = forumService.findById(forum.getForumId());
	f.setName(forum.getName());
	f.setDescription(forum.getDescription());
	forumService.update(f);
	return "list";
    }
    
    /**
     * @Description:删除板块
     * @return
     */
    @RequiresPermissions("forumManage/forumManageAction_list")
    public String delete(){
	forumService.delete(forum.getForumId());
	return "list";
    }
    
    
    /**
     * @Description:上移板块
     * @return
     */
    @RequiresPermissions("forumManage/forumManageAction_list")
    public String moveUp(){
	forumService.moveUp(forum.getForumId());
	return "list";
    }
    
    /**
     * @Description:下移板块
     * @return
     */
    @RequiresPermissions("forumManage/forumManageAction_list")
    public String moveDown(){
	forumService.moveDown(forum.getForumId());
	return "list";
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
    
}
