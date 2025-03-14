package com.kihd.cloudpicturebackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kihd.cloudpicturebackend.model.entity.User;
import com.kihd.cloudpicturebackend.service.UserService;
import com.kihd.cloudpicturebackend.mapper.UserMapper;
import org.springframework.stereotype.Service;

/**
* @author Nan Liu
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2025-03-14 12:35:31
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

}




