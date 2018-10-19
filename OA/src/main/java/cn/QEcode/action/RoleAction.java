package cn.QEcode.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;

import com.opensymphony.xwork2.ModelDriven;

import cn.QEcode.domain.Role;
import cn.QEcode.service.RoleService;


/**  
* <p>Title: RoleAction.java</p>  
* <p>Description:职位的Action </p>   
* @author Qcode  
* @date 2018年10月16日  
* @version 1.0  
*/ 
@Controller("roleAction")
public class RoleAction implements ModelDriven<Role>{
    
    @Resource(name="RoleService")
    private RoleService roleService;
    
    private Role role = new Role();
    private List<Role> roles;
    
    @Override
    public Role getModel() {
	// TODO 自动生成的方法存根
	return role;
    }
    
    /**
     * @Description:列表页面
     * @return
     */
    public String listUI(){
	roles = roleService.findAll();
	return "listUI";
    }
    
    /**
     * @Description:增加页面
     * @return
     */
    public String addUI(){
	role = new Role();
	return "addUI";
    }
    
    /**
     * @Description:增加实体
     * @return
     */
    public String add(){
	roleService.save(role);
	return "list";
    }
    
    /**
     * @Description:删除实体
     * @return
     */
    public String delete(){
	roleService.delete(role.getRoleId());
	return "list";
    }
    
    /**
     * @Description:修改页面
     * @return
     */
    public String editUI(){
	Role r = roleService.findById(role.getRoleId());
	BeanUtils.copyProperties(r,role);
	return "editUI";
    }
    
    /**
     * @Description:修改实体
     * @return
     */
    public String edit(){
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

    
}
