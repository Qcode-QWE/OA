package cn.QEcode.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.io.filefilter.FalseFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.QEcode.dao.RoleDao;
import cn.QEcode.domain.Page;
import cn.QEcode.domain.Role;
import cn.QEcode.service.RoleService;

@Service("RoleService")
@Transactional(readOnly=true,propagation = Propagation.REQUIRED)
public class RoleServiceImpl implements RoleService {
    
    @Resource(name="RoleDao")
    private RoleDao roleDao;
    
    /**
     * @Description:查询所有用户
     * @return
     */
    public List<Role> findAll() {
	return roleDao.findAll();
    }

    
    /**
    * @Description:删除职位
    * @param role
    */
    @Transactional(readOnly=false,propagation=Propagation.REQUIRED)
    public void delete(Long id) {
	roleDao.delete(id);
    }


    /**
     * @Description:增加职位
     * @param role
     */
    @Transactional(readOnly=false,propagation=Propagation.REQUIRED)
    public void save(Role role) {
	roleDao.save(role);
    }


    /**
     * @Description:根据id查询职位
     * @param roleId
     * @return
     */
    public Role findById(Long roleId) {
	
	return roleDao.findById(roleId);
    }

    /**
     * @Description:更新职位
     * @param role
     */
    @Transactional(readOnly=false,propagation=Propagation.REQUIRED)
    public void update(Role role) {
	roleDao.update(role);
    }


    /**
     * @Description:根据id数组查询职位
     * @param roleIds
     * @return
     */
    @Override
    public List<Role> findByIds(Long[] roleIds) {
	return roleDao.findByIds(roleIds);
    }


    /**
     * @Description:分页查询
     * @param pageNum
     * @param roleName
     * @param roleDescription
     * @return
     */
    @Override
    public Page getPage(int pageNum, String roleName, String roleDescription) {
	DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Role.class);
	if(StringUtils.isNotBlank(roleName)){
	    detachedCriteria.add(Restrictions.like("name", "%"+roleName+"%"));
	}
	if(StringUtils.isNotBlank(roleDescription)){
	    detachedCriteria.add(Restrictions.like("description", "%"+roleDescription+"%"));
	}
	detachedCriteria.addOrder(Order.asc("roleId"));
	Page page = roleDao.getPage(pageNum,detachedCriteria);
	
	return page;
    }
}
