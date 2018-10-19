package cn.QEcode.action;

import org.springframework.stereotype.Controller;

@Controller("homeAction")
public class HomeAction {
    public String index(){
	return "index";
    }
    
    public String top(){
	return "top";
    }
    public String left(){
	return "left";
    }
    public String right(){
	return "right";
    }
    
    public String bottom(){
	return "bottom";
    }
}
