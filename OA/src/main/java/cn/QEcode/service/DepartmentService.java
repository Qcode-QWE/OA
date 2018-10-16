package cn.QEcode.service;

import java.util.List;

import cn.QEcode.domain.Department;

public interface DepartmentService {

    /**
     * @Description:查询所有部门
     * @return
     */
    List<Department> findAll();

    /**
     * @Description:保存部门
     * @param department
     */
    void add(Department department);

    /**
     * @Description:删除部门
     * @param department
     */
    void delete(Department department);

    /**
     * @Description:根据id查询部门
     * @param departmentId
     * @return
     */
    Department findById(Long departmentId);

    /**
     * @Description:修改部门
     * @param department
     */
    void update(Department department);

   

}
