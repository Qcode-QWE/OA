package cn.QEcode.dao;

import java.util.List;

import cn.QEcode.base.BaseDao;
import cn.QEcode.domain.Page;
import cn.QEcode.domain.Reply;
import cn.QEcode.domain.Topic;

public interface ReplyDao extends BaseDao<Reply> {

    /**
     * @Description::查询主题的回帖,置顶帖在最上面,其余按时间排序
     * @param topic
     * @return
     */
    List<Reply> findByTopic(Topic topic);


}
