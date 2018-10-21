package cn.QEcode.domain;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.opensymphony.xwork2.ActionContext;


/**  
* <p>Title: User.java</p>  
* <p>Description: 用户的实体类</p>   
* @author Qcode  
* @date 2018年10月16日  
* @version 1.0  
*/ 

@Entity
@Table(name="user")
public class User {
    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long userId;
    
    @Column(name="login_name")
    private String loginName;
    
    @Column(name="password")
    private String password;
    
    @Column(name="name")
    private String name;
    
    @Column(name="gender")
    private String gender;
    
    @Column(name="phone")
    private String phone;
    
    @Column(name="email")
    private String email;
    
    //use与部门的关系是多对一的关系
    @ManyToOne(targetEntity=Department.class)
    @JoinColumn(name="department_id",referencedColumnName="department_id")
    private Department departmentId;
    
    //use与职位的关系是多对多的关系
    @ManyToMany(targetEntity=Role.class,fetch=FetchType.EAGER)
    @JoinColumn(name="roles",referencedColumnName="roles_id")
    private Set<Role> roles = new HashSet<Role>();
    
    
    public boolean hasPrivilegeByName(String privilegeName,Set<String> set){
	//如果是超级管理员,拥有全部权限
	if("admin".equals(loginName)){
	    return true;
	}
	
	/*for(Role role:roles){
	    for(Privilege privilege : role.getPrivileges()){
		System.out.println(privilege.getName()+"  "+privilegeName);
		if(privilege.getName().equals(privilegeName)){
		    System.out.println(privilegeName);
		    return true;
		}
	    }
	}*/
	if(set.contains(privilegeName)){
	    return true;
	}
	return false;
    }
    
    public boolean hasPrivilegeByUrl(String privilegeUrl){
	String uu = privilegeUrl;
	//获取XXX/XXX/XXAction_xxx中XXAction_xxx
	//获取最后一个/的位置
	int index = uu.lastIndexOf("/");
	String url = uu;
	//获取XXAction_xxx
	if(index!=-1){
	    url = uu.substring(index+1);
	}
	//去掉url后可能存在的?
	index = url.indexOf("?");
	if(index!=-1){
	    url = url.substring(0,index);
	}
	//去掉url中的UI,方便判断
	index = url.indexOf("UI");
	if(index!=-1){
	    url = url.substring(0, index);
	}
	Set<String> urlSet = (Set<String>) ActionContext.getContext().getSession().get("urlSet");
	if(urlSet!=null&&urlSet.contains(url)){
	    return true;
	}else{
	    return false;
	}
    }

    public String getLoginName() {
        return loginName;
    }
    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Department getDepartmentId() {
        return departmentId;
    }
    public void setDepartmentId(Department department) {
        this.departmentId = department;
    }
    public Set<Role> getRoles() {
        return roles;
    }
    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }
    @Override
    public String toString() {
	return "User [userid=" + userId + ", loginName=" + loginName + ", password="
		+ password + ", name=" + name + ", gender=" + gender
		+ ", phone=" + phone + ", email=" + email + "]";
    }
    public Long getUserId() {
        return userId;
    }
    public void setUserId(Long userId) {
        this.userId = userId;
    }



    
    
    
    
}
