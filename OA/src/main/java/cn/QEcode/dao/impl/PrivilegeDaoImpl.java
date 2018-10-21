package cn.QEcode.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.QEcode.base.impl.BaseDaoImpl;
import cn.QEcode.dao.PrivilegeDao;
import cn.QEcode.domain.Privilege;

@Repository("PrivilegeDao")
public class PrivilegeDaoImpl extends BaseDaoImpl<Privilege> implements PrivilegeDao  {

    /**
     * @Description:查询顶级权限
     * @return
     */
    
    public List<Privilege> findTopList() {
	
	return (List<Privilege>) hibernateTemplate.find(" from Privilege where parent is null");
    }

}
