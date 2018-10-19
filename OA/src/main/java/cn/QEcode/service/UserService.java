package cn.QEcode.service;

import java.util.List;

import cn.QEcode.domain.Role;
import cn.QEcode.domain.User;

public interface UserService {

    /**
     * @Description:查询所有用户
     * @return
     */
    public List<User> findAll();
    
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

}
