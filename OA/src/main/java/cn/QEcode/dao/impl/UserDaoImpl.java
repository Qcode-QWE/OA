package cn.QEcode.dao.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;
import org.springframework.util.DigestUtils;

import cn.QEcode.base.BaseDao;
import cn.QEcode.base.impl.BaseDaoImpl;
import cn.QEcode.dao.UserDao;
import cn.QEcode.domain.Page;
import cn.QEcode.domain.Role;
import cn.QEcode.domain.Topic;
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

    /**
     * @Description:根据登陆名获取密码
     * @param loginName
     * @return
     */
    @Override
    public User findPasswordByName(String loginName) {
	return (User) hibernateTemplate.find("from User where loginName = ?",loginName).get(0);
    }

    /**
     * @Description:
     * @param pageNum
     * @param hql
     * @param patameters
     * @return
     */
    @Override
    public Page getPage(int pageNum,DetachedCriteria detachedCriteria) {
	detachedCriteria.setProjection(Projections.count("userId"));
	Long pagesum = (Long) hibernateTemplate.findByCriteria(detachedCriteria).get(0);
	int sum = pagesum.intValue();
	// 构造page
	Page page = new Page(pageNum, sum);
	
	Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
	detachedCriteria.setProjection(null);
	Criteria criteria = detachedCriteria.getExecutableCriteria(session);
	criteria.setProjection((Projections.distinct(Property.forName("userId"))));
	List<User> userList = criteria.setFirstResult(page.getStartIndex()).setMaxResults(page.getPageSize()).list();
	//重新构建条件
	Criteria criteria2 = session.createCriteria(User.class);
	List<User> users = new ArrayList<User>();
	//查询所有userId在userList里的user
	if(userList!=null&&userList.size()>0){
	    criteria2.add(Restrictions.in("userId",userList));
	    //去重
	    criteria2.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	    //查询
	    users = criteria2.list();
	}	
	page.setRecords(users);
	return page;
    }
    
    
    
}
