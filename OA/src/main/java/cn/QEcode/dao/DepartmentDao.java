package cn.QEcode.dao;

import java.util.List;

import cn.QEcode.base.BaseDao;
import cn.QEcode.domain.Department;

public interface DepartmentDao extends BaseDao<Department> {

    /**
     * @Description:获取部门名称列表
     * @return
     */
    List<Department> getName();

    /**
     * @Description:获取顶级部门
     * @return
     */
    List<Department> findToList();

    /**
     * @Description:查询子级部门
     * @param parentId
     * @return
     */
    List<Department> findChildren(Long parentId);

}
