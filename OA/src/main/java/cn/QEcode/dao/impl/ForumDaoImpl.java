package cn.QEcode.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.stereotype.Repository;

import cn.QEcode.base.impl.BaseDaoImpl;
import cn.QEcode.dao.ForumDao;
import cn.QEcode.domain.Forum;
import cn.QEcode.service.ForumService;

@Repository("ForumDao")
public class ForumDaoImpl extends BaseDaoImpl<Forum> implements ForumDao {

    /**
     * @Description:升序查询
     * @return
     */
    @Override
    public List<Forum> findAll() {
	return (List<Forum>) hibernateTemplate.find("from Forum order by position");
    }

    /**
     * @Description:在添加板块时,指定position值
     * @param entity
     */
    @Override
    public void save(Forum entity) {
	//得到id
	hibernateTemplate.save(entity);
	//利用session的一级缓存特性,在session关闭时,自动更新
	entity.setPosition(entity.getForumId().intValue());
    }

    /**
     * @Description:上移
     * @param id
     */
    public void moveUp(Long id){
	
	Forum forum = findById(id);
	Forum other = (Forum) hibernateTemplate.execute(
		new HibernateCallback<Forum>() {
		    @Override
		    public Forum doInHibernate(Session session)
			    throws HibernateException {
			String hql = "from Forum where position < "+forum.getPosition()+" order by position desc";
			Query query = session.createQuery(hql);
			query.setFirstResult(0);
			query.setMaxResults(1);
			return (Forum) query.list().get(0);
		    }   
		}
		);
	if(other==null){
	    return ;
	}
	int temp = forum.getPosition();
	forum.setPosition(other.getPosition());
	other.setPosition(temp);
    }
    
    /**
     * @Description:下移
     * @param id
     */
    public void moveDown(Long id){
	Forum forum = findById(id);
	Forum other = (Forum) hibernateTemplate.execute(
		new HibernateCallback<Forum>() {

		    @Override
		    public Forum doInHibernate(Session session)
			    throws HibernateException {
			String hql = "from Forum where position > "+forum.getPosition()+" order by position ";
			Query query = session.createQuery(hql);
			query.setFirstResult(0);
			query.setMaxResults(1);
			return (Forum) query.list().get(0);
		    }   
		}
		);
	if(other==null){
	    return ;
	}
	int temp = forum.getPosition();
	forum.setPosition(other.getPosition());
	other.setPosition(temp);
    }

}
