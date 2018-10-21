package cn.QEcode.dao;

import java.util.List;

import cn.QEcode.base.BaseDao;
import cn.QEcode.domain.Privilege;

public interface PrivilegeDao extends BaseDao<Privilege> {

    /**
     * @Description:查询顶级权限
     * @returnT
     */
    List<Privilege> findTopList();
    /**
     * @Description:查询所有权限url,且不为空
     * @return
     */
    List<Privilege> findAllPrivileges();
    
}
