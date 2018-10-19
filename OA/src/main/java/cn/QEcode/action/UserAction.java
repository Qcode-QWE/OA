package cn.QEcode.action;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;

import cn.QEcode.dao.RoleDao;
import cn.QEcode.domain.Department;
import cn.QEcode.domain.Role;
import cn.QEcode.domain.User;
import cn.QEcode.service.DepartmentService;
import cn.QEcode.service.RoleService;
import cn.QEcode.service.UserService;
import cn.QEcode.util.DepartmentUtils;

import com.opensymphony.xwork2.ModelDriven;


/**  
* <p>Title: UserAction.java</p>  
* <p>Description:User的Action </p>   
* @author Qcode  
* @date 2018年10月16日  
* @version 1.0  
*/ 
@Controller("userAction")
@Scope("prototype")
public class UserAction{
   
    private User user = new User();
    @Resource(name="UserService")
    private UserService userService;
    @Resource(name="DepartmentService")
    private DepartmentService departmentService;
    @Resource(name="RoleService")
    private RoleService roleService;
    
    private List<User> users;
    //部门名称列表
    private List<Department> departments;
    
    //职位名称列表
    private List<Role> roleList;
    //获取页面返回的职位id列表
    private Long[] roleIds;
    
    
    
    
    /**
     * @Description:列表页面
     * @return
     */
    public String listUI(){
	// TODO 要修改为树状结构
	users = userService.findAll();
	return "listUI";
    }
    
    /**
     * @Description:增加页面
     * @return
     */
    public String addUI(){
	user = new User();
	//获取部门名称列表
	departments = departmentService.getName();
	//获取职位名称列表
	roleList = roleService.findAll();
	return "addUI";
    }
    
    /**
     * @Description:增加实体
     * @return
     */
    public String add(){
	
	List<Role> roles = roleService.findByIds(roleIds);
	user.setRoles(new HashSet<Role>(roles));
	user.setPassword(DigestUtils.md5DigestAsHex("123".getBytes()));
	
	userService.save(user);
	return "list";
    }
    
    /**
     * @Description:删除实体
     * @return
     */
    public String delete(){
	userService.delete(user.getUserId());
	return "list";
    }
    
    /**
     * @Description:初始化密码为123
     * @return
     */
    public String initPassword(){
	user = userService.findById(user.getUserId());
	user.setPassword(DigestUtils.md5DigestAsHex("123".getBytes()));
	userService.update(user);
	return "list";
    }
    
    
    /**
     * @Description:修改页面
     * @return
     */
    public String editUI(){
	
	user = userService.findById(user.getUserId());
	//获取顶级部门列表
	List<Department> topList = departmentService.findToList();
	//获取部门名称列表
    	departments = DepartmentUtils.getTree(topList);
    	//获取职位名称列表
    	roleList = roleService.findAll();
    	//获取用户的职位列表
    	if(user.getRoles()!=null){
    	    	roleIds = new Long[user.getRoles().size()+1];
        	int i = 0;
        	for (Role role : user.getRoles()) {
        	    roleIds[i++] = role.getRoleId();  
        	}
    	}
    	
    	
	return "editUI";
    }
    
    /**
     * @Description:修改实体
     * @return
     */
    public String edit(){
	
	List<Role> roles = roleService.findByIds(roleIds);
	user.setRoles(new HashSet<Role>(roles));
	userService.update(user);
	return "list";
    }
    


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public Long[] getRoleIds() {
        return roleIds;
    }

    public void setRoleIds(Long[] roleIds) {
        this.roleIds = roleIds;
    }


    
    
}
