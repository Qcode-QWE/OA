package cn.QEcode.service;

import java.util.List;

import cn.QEcode.base.BaseService;
import cn.QEcode.domain.Forum;
import cn.QEcode.domain.Topic;

public interface TopicService extends BaseService<Topic> {

    /**
     * @Description:查询板块下的主题
     * @param forum
     * @return
     */
    List<Topic> findByForum(Forum forum);

}
