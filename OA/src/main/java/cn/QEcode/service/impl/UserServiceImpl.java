package cn.QEcode.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.QEcode.dao.UserDao;
import cn.QEcode.domain.Role;
import cn.QEcode.domain.User;
import cn.QEcode.service.UserService;

@Service("UserService")
@Transactional(readOnly=true,propagation = Propagation.REQUIRED)
public class UserServiceImpl implements UserService {
    @Resource(name="UserDao")
    private UserDao userDao;
    
    /**
     * @Description:查询所有用户
     * @return
     */
    public List<User> findAll(){
	return userDao.findAll();
    }
    
    /**
     * @Description:删除用户
     * @param 
     */
    @Transactional(readOnly=false,propagation=Propagation.REQUIRED)
    public void delete(Long id){
	userDao.delete(id);
    }

    /**
     * @Description:增加用户
     * @param 
     */
    @Transactional(readOnly=false,propagation=Propagation.REQUIRED)
    public void save(User user){
	userDao.save(user);
    }

    /**
     * @Description:根据id查询用户
     * @param 
     * @return
     */
    public User findById(Long userId){
	return userDao.findById(userId);
    }

    /**
     * @Description:更新用户
     * @param 
     */
    @Transactional(readOnly=false,propagation=Propagation.REQUIRED)
    public void update(User user){
	userDao.update(user);
    }

    /**
     * @Description:初始化密码
     */
    @Transactional(readOnly=false,propagation=Propagation.REQUIRED)
    public void initPassword(User user) {
	userDao.initPassword(user);
    }

    /**
     * @Description:登陆
     * @param loginName
     * @param password
     * @return
     */
    @Override
    public User findByLoginNameAndPassword(String loginName, String password) {
	return userDao.findByLoginNameAndPassword(loginName,password);
    }
    
    /**
     * @Description:根据登陆名获取密码
     * @param loginName
     * @return
     */
    public User findPasswordByName(String loginName){
	return userDao.findPasswordByName(loginName);
    }
    

}
