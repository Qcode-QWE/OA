package cn.QEcode.dao.impl;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;
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


  
    
    
}
