package cn.QEcode.realm;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import com.opensymphony.xwork2.ActionContext;

import cn.QEcode.cache.MySimpleByteSource;
import cn.QEcode.domain.Privilege;
import cn.QEcode.domain.Role;
import cn.QEcode.domain.User;
import cn.QEcode.service.PrivilegeService;
import cn.QEcode.service.RoleService;
import cn.QEcode.service.UserService;

/**  
* <p>Title: UserRealm.java</p>  
* <p>Description: </p>   
* @author QEcode  
* @date 2018年10月28日  
* @version 1.0  
*/ 
public class UserRealm  extends AuthorizingRealm{

    @Resource(name="UserService")
    private UserService userService ;
    @Resource(name="RoleService")
    private RoleService roleService;
    @Resource(name="PrivilegeService")
    private PrivilegeService privilegeService;
    
    public String getName(){
	return "userRealm";
    }
    
    /**
     * @Description:授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(
	    PrincipalCollection principals) {
	
	User user = (User) principals.getPrimaryPrincipal();
	List<Privilege> privileges = new ArrayList<Privilege>();
	
	//如果是超级用户,获得所有权限
	if("admin".equals(user.getLoginName())){
	    privileges = privilegeService.findAllPrivileges();
	}else{
	    //根据user获取role
	    Set<Role> roles = user.getRoles();
	    // 获取权限
	    for (Role role : roles) {
		privileges.addAll(role.getPrivileges());
	    }
	}
	//获取权限url
	Set<String> primessions  = new HashSet<String>();
	for(Privilege privilege:privileges){
	    primessions.add(privilege.getUrl());
	}
	//把权限封装
	SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
	info.addStringPermissions(primessions);
	return info;
    }

    /**
     * @Description:身份验证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(
	    AuthenticationToken token) throws AuthenticationException {
	//获取用户名
	String name = (String) token.getPrincipal();
	User user = userService.findPasswordByName(name);
	//盐值
	//ByteSource credentialsSalt = ByteSource.Util.bytes("123");
	//改用redis缓存
	SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user,user.getPassword(),getName());
	info.setCredentialsSalt(new MySimpleByteSource("123"));
	return info;
    }
    
}
