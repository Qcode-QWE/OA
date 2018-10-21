package cn.QEcode.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import cn.QEcode.domain.Department;
import cn.QEcode.service.DepartmentService;
import cn.QEcode.util.DepartmentUtils;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;


/**  
* <p>Title: DepartmentAction.java</p>  
* <p>Description:Department的Action </p>   
* @author Qcode  
* @date 2018年10月16日  
* @version 1.0  
*/ 
@Controller("departmentAction")
@Scope("prototype")
public class DepartmentAction {
    
    private Department department;
    @Resource(name="DepartmentService")
    private DepartmentService departmentService ;
    
    private List<Department> departments;
    
    private Long parentId;
    
    private Department parent;
    
    /**
     * @Description:列表页面
     * @return
     */
    
    public String listUI(){
	//查询顶级部门
	if(parentId == null){
	    departments = departmentService.findToList();
	}else{
	    departments = departmentService.findChildren(parentId);
	    parent = departmentService.findById(parentId);
	}
	return "listUI";
    }
    
    /**
     * @Description:增加页面
     * @return
     */
    public String addUI(){
	department = new Department();
	if(parentId!=null){
	    parent = departmentService.findById(parentId);
	}
	return "addUI";
    }
    
    /**
     * @Description:增加实体
     * @return
     */
    public String add(){
	parent = departmentService.findById(parentId);
	department.setParent(parent);
	departmentService.add(department);
	return "list";
    }
    
    /**
     * @Description:删除实体
     * @return
     */
    public String delete(){
	departmentService.delete(department);
	return "list";
    }
    
    /**
     * @Description:修改页面
     * @return
     */
    public String editUI(){
	//departments = departmentService.findAll();
	
	departments = DepartmentUtils.getTree(departmentService.findToList());
	department = departmentService.findById(department.getDepartmentId());
	return "editUI";
    }
    
    /**
     * @Description:修改实体
     * @return
     */
    public String edit(){
	if(department.getParent()!=null){
	    department.setParent(departmentService.findById(department.getParent().getDepartmentId()));
	    parentId = department.getParent().getDepartmentId();
	}
	departmentService.update(department);
	return "list";
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public List<Department> getDepartments() {
        return departments;
    }

    public void setDepartments(List<Department> departments) {
        this.departments = departments;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Department getParent() {
        return parent;
    }

    public void setParent(Department parent) {
        this.parent = parent;
    }


    
}
