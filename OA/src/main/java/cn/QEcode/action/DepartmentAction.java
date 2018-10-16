package cn.QEcode.action;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;

import cn.QEcode.domain.Department;
import cn.QEcode.service.DepartmentService;

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
public class DepartmentAction extends ActionSupport implements ModelDriven<Department> {
    
    private Department department = new Department();
    @Resource(name="DepartmentService")
    private DepartmentService departmentService ;
    
    private List<Department> departments;
    
    @Override
    public Department getModel() {
	
	return department;
    }
    
    /**
     * @Description:列表页面
     * @return
     */
    public String listUI(){
	departments = departmentService.findAll();
	return "listUI";
    }
    
    /**
     * @Description:增加页面
     * @return
     */
    public String addUI(){
	
	return "addUI";
    }
    
    /**
     * @Description:增加实体
     * @return
     */
    public String add(){
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
	Department d = departmentService.findById(department.getDepartmentId());
	BeanUtils.copyProperties(d, department);
	return "editUI";
    }
    
    /**
     * @Description:修改实体
     * @return
     */
    public String edit(){
	departmentService.update(department);
	return "list";
    }


    
}
