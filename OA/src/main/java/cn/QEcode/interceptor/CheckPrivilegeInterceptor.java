package cn.QEcode.interceptor;

import java.util.List;

import cn.QEcode.domain.Privilege;
import cn.QEcode.domain.User;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;
import com.opensymphony.xwork2.interceptor.Interceptor;

public class CheckPrivilegeInterceptor extends AbstractInterceptor{

    @Override
    public String intercept(ActionInvocation invocation) throws Exception {
	
	//获取用户,判断用户是否登陆
	User user  = (User) ActionContext.getContext().getSession().get("user");
	
	//获取当前访问url
	String url = invocation.getProxy().getActionName();
	String namespace=  invocation.getProxy().getNamespace();
	if(namespace.startsWith("/")){
	    namespace = namespace.substring(1);
	}
	
	//如果用户没有登陆,
	if(user==null){
	    //如果是进行登陆,则放行
	    if(url.startsWith("userAction_login")){
		   return invocation.invoke();
	    }else {
		//如果不是,则提示登陆
		return "loginUI";
	    }
	}else{
	  //如果用户已经登陆,访问不需要权限的页面,放行
	    List<String> allPrivileges = (List<String>) ActionContext.getContext().getApplication().get("allPrivilegeUrl");
	    if(!allPrivileges.contains(namespace+"/"+url)){
		return invocation.invoke();
	    }
	    //拥有权限
	    if(user.hasPrivilegeByUrl(url)){
		 return invocation.invoke();
	    }else {
		return "noPrivilegeError";
	    }
	}
    }

    
}
