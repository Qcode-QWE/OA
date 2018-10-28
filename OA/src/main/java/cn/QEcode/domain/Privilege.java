package cn.QEcode.domain;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

import com.alibaba.fastjson.parser.Feature;

/**  
* <p>Title: Privilege.java</p>  
* <p>Description:权限的实体 </p>   
* @author Qcode  
* @date 2018年10月19日  
* @version 1.0  
*/ 
@Entity
@Table(name="privilege")
public class Privilege implements Serializable {
    	public Privilege(String url) {
	super();
	this.url = url;
    }

	@Id
    	@Column(name="privilege_id")
    	@GeneratedValue(strategy=GenerationType.IDENTITY)
    	private Long privilegeId;
    	
    	@Column(name="url")
	private String url;
    	
    	@Column(name="name")
	private String name;
    	
    	@Column(name="icon")
	private String icon; // 图标，顶级菜单用的
    	
    	//和职位的关系是多对多
    	@ManyToMany(mappedBy="privileges")
	private Set<Role> roles = new HashSet<Role>();
	
    	//和父权限的关系是多对一
	@ManyToOne(targetEntity=Privilege.class)
	@JoinColumn(name="parent",referencedColumnName="privilege_id")
	private Privilege parent;
	
	//和子权限的关系是一对多
	@OneToMany(mappedBy="parent",cascade={CascadeType.REMOVE},fetch=FetchType.EAGER)
	@OrderBy("privilegeId")
	private Set<Privilege> children = new HashSet<Privilege>();
	
	public Privilege(){
	    
	}
	
	
	public Privilege( String name,String url, String icon, Privilege parent) {
	    super();
	    this.url = url;
	    this.name = name;
	    this.icon = icon;
	    this.parent = parent;
	}

	public Long getPrivilegeId() {
	    return privilegeId;
	}

	public void setPrivilegeId(Long privilegeId) {
	    this.privilegeId = privilegeId;
	}

	public String getUrl() {
	    return url;
	}

	public void setUrl(String url) {
	    this.url = url;
	}

	public String getName() {
	    return name;
	}

	public void setName(String name) {
	    this.name = name;
	}

	public String getIcon() {
	    return icon;
	}

	public void setIcon(String icon) {
	    this.icon = icon;
	}

	public Set<Role> getRoles() {
	    return roles;
	}

	public void setRoles(Set<Role> roles) {
	    this.roles = roles;
	}

	public Privilege getParent() {
	    return parent;
	}

	public void setParent(Privilege parent) {
	    this.parent = parent;
	}

	public Set<Privilege> getChildren() {
	    return children;
	}

	public void setChildren(Set<Privilege> children) {
	    this.children = children;
	}
	
	
	
	
}
