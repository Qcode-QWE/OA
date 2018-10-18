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

}
