package cn.QEcode.service;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;

import cn.QEcode.base.BaseService;
import cn.QEcode.domain.Forum;
import cn.QEcode.domain.Page;
import cn.QEcode.domain.Topic;

public interface TopicService extends BaseService<Topic> {

    /**
     * @Description:查询板块下的主题
     * @param forum
     * @return
     */
    List<Topic> findByForum(Forum forum);

    /**
     * @Description:分页查询
     * @param pageNum
     * @param objects
     * @return
     */
    Page getPage(int pageNum, Object[] objects);

}
