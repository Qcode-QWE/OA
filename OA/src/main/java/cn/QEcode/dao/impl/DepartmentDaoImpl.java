package cn.QEcode.dao.impl;

import org.springframework.stereotype.Repository;

import cn.QEcode.base.impl.BaseDaoImpl;
import cn.QEcode.dao.DepartmentDao;
import cn.QEcode.domain.Department;

@Repository("DepartmentDao")
public class DepartmentDaoImpl extends BaseDaoImpl<Department> implements DepartmentDao {

}
