package cn.QEcode.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import cn.QEcode.base.impl.BaseDaoImpl;
import cn.QEcode.dao.TopicDao;
import cn.QEcode.domain.Forum;
import cn.QEcode.domain.Page;
import cn.QEcode.domain.Reply;
import cn.QEcode.domain.Topic;

/**  
* <p>Title: TopicDaoImpl.java</p>  
* <p>Description:论坛主题Dao层 </p>   
* @author Qcode  
* @date 2018年10月23日  
* @version 1.0  
*/ 
@Repository("TopicDao")
public class TopicDaoImpl extends BaseDaoImpl<Topic> implements TopicDao {
    
    /**
     * @Description:查询板块的主题,置顶帖在最上面,其余按时间排序
     * @param forum
     * @return
     */
    @Override
    public List<Topic> findByForum(Forum forum) {
	
	return (List<Topic>) hibernateTemplate.find(
		"from Topic where forum = ?  order by (case type when 2 then 2 else 0 end ) desc,last_update_time desc ",forum);
    }
    
    public Page getPage(int pageNum,String hql,List<Object> patameters){
	
	//获取总页数
	DetachedCriteria detachedCriteria = DetachedCriteria.forClass(Topic.class);
	detachedCriteria.add(Restrictions.eq("forum", patameters.get(0)));
	detachedCriteria.setProjection(Projections.count("topicId"));
	Long pagesum = (Long) hibernateTemplate.findByCriteria(detachedCriteria).get(0);
	
	int sum = pagesum.intValue();
	// 构造page
	Page page = new Page(pageNum, sum);
	List<Topic> topics = (List<Topic>) hibernateTemplate
		.execute(new HibernateCallback<List<Topic>>() {
		    public List<Topic> doInHibernate(Session session)
			    throws HibernateException {
			Query query = session.createQuery(hql);
			if(patameters!=null && patameters.size() > 0){	
			    System.out.println(patameters.get(0));
			    query.setParameter(0, patameters.get(0));
			}
			query.setFirstResult(page.getStartIndex());
			query.setMaxResults(page.getPageSize());
			return query.list();
		    }
		});
	page.setRecords(topics);

	return page;
	
    }
}
