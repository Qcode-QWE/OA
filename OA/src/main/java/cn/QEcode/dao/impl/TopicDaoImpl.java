package cn.QEcode.dao.impl;

import java.util.List;

import org.springframework.stereotype.Repository;

import cn.QEcode.base.impl.BaseDaoImpl;
import cn.QEcode.dao.TopicDao;
import cn.QEcode.domain.Forum;
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

}
