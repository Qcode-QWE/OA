package cn.QEcode.dao;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import cn.QEcode.base.BaseDao;
import cn.QEcode.base.impl.BaseDaoImpl;
import cn.QEcode.domain.Page;
import cn.QEcode.domain.User;

public interface UserDao extends BaseDao<User> {

    /**
     * @Description:初始化密码
     */
    void initPassword(User user);

    /**
     * @Description:登陆
     * @param loginName
     * @param password
     * @return
     */
    User findByLoginNameAndPassword(String loginName, String password);

    /**
     * @Description:根据登陆名获取密码
     * @param loginName
     * @return
     */
    User findPasswordByName(String loginName);

    /**
     * @Description:分页查询
     * @param pageNum
     * @param hql
     * @param patameters
     * @return
     */
    //public Page getPage(int pageNum,String hql,List<Object> patameters);
    public Page getPage(int pageNum,DetachedCriteria  detachedCriteria);
}
