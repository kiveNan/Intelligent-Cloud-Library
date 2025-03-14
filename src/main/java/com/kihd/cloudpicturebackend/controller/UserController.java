package com.kihd.cloudpicturebackend.controller;

import com.kihd.cloudpicturebackend.common.BaseResponse;
import com.kihd.cloudpicturebackend.common.ResultUtils;
import com.kihd.cloudpicturebackend.exception.ErrorCode;
import com.kihd.cloudpicturebackend.exception.ThrowUtils;
import com.kihd.cloudpicturebackend.model.dto.user.UserRegisterRequest;
import com.kihd.cloudpicturebackend.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserService userService;

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public BaseResponse<Long> userRegister(@RequestBody UserRegisterRequest userRegisterRequest) {
        ThrowUtils.throwIf(userRegisterRequest == null, ErrorCode.PARAMS_ERROR);
        String userAccount = userRegisterRequest.getUserAccount();
        String userPassword = userRegisterRequest.getUserPassword();
        String checkPassword = userRegisterRequest.getCheckPassword();
        long result = userService.userRegister(userAccount, userPassword, checkPassword);
        return ResultUtils.success(result);
    }
}
