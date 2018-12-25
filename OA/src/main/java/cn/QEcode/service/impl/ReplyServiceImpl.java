package cn.QEcode.service.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.QEcode.dao.ReplyDao;
import cn.QEcode.domain.Forum;
import cn.QEcode.domain.Page;
import cn.QEcode.domain.Reply;
import cn.QEcode.domain.Topic;
import cn.QEcode.service.ForumService;
import cn.QEcode.service.ReplyService;
import cn.QEcode.service.TopicService;

/**  
* <p>Title: ReplyServiceImpl.java</p>  
* <p>Description:论坛回帖的业务层 </p>   
* @author Qcode  
* @date 2018年10月23日  
* @version 1.0  
*/ 
@Repository("ReplyService")
@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
public class ReplyServiceImpl implements ReplyService {
    @Resource(name="ReplyDao")
    private ReplyDao replyDao;
    @Resource(name="ForumService")
    private ForumService forumService;
    @Resource(name="TopicService")
    private TopicService topicService;
    
    public List<Reply> findAll() {
	
	return replyDao.findAll();
    }

    @Transactional(readOnly=false,propagation=Propagation.REQUIRED)
    public void delete(Long id) {
	// TODO 自动生成的方法存根
	
    }

    /**
     * @Description:新增回帖
     * @param entity
     */
    @Transactional(readOnly=false,propagation=Propagation.REQUIRED)
    public void save(Reply entity) {
	replyDao.save(entity);
	
	Topic topic = entity.getTopic();
	Forum forum = topic.getForum();
	
	//主题数加回帖数
	forum.setArticleCount(forum.getArticleCount()+1);
	//主题的回帖数
	topic.setReplyCount(topic.getReplyCount()+1);
	//主题最后发表的回复
	topic.setLastReply(entity);
	//主题的更新时间
	topic.setLastUpdateTime(entity.getPostTime());
	
	topicService.update(topic);
	forumService.update(forum);
    }

    @Override
    public Reply findById(Long id) {
	return replyDao.findById(id);
    }

    @Transactional(readOnly=false,propagation=Propagation.REQUIRED)
    public void update(Reply entity) {
	// TODO 自动生成的方法存根
	
    }

    @Override
    public List<Reply> findByIds(Long[] entityIds) {
	// TODO 自动生成的方法存根
	return null;
    }
    
    /**
     * @Description:查询回帖的列表,按时间排序
     * @param topic
     * @return
     */
    public List<Reply> findByTopic(Topic topic){
	return replyDao.findByTopic(topic);
    }

    /**
     * @Description:分页查询回帖
     * @param pageNum
     * @param topic
     * @return
     */
    @Override
    public Page getPage(int pageNum, Topic topic) {
	String hql  = "from Reply where topic = ? order by postTime";
	List<Object> objects = new ArrayList<Object>();
	objects.add(topic);
	return replyDao.getPage(pageNum,hql,objects);
    }

    @Override
    public Page getFindPage(Reply reply) {
	//获取该回帖在第几页
	int pagenum = replyDao.getFindPage(reply);
	Topic topic = topicService.findById(reply.getTopic().getTopicId());
	Page page = getPage(pagenum,topic);
	return page;
    }
    
    
}
