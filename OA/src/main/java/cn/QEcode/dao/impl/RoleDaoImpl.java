package cn.QEcode.dao.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import cn.QEcode.base.impl.BaseDaoImpl;
import cn.QEcode.dao.RoleDao;
import cn.QEcode.domain.Page;
import cn.QEcode.domain.Role;

@Repository("RoleDao")
public class RoleDaoImpl extends BaseDaoImpl<Role> implements RoleDao {

    /**
     * @Description:分页查询
     * @param pageNum
     * @param detachedCriteria
     * @return
     */
    @Override
    public Page getPage(int pageNum, DetachedCriteria detachedCriteria) {
	
	//获取总数
	detachedCriteria.setProjection(Projections.count("roleId"));
	Long pageSum = (Long) hibernateTemplate.findByCriteria(detachedCriteria).get(0);
	int sum = pageSum.intValue();
	detachedCriteria.setProjection(null);
	//构造page
	Page page = new Page(pageNum, sum);
	Session session = hibernateTemplate.getSessionFactory().getCurrentSession();
	
	//获取前5条 不重复的role的id
	Criteria criteria = detachedCriteria.getExecutableCriteria(session);
	criteria.addOrder(Order.asc("roleId"));
	criteria.setProjection((Projections.distinct(Property.forName("roleId"))));
	List<Role> roleList =  criteria.setFirstResult(page.getStartIndex()).setMaxResults(page.getPageSize()).list();
	//重新构造条件
	List<Role> roles = new ArrayList<Role>();
	Criteria criteria2 = session.createCriteria(Role.class);
	if(roleList!=null&&roleList.size()>0){
	   //查询id为roleList的role
	   criteria2.add(Restrictions.in("roleId", roleList));
	   //去重
	   criteria2.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
	   //查询 
	    roles = criteria2.list();
	}
	page.setRecords(roles);
	return page;
    }


    
}
