package com.kihd.cloudpicturebackend.controller;

import com.kihd.cloudpicturebackend.annotation.AuthCheck;
import com.kihd.cloudpicturebackend.common.BaseResponse;
import com.kihd.cloudpicturebackend.common.ResultUtils;
import com.kihd.cloudpicturebackend.constant.UserConstant;
import com.kihd.cloudpicturebackend.model.dto.picture.PictureUploadRequest;
import com.kihd.cloudpicturebackend.model.entity.User;
import com.kihd.cloudpicturebackend.model.vo.picture.PictureVO;
import com.kihd.cloudpicturebackend.service.PictureService;
import com.kihd.cloudpicturebackend.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/picture")
@Slf4j
@Api(tags = "图片模块")
public class PictureController {
    @Resource
    private PictureService pictureService;
    @Resource
    private UserService userService;
    /**
     * 上传图片（可重新上传）
     */
    @ApiOperation("上传图片（可重新上传）")
    @PostMapping("/upload")
    @AuthCheck(mustRole = UserConstant.ADMIN_ROLE)
    public BaseResponse<PictureVO> uploadPicture(
            @RequestPart("file") MultipartFile multipartFile,
            PictureUploadRequest pictureUploadRequest,
            HttpServletRequest request) {
        User loginUser = userService.getLoginUser(request);
        PictureVO pictureVO = pictureService.uploadPicture(multipartFile, pictureUploadRequest, loginUser);
        return ResultUtils.success(pictureVO);
    }

}
