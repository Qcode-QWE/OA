package cn.QEcode.service.impl;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import cn.QEcode.service.UserService;

@Service(" UserService")
@Transactional(readOnly=true,propagation = Propagation.REQUIRED)
public class UserServiceImpl implements UserService {

}
