package cn.QEcode.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import cn.QEcode.base.BaseDao;
import cn.QEcode.domain.Page;
import cn.QEcode.domain.Role;

public interface RoleDao extends BaseDao<Role> {

    /**
     * @Description:分页查询
     * @param pageNum
     * @param detachedCriteria
     * @return
     */
    Page getPage(int pageNum, DetachedCriteria detachedCriteria);


}
