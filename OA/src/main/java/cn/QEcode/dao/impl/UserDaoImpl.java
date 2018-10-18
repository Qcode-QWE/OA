package cn.QEcode.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.QEcode.base.BaseDao;
import cn.QEcode.base.impl.BaseDaoImpl;
import cn.QEcode.dao.UserDao;
import cn.QEcode.domain.User;

@Repository("UserDao")
public class UserDaoImpl extends BaseDaoImpl<User> implements UserDao {
    
    /**
     * @Description:初始化密码
     */
    @Override
    public void initPassword(User user) {
	
    }
    
    
    
}
