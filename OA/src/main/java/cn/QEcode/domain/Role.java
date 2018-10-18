package cn.QEcode.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;


/**  
* <p>Title: Role.java</p>  
* <p>Description:职位的实体类 </p>   
* @author Qcode  
* @date 2018年10月16日  
* @version 1.0  
*/ 
@Entity
@Table(name="role")
public class Role {
    @Id
    @Column(name="role_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long roleId;
    
    @Column(name="name")
    private String name;
    
    @Column(name="description")
    private String description;
    
    @ManyToMany(mappedBy="roles")
    private Set<User> users = new HashSet<User>();
    
    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Set<User> getUsers() {
        return users;
    }
    public void setUsers(Set<User> users) {
        this.users = users;
    }
    @Override
    public String toString() {
	return "Role [roleId=" + roleId + ", name=" + name + ", description="
		+ description + "]";
    }
    public Long getRoleId() {
        return roleId;
    }
    public void setRoleId(Long roleId) {
        this.roleId = roleId;
    }
    
    public Role(){
	
    }
    
    public Role(Long roleId, String name) {
	this.roleId = roleId;
	this.name = name;
    }
    
    
    
    
    
    
}
