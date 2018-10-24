package cn.QEcode.service;

import cn.QEcode.base.BaseService;
import cn.QEcode.domain.Forum;

public interface ForumService extends BaseService<Forum> {

    /**
     * @Description:上移
     * @param forumId
     */
    void moveUp(Long forumId);
    /**
     * @Description:下移
     * @param forumId
     */
    void moveDown(Long forumId);
    
}
