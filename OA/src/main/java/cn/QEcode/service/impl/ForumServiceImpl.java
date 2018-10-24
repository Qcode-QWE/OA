package cn.QEcode.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.QEcode.dao.ForumDao;
import cn.QEcode.domain.Forum;
import cn.QEcode.service.ForumService;

@Service("ForumService")
@Transactional(readOnly=true,propagation=Propagation.REQUIRED)
public class ForumServiceImpl implements ForumService {

    @Resource(name="ForumDao")
    private ForumDao forumDao;
    
    /**
     * @Description:查询论坛板块列表
     * @return
     */
    @Override
    public List<Forum> findAll() {
	return forumDao.findAll();
    }

    /**
     * @Description:删除论坛板块
     * @param id
     */
    @Transactional(readOnly=false,propagation=Propagation.REQUIRED)
    public void delete(Long id) {
	forumDao.delete(id);
    }

    /**
     * @Description:保存论坛板块
     * @param entity
     */
    @Transactional(readOnly=false,propagation=Propagation.REQUIRED)
    public void save(Forum entity) {
	forumDao.save(entity);
    }

    /**
     * @Description:根据id查询论坛板块
     * @param id
     * @return
     */
    @Override
    public Forum findById(Long id) {
	
	return forumDao.findById(id);
    }

    /**
     * @Description:更新论坛板块
     * @param entity
     */
    @Transactional(readOnly=false,propagation=Propagation.REQUIRED)
    public void update(Forum entity) {
	forumDao.update(entity);
	
    }

    @Override
    public List<Forum> findByIds(Long[] entityIds) {
	
	return forumDao.findByIds(entityIds);
    }

    /**
     * @Description:上移
     * @param forumId
     */
    @Transactional(readOnly=false,propagation=Propagation.REQUIRED)
    public void moveUp(Long forumId) {
	// TODO 自动生成的方法存根
	forumDao.moveUp(forumId);
    }
    /**
     * @Description:下移
     * @param forumId
     */
    @Transactional(readOnly=false,propagation=Propagation.REQUIRED)
    public void moveDown(Long forumId) {
	// TODO 自动生成的方法存根
	forumDao.moveDown(forumId);
    }

}
