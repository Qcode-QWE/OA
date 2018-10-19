package cn.QEcode.domain;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Cascade;


/**  
* <p>Title: Department.java</p>  
* <p>Description:部门的实体类 </p>   
* @author Qcode  
* @date 2018年10月16日  
* @version 1.0  
*/ 
@Entity
@Table(name="department")
public class Department {
    @Id
    @Column(name="department_id")
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long departmentId;
    
    //部门与父部门的关系是多对一
    @ManyToOne(targetEntity=Department.class)
    @JoinColumn(name="parent",referencedColumnName="department_id")
    private Department parent;
    
    //部门与子部门的关系是一对多
    @OneToMany(mappedBy="parent",cascade={CascadeType.REMOVE})
    private Set<Department> children = new  HashSet<Department>();
    
    @Column(name="name")
    private String name;
    
    @Column(name="description")
    private String description;
    
    //部门与用户的关系是一对多
    @OneToMany(mappedBy="departmentId")
    private Set<User> users = new HashSet<User>();
    
    
   
    public Department getParent() {
        return parent;
    }
    public void setParent(Department parent) {
        this.parent = parent;
    }
    public Set<Department> getChildren() {
        return children;
    }
    public void setChildren(Set<Department> children) {
        this.children = children;
    }
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
	return "Department [departmentId=" + departmentId + ", name=" + name + ", description="
		+ description + "]";
    }
    public Long getDepartmentId() {
        return departmentId;
    }
    public Department(Long departmentId, String name) {
	super();
	this.departmentId = departmentId;
	this.name = name;
    }
    public Department() {
	// TODO 自动生成的构造函数存根
    }
    public void setDepartmentId(Long departmentId) {
        this.departmentId = departmentId;
    }
    
    
    
    
    
    
}
