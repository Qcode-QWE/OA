package cn.QEcode.dao;

import cn.QEcode.base.BaseDao;
import cn.QEcode.domain.Forum;

public interface ForumDao extends BaseDao<Forum> {
    /**
     * @Description:上移
     * @param id
     */
    public void moveUp(Long id);
    
    /**
     * @Description:下移
     * @param id
     */
    public void moveDown(Long id);
}
