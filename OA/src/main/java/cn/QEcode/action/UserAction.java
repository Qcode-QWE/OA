package cn.QEcode.action;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;

import cn.QEcode.dao.RoleDao;
import cn.QEcode.domain.Department;
import cn.QEcode.domain.Privilege;
import cn.QEcode.domain.Role;
import cn.QEcode.domain.User;
import cn.QEcode.service.DepartmentService;
import cn.QEcode.service.RoleService;
import cn.QEcode.service.UserService;
import cn.QEcode.util.DepartmentUtils;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

/**
 * <p>
 * Title: UserAction.java
 * </p>
 * <p>
 * Description:User的Action
 * </p>
 * 
 * @author Qcode
 * @date 2018年10月16日
 * @version 1.0
 */
@Controller("userAction")
@Scope("prototype")
public class UserAction extends ActionSupport {

    private User user;
    @Resource(name = "UserService")
    private UserService userService;
    @Resource(name = "DepartmentService")
    private DepartmentService departmentService;
    @Resource(name = "RoleService")
    private RoleService roleService;

    private List<User> users;
    // 部门名称列表
    private List<Department> departments;

    // 职位名称列表
    private List<Role> roleList;
    // 获取页面返回的职位id列表
    private Long[] roleIds;

    /**
     * @Description:列表页面
     * @return
     */
    @RequiresPermissions("user/userAction_list")
    public String listUI() {
	// TODO 要修改为树状结构
	users = userService.findAll();
	return "listUI";
    }

    /**
     * @Description:增加页面
     * @return
     */
    @RequiresPermissions("user/userAction_add")
    public String addUI() {
	
	// 获取部门名称列表
	departments = departmentService.getName();
	// 获取职位名称列表
	roleList = roleService.findAll();
	return "addUI";
    }

    /**
     * @Description:增加实体
     * @return
     */
    @RequiresPermissions("user/userAction_add")
    public String add() {
	List<Role> roles = roleService.findByIds(roleIds);
	user.setRoles(new HashSet<Role>(roles));
	String password = new Md5Hash(user.getPassword(),"123",2).toString();
	user.setPassword(password);
	userService.save(user);
	return "list";
    }

    /**
     * @Description:删除实体
     * @return
     */
    @RequiresPermissions("user/userAction_delete")
    public String delete() {
	userService.delete(user.getUserId());
	return "list";
    }

    /**
     * @Description:初始化密码为123
     * @return
     */
    @RequiresPermissions("user/userAction_edit")
    public String initPassword() {
	user = userService.findById(user.getUserId());
	String password = new Md5Hash("123","123",2).toString();
	user.setPassword(password);
	userService.update(user);
	return "list";
    }

    /**
     * @Description:修改页面
     * @return
     */
    @RequiresPermissions("user/userAction_edit")
    public String editUI() {

	user = userService.findById(user.getUserId());
	// 获取顶级部门列表
	List<Department> topList = departmentService.findToList();
	// 获取部门名称列表
	departments = DepartmentUtils.getTree(topList);
	// 获取职位名称列表
	roleList = roleService.findAll();
	// 获取用户的职位列表
	if (user.getRoles() != null) {
	    roleIds = new Long[user.getRoles().size() + 1];
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
    @RequiresPermissions("user/userAction_edit")
    public String edit() {
	List<Role> roles = roleService.findByIds(roleIds);
	user.setRoles(new HashSet<Role>(roles));
	userService.update(user);
	return "list";
    }

    /** 登录页面 */
    public String loginUI() throws Exception {
	return "loginUI";
    }

    /** 注销 */
    public String logout() throws Exception {
	ActionContext.getContext().getSession().remove("user");
	return "logout";
    }

    /**
     * @Description:登陆
     * @return
     * @throws Exception
     */
    public String login() {
	
	Subject subject = SecurityUtils.getSubject();
	UsernamePasswordToken token = new UsernamePasswordToken(user.getLoginName(),user.getPassword());
	try {
	    subject.login(token);
	    user = (User) subject.getPrincipals().getPrimaryPrincipal();
		ActionContext.getContext().getSession().put("user", user);
	} catch (Exception e) {
	    addFieldError("login", "用户名或密码不正确");
	    return "loginUI";
	}
	return "toIndex";
	/*if (StringUtils.isNotBlank(user.getLoginName())
		&& StringUtils.isNotBlank(user.getPassword())) {
	    User u = userService.findByLoginNameAndPassword(
		    user.getLoginName(), user.getPassword());
	    if (u != null) {
		ActionContext.getContext().getSession().put("user", u);
		Set<String> set = new HashSet<String>();
		Set<String> urlSet = new HashSet<String>();
		//获取用户权限和url权限
		for(Role role:u.getRoles()){
		    for(Privilege privilege:role.getPrivileges()){
			set.add(privilege.getName());
			if(privilege.getUrl()!=null){
			    //去掉url中(xx/)Action
			    String url = privilege.getUrl();
			    int index = url.lastIndexOf('/');
			    if(index!=-1){
				url = url.substring(index+1, url.length());
			    }
			    urlSet.add(url);
			}
		    }
		}
		ActionContext.getContext().getSession().put("set", set);
		ActionContext.getContext().getSession().put("urlSet", urlSet);
		return "toIndex";
	    }
	
	}
	addFieldError("login", "用户名或密码不正确");
	
	return "loginUI";
	*/
	
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
