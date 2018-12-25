package cn.QEcode.dao.impl;

import java.util.List;

import org.apache.solr.client.solrj.request.CollectionAdminRequest.Create;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import cn.QEcode.base.impl.BaseDaoImpl;
import cn.QEcode.dao.ReplyDao;
import cn.QEcode.domain.Forum;
import cn.QEcode.domain.Page;
import cn.QEcode.domain.Reply;
import cn.QEcode.domain.Topic;
import cn.QEcode.service.ReplyService;

@Repository("ReplyDao")
public class ReplyDaoImpl extends BaseDaoImpl<Reply> implements ReplyDao {

    /**
     * @Description::查询主题的回帖,按时间排序
     * @param topic
     * @return
     */
    public List<Reply> findByTopic(Topic topic) {
	
	return (List<Reply>) hibernateTemplate.find("from Reply where topic = ? order by postTime",topic);
    }
    
    /**
     * @Description:分页查询
     * @param pageNum
     * @param hql
     * @param patameters
     * @return
     */
    public Page getPage(int pageNum,String hql,List<Object> patameters){
	//获取总页数
	DetachedCriteria detachedCriteria = DetachedCriteria
		.forClass(Reply.class);
	detachedCriteria.add(Restrictions.eq("topic", patameters.get(0)));
	detachedCriteria.setProjection(Projections.count("replyId"));
	Long pagesum = (Long) hibernateTemplate
		.findByCriteria(detachedCriteria).get(0);

	int sum = pagesum.intValue();
	// 构造page
	Page page = new Page(pageNum, sum);
	List<Topic> topics = (List<Topic>) hibernateTemplate
		.execute(new HibernateCallback<List<Topic>>() {
		    public List<Topic> doInHibernate(Session session)
			    throws HibernateException {
			Query query = session.createQuery(hql);
			if (patameters != null && patameters.size() > 0) {
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

    /**
     * @Description:获取该回帖在第几页
     * @param reply
     * @return
     */
    @Override
    public int getFindPage(Reply reply) {
	//获取该回帖在第几页
	DetachedCriteria detachedCriteria = DetachedCriteria
		.forClass(Reply.class);
	//查询在该回帖前有多少个回帖
	detachedCriteria.add(Restrictions.eq("topic",reply.getTopic()));
	detachedCriteria.setProjection(Projections.count("replyId"));
	detachedCriteria.add(Restrictions.le("replyId",reply.getReplyId()));
	Long pagesum = (Long) hibernateTemplate
		.findByCriteria(detachedCriteria).get(0);
	
	//计算该回帖在第几页
	int pagenum = (int) (pagesum%5==0?(pagesum/5):pagesum/5+1);	
	return pagenum;
    }

  
    
    
}
