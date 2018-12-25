package cn.QEcode.action;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

import cn.QEcode.domain.Page;
import cn.QEcode.domain.Privilege;
import cn.QEcode.domain.Role;
import cn.QEcode.service.PrivilegeService;
import cn.QEcode.service.RoleService;


/**  
* <p>Title: RoleAction.java</p>  
* <p>Description:职位的Action </p>   
* @author Qcode  
* @date 2018年10月16日  
* @version 1.0  
*/ 
@Controller("roleAction")
@Scope("prototype")
public class RoleAction implements ModelDriven<Role>{
    
    @Resource(name="RoleService")
    private RoleService roleService;
    @Resource(name="PrivilegeService")
    private PrivilegeService privilegeService;
    
    private Role role = new Role();
    private List<Role> roles;
    
    private Long[] privilegeIds;
    private List<Privilege> privilegeList;
    private List<Privilege> topList;
    private int pageNum = 1;
    private Page page;
    
    //查询条件
    private String roleName;
    private String roleDescription;
    
    
    @Override
    public Role getModel() {
	// TODO 自动生成的方法存根
	System.out.println(role.getName());
	return role;
    }
    
    /**
     * @Description:列表页面
     * @return
     */
    @RequiresPermissions("role/roleAction_list")
    public String listUI(){
	//roles = roleService.findAll();
	//分页查询
	page = roleService.getPage(pageNum,roleName,roleDescription);
	return "listUI";
    }
    
    /**
     * @Description:增加页面
     * @return
     */
    @RequiresPermissions("role/roleAction_add")
    public String addUI(){
	
	return "addUI";
    }
    
    /**
     * @Description:增加实体
     * @return
     */
    @RequiresPermissions("role/roleAction_add")
    public String add(){
	roleService.save(role);
	return "list";
    }
    
    /**
     * @Description:删除实体
     * @return
     */
    @RequiresPermissions("role/roleAction_delete")
    public String delete(){
	roleService.delete(role.getRoleId());
	return "list";
    }
    
    /**
     * @Description:修改页面
     * @return
     */
    @RequiresPermissions("role/roleAction_edit")
    public String editUI(){
	Role r = roleService.findById(role.getRoleId());
	BeanUtils.copyProperties(r,role);
	return "editUI";
    }
    
    /**
     * @Description:修改实体
     * @return
     */
    @RequiresPermissions("role/roleAction_edit")
    public String edit(){
	roleService.update(role);
	return "list";
    }
    
    
    /**
     * @Description:设置权限页面
     * @return
     */
    @RequiresPermissions("role/roleAction_edit")
    public String setPrivilegeUI(){
	role = roleService.findById(role.getRoleId());
	//获取权限列表
	//privilegeList = privilegeService.findAll();
	//查询
	topList = privilegeService.findtopList();
	//回显数据
	if(role.getPrivileges()!=null){
	    int i = 0;
	    privilegeIds = new Long[role.getPrivileges().size()+1];
	    for(Privilege privilege : role.getPrivileges()){
		privilegeIds[i++] = privilege.getPrivilegeId();
	    }
	}
	return "setPrivilegeUI";
    }
    
    /**
     * @Description:设置权限
     * @return
     */
    @RequiresPermissions("role/roleAction_edit")
    public String setPrivilege(){
	List<Privilege> list = privilegeService.findByIds(privilegeIds);
	role = roleService.findById(role.getRoleId());
	role.setPrivileges(new HashSet<Privilege>(list));
	roleService.update(role);
	return "list";	
    }
    
    
    
    

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }

    public Long[] getPrivilegeIds() {
        return privilegeIds;
    }

    public void setPrivilegeIds(Long[] privilegeIds) {
        this.privilegeIds = privilegeIds;
    }

    public List<Privilege> getPrivilegeList() {
        return privilegeList;
    }

    public void setPrivilegeList(List<Privilege> privilegeList) {
        this.privilegeList = privilegeList;
    }

    public List<Privilege> getTopList() {
        return topList;
    }

    public void setTopList(List<Privilege> topList) {
        this.topList = topList;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
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
