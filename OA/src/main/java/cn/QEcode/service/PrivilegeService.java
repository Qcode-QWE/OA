package cn.QEcode.service;

import java.util.List;

import cn.QEcode.base.BaseService;
import cn.QEcode.domain.Privilege;
import cn.QEcode.domain.Role;

/**  
* <p>Title: PrivilegeService.java</p>  
* <p>Description:权限的业务层 </p>   
* @author Qcode  
* @date 2018年10月20日  
* @version 1.0  
*/ 
public interface PrivilegeService extends BaseService<Privilege> {

    /**
     * @Description:查找顶级菜单
     * @return
     */
    List<Privilege> findtopList();
    
    
}
