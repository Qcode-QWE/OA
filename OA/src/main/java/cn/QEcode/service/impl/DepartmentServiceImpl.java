package cn.QEcode.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.filefilter.FalseFileFilter;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.QEcode.dao.DepartmentDao;
import cn.QEcode.domain.Department;
import cn.QEcode.service.DepartmentService;

@Service("DepartmentService")
@Transactional(readOnly=true,propagation = Propagation.REQUIRED)
public class DepartmentServiceImpl implements DepartmentService {

    @Resource(name = "DepartmentDao")
    private DepartmentDao departmentDao;
    /**
     * @Description:查询所有部门
     * @return
     */
    public List<Department> findAll() {
	return departmentDao.findAll();
    }
    
    
    /**
     * @Description:保存部门
     * @param department
     */
    @Transactional(readOnly=false,propagation=Propagation.REQUIRED)
    public void add(Department department) {
	departmentDao.save(department);
    }


    /**
     * @Description:删除部门
     * @param department
     */
    @Override
    public void delete(Department department) {
	departmentDao.delete(department);
    }


    /**
     * @Description:根据id查询部门
     * @param departmentId
     * @return
     */
    @Override
    public Department findById(Long departmentId) {
	
	return departmentDao.findById(departmentId);
    }


    /**
     * @Description:修改部门
     * @param department
     */
    @Transactional(readOnly=false,propagation=Propagation.REQUIRED)
    public void update(Department department) {
	departmentDao.update(department);
    }


    /**
     * @Description:获取部门名称列表
     * @return
     */
    @Override
    public List<Department> getName() {
	return departmentDao.getName();
    }

}
