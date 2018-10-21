package cn.QEcode.base;

import java.util.List;


public interface BaseService<T> {
    
    /**
     * @Description:查询所有
     * @return
     */
    public List<T> findAll();
    
    /**
     * @Description:删除
     * @param role
     */
    public void delete(Long id);

    /**
     * @Description:增加
     * @param role
     */
    public void save(T entity);

    /**
     * @Description:根据id查询
     * @param roleId
     * @return
     */
    public T findById(Long id);

    /**
     * @Description:更新职位
     * @param role
     */
    public void update(T entity);

    /**
     * @Description:根据id数组查询职位
     * @param roleIds
     * @return
     */
    public List<T> findByIds(Long[] entityIds);
    
}
