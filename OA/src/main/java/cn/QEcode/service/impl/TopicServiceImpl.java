package cn.QEcode.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.QEcode.dao.TopicDao;
import cn.QEcode.domain.Forum;
import cn.QEcode.domain.Page;
import cn.QEcode.domain.Topic;
import cn.QEcode.service.ForumService;
import cn.QEcode.service.TopicService;

/**  
* <p>Title: TopicServiceImpl.java</p>  
* <p>Description:论坛主题业务层 </p>   
* @author Qcode  
* @date 2018年10月23日  
* @version 1.0  
*/ 
@Service("TopicService")
@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
public class TopicServiceImpl implements TopicService {

    @Resource(name="TopicDao")
    private TopicDao topicDao;
    @Resource(name="")
    private ForumService forumServicer;
    
    @Override
    public List<Topic> findAll() {
	return topicDao.findAll();
    }

    @Transactional(readOnly=false,propagation=Propagation.REQUIRED)
    public void delete(Long id) {
	topicDao.delete(id);
    }

    /**
     * @Description:新增论坛主题
     * @param entity
     */
    @Transactional(readOnly=false,propagation=Propagation.REQUIRED)
    public void save(Topic topic) {
	//最后更新时间为发帖时间
	topic.setLastUpdateTime(topic.getPostTime());
	topicDao.save(topic);
	//新增主题后,要修改forum的信息
	Forum forum = topic.getForum();
	//文章数
	forum.setArticleCount(forum.getArticleCount()+1);
	//主题数
	forum.setTopicCount(forum.getTopicCount()+1);
	//最后发表的主题
	forum.setLastTopic(topic);
	
	forumServicer.update(forum);
    }

    @Override
    public Topic findById(Long id) {
	return topicDao.findById(id);
    }

    @Transactional(readOnly=false,propagation=Propagation.REQUIRED)
    public void update(Topic entity) {
	
    }

    @Override
    public List<Topic> findByIds(Long[] entityIds) {
	// TODO 自动生成的方法存根
	return null;
    }

    /**
     * @Description:查询板块的主题,置顶帖在最上面,其余按时间排序
     * @param forum
     * @return
     */
    @Override
    public List<Topic> findByForum(Forum forum) {
	return topicDao.findByForum(forum);
    }

    /**
     * @Description:分页查询
     * @param pageNum
     * @param forum
     * @return
     */
    @Override
    public Page getPage(int pageNum, Forum forum) {
	String hql =  "from Topic where forum = ?  order by (case type when 2 then 2 else 0 end ) desc,last_update_time desc ";
	
	return topicDao.getPage(pageNum, hql, new Object[] {forum});
    }

}
