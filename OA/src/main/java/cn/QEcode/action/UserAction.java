package cn.QEcode.action;

import org.springframework.stereotype.Controller;


/**  
* <p>Title: UserAction.java</p>  
* <p>Description:User的Action </p>   
* @author Qcode  
* @date 2018年10月16日  
* @version 1.0  
*/ 
@Controller("userAction")
public class UserAction {
   
    /**
     * @Description:列表页面
     * @return
     */
    public String listUI(){
	
	
	return "listUI";
    }
    
    /**
     * @Description:增加页面
     * @return
     */
    public String addUI(){
	
	return "addUI";
    }
    
    /**
     * @Description:增加实体
     * @return
     */
    public String add(){
	
	return "list";
    }
    
    /**
     * @Description:删除实体
     * @return
     */
    public String delete(){
	
	return "list";
    }
    
    /**
     * @Description:修改页面
     * @return
     */
    public String editUI(){
	
	return "editUI";
    }
    
    /**
     * @Description:修改实体
     * @return
     */
    public String edit(){
	
	return "list";
    }
    
    
    
    
}
