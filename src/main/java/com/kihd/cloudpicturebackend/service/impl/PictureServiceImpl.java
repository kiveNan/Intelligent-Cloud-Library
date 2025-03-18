package com.kihd.cloudpicturebackend.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.kihd.cloudpicturebackend.exception.ErrorCode;
import com.kihd.cloudpicturebackend.exception.ThrowUtils;
import com.kihd.cloudpicturebackend.manage.FileManager;
import com.kihd.cloudpicturebackend.mapper.PictureMapper;
import com.kihd.cloudpicturebackend.model.dto.file.UploadPictureResult;
import com.kihd.cloudpicturebackend.model.dto.picture.PictureUploadRequest;
import com.kihd.cloudpicturebackend.model.entity.Picture;
import com.kihd.cloudpicturebackend.model.entity.User;
import com.kihd.cloudpicturebackend.model.vo.picture.PictureVO;
import com.kihd.cloudpicturebackend.service.PictureService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.Date;

/**
* @author Nan Liu
* @description 针对表【picture(图片)】的数据库操作Service实现
* @createDate 2025-03-18 09:44:39
*/
@Service
public class PictureServiceImpl extends ServiceImpl<PictureMapper, Picture>
    implements PictureService{
    @Resource
    private FileManager fileManager;

    @Override
    public PictureVO uploadPicture(MultipartFile multipartFile, PictureUploadRequest pictureUploadRequest, User loginUser) {
        ThrowUtils.throwIf(loginUser == null, ErrorCode.NO_AUTH_ERROR);
        // 用于判断是新增还是更新图片
        Long pictureId = null;
        if (pictureUploadRequest != null) {
            pictureId = pictureUploadRequest.getId();
        }
        // 如果是更新图片，需要校验图片是否存在
        if (pictureId != null) {
            boolean exists = this.lambdaQuery()
                    .eq(Picture::getId, pictureId)
                    .exists();
            ThrowUtils.throwIf(!exists, ErrorCode.NOT_FOUND_ERROR, "图片不存在");
        }
        // 上传图片，得到信息
        // 按照用户 id 划分目录
        String uploadPathPrefix = String.format("public/%s", loginUser.getId());
        UploadPictureResult uploadPictureResult = fileManager.uploadPicture(multipartFile, uploadPathPrefix);
        // 构造要入库的图片信息
        Picture picture = new Picture();
        picture.setUrl(uploadPictureResult.getUrl());
        picture.setName(uploadPictureResult.getPicName());
        picture.setPicSize(uploadPictureResult.getPicSize());
        picture.setPicWidth(uploadPictureResult.getPicWidth());
        picture.setPicHeight(uploadPictureResult.getPicHeight());
        picture.setPicScale(uploadPictureResult.getPicScale());
        picture.setPicFormat(uploadPictureResult.getPicFormat());
        picture.setUserId(loginUser.getId());
        // 如果 pictureId 不为空，表示更新，否则是新增
        if (pictureId != null) {
            // 如果是更新，需要补充 id 和编辑时间
            picture.setId(pictureId);
            picture.setEditTime(new Date());
        }
        boolean result = this.saveOrUpdate(picture);
        ThrowUtils.throwIf(!result, ErrorCode.OPERATION_ERROR, "图片上传失败");
        return PictureVO.objToVo(picture);
    }

}




