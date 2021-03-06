package cn.QEcode.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.QEcode.base.impl.BaseDaoImpl;
import cn.QEcode.dao.DepartmentDao;
import cn.QEcode.domain.Department;

@Repository("DepartmentDao")
public class DepartmentDaoImpl extends BaseDaoImpl<Department> implements DepartmentDao {

    /**
     * @Description:获取部门名称列表
     * @return
     */
    @Override
    public List<Department> getName() {
	return (List<Department>) hibernateTemplate.find("select new Department(departmentId,name) from Department");
    }

    /**
     * @Description:获取顶级部门列表
     * @return
     */
    @Override
    public List<Department> findToList() {
	
	return (List<Department>) hibernateTemplate.find("from Department where parent is null");
    }

    /**
     * @Description:查询子级部门
     * @param parentId
     * @return
     */
    @Override
    public List<Department> findChildren(Long parentId) {
	
	return (List<Department>) hibernateTemplate.find("from Department where parent.departmentId = ? ",parentId);
    }

}
