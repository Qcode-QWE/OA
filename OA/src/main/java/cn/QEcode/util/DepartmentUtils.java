package cn.QEcode.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import cn.QEcode.domain.Department;

public class DepartmentUtils {
    
    /**
     * @Description:获取部门的树状结构
     */
    public static List<Department> getTree(List<Department> toList) {
	List<Department> list = new ArrayList<Department>();
	walkTree(toList, list, "　");
	
	return list;
    }
    
    /**
     * @Description:递归显示树
     * @param toList
     * @param list
     * @param prefix
     */
    public static void walkTree(Collection<Department> toList,List<Department> list,String prefix){
	
	for (Department department : toList) {
	    /**
	     * 由于当前session并没有关闭,如果直接修改持久化对象department的name,则name属性在数据库中会被更新
	     * 这是由于修改name的时候,在一级缓存中的name也会被修改,当session关闭的时候,name属性就会被提交到数据库
	     */
	    Department copy = new Department();
	    copy.setDepartmentId(department.getDepartmentId());
	    copy.setName(prefix +"-" + department.getName());
	    list.add(copy);
	    
	    //递归子树
	    walkTree(department.getChildren(), list, "　"+prefix);
	    
	}
    }
    
    
}
