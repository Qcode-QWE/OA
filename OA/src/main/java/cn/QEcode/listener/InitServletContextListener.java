package cn.QEcode.listener;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.context.ApplicationContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import com.opensymphony.xwork2.ActionContext;

import cn.QEcode.domain.Privilege;
import cn.QEcode.service.PrivilegeService;
import cn.QEcode.service.impl.PrivilegeServiceImpl;

/**  
* <p>Title: InitServletContextListener.java</p>  
* <p>Description: 监听器,用于程序启动时初始化顶级菜单</p>   
* @author Qcode  
* @date 2018年10月20日  
* @version 1.0  
*/ 
public class InitServletContextListener implements ServletContextListener {

    /**
     * @Description:初始化
     * @param sce
     */
 
    public void contextInitialized(ServletContextEvent sce) {
	
	ServletContext application = sce.getServletContext();
	//得到service实例
	ApplicationContext ac = WebApplicationContextUtils.getWebApplicationContext(application);
	PrivilegeService privilegeService = (PrivilegeService) ac.getBean("PrivilegeService");
	
	//获取顶级菜单
	List<Privilege> topPrivileges = privilegeService.findtopList();
	application.setAttribute("topPrivileges", topPrivileges);
	
	System.out.println("-------顶级菜单已准备好----");
	
	//获取所有权限url
	/*List<Privilege> privileges = privilegeService.findAllPrivileges();
	List<String> allPrivileges = new ArrayList<String>();
	for(Privilege privilege : privileges){
	    allPrivileges.add(privilege.getUrl());
	}
	application.setAttribute("allPrivilegeUrl", allPrivileges);*/
    }

    /**
     * @Description:销毁
     * @param sce
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
	// TODO 自动生成的方法存根

    }

}
