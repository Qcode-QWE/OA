package cn.QEcode.service;

import java.util.List;

import cn.QEcode.base.BaseService;
import cn.QEcode.domain.Page;
import cn.QEcode.domain.Reply;
import cn.QEcode.domain.Topic;

/**  
* <p>Title: ReplyService.java</p>  
* <p>Description:论坛回帖的业务层 </p>   
* @author Qcode  
* @date 2018年10月23日  
* @version 1.0  
*/ 
public interface ReplyService extends BaseService<Reply> {
    
    /**
     * @Description:查询回帖的列表,按时间排序
     * @param topic
     * @return
     */
    public List<Reply> findByTopic(Topic topic);

    /**
     * @Description:分页查询回帖
     * @param pageNum
     * @param topic
     * @return
     */
    public Page getPage(int pageNum, Topic topic);
    
    /**
     * @Description:查找查询结果
     * @param topic
     * @param reply
     * @return
     */
    public Page getFindPage(Reply reply);
    
    
}
