package cn.QEcode.service;

import java.util.List;

import cn.QEcode.domain.Page;
import cn.QEcode.domain.Role;

public interface RoleService {
    
    
    /**
     * @Description:查询所有职位
     * @return
     */
    public List<Role> findAll();
    
    /**
     * @Description:删除职位
     * @param role
     */
    public void delete(Long id);

    /**
     * @Description:增加职位
     * @param role
     */
    public void save(Role role);

    /**
     * @Description:根据id查询职位
     * @param roleId
     * @return
     */
    public Role findById(Long roleId);

    /**
     * @Description:更新职位
     * @param role
     */
    public void update(Role role);

    /**
     * @Description:根据id数组查询职位
     * @param roleIds
     * @return
     */
    public List<Role> findByIds(Long[] roleIds);

    /**
     * @Description:分页查询
     * @param pageNum
     * @param roleName
     * @param roleDescription
     * @return
     */
    public Page getPage(int pageNum, String roleName, String roleDescription);


}
