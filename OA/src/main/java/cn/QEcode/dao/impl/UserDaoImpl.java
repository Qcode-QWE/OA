package cn.QEcode.dao.impl;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;
import org.springframework.util.DigestUtils;

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
	hibernateTemplate.update(user);
    }

    @Override
    public User findByLoginNameAndPassword(String loginName, String password) {

	List<User> users = (List<User>) hibernateTemplate.find("from User where loginName = ? and password = ?", loginName,DigestUtils.md5DigestAsHex(password.getBytes())); 
	User user = null;
	if(users.size()>0){
	    user = users.get(0);
	}
	return user;
    }
    
    
    
}
