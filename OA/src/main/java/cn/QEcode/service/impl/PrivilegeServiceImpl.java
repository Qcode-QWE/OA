package cn.QEcode.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.QEcode.dao.PrivilegeDao;
import cn.QEcode.domain.Privilege;
import cn.QEcode.service.PrivilegeService;

@Service("PrivilegeService")
@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
public class PrivilegeServiceImpl implements PrivilegeService {
    
    @Resource(name="PrivilegeDao")
    private PrivilegeDao privilegeDao;
    /**
     * @Description:查询所有权限
     * @return
     */
    public List<Privilege> findAll(){
	return privilegeDao.findAll();
    }
    
    /**
     * @Description:删除权限
     * @param role
     */
    public void delete(Long id){
	privilegeDao.delete(id);
    }

    /**
     * @Description:增加权限
     * @param role
     */
    public void save(Privilege privilege){
	privilegeDao.save(privilege);
    }

    /**
     * @Description:根据id查询权限
     * @param privilegeId
     * @return
     */
    public Privilege findById(Long privilegeId){
	return privilegeDao.findById(privilegeId);
    }

    /**
     * @Description:更新权限
     * @param 
     */
    public void update(Privilege privilege){
	privilegeDao.update(privilege);
    }

    /**
     * @Description:根据id数组查询权限
     * @param Ids
     * @return
     */
    public List<Privilege> findByIds(Long[] privilegeIds){
	return privilegeDao.findByIds(privilegeIds);
    }

    /**
     * @Description:查询顶级权限
     * @return
     */
    @Override
    public List<Privilege> findtopList() {
	return privilegeDao.findTopList();
    }
    
}
