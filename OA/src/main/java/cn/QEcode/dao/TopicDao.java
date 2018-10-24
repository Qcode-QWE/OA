package cn.QEcode.dao;

import java.util.List;

import cn.QEcode.base.BaseDao;
import cn.QEcode.domain.Forum;
import cn.QEcode.domain.Page;
import cn.QEcode.domain.Topic;

public interface TopicDao extends BaseDao<Topic>{
    
    /**
     * @Description:查询板块的主题,按时间排序
     * @param forum
     * @return
     */
    List<Topic> findByForum(Forum forum);


}
