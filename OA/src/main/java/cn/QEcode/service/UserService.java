package cn.QEcode.service;

import java.util.List;
import java.util.Set;

import cn.QEcode.domain.Department;
import cn.QEcode.domain.Page;
import cn.QEcode.domain.Role;
import cn.QEcode.domain.User;

public interface UserService {

    /**
     * @Description:查询所有用户
     * @return
     */
    public List<User> findAll();
    
    public Page getPage(int pageNum,String loginName,List<Role> roles,Department department);
    
    /**
     * @Description:删除用户
     * @param 
     */
    public void delete(Long id);

    /**
     * @Description:增加用户
     * @param 
     */
    public void save(User user);

    /**
     * @Description:根据id查询用户
     * @param 
     * @return
     */
    public User findById(Long userId);

    /**
     * @Description:更新用户
     * @param 
     */
    public void update(User user);

    /**
     * @Description:初始化密码
     */
    public void initPassword(User user);

    /**
     * @Description:登陆
     * @param loginName
     * @param password
     * @return
     */
    public User findByLoginNameAndPassword(String loginName, String password);

    
    
    /**
     * @Description:根据登陆名获取密码
     * @param loginName
     * @return
     */
    public User findPasswordByName(String loginName);
    
}
