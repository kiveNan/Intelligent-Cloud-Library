package com.kihd.cloudpicturebackend.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kihd.cloudpicturebackend.model.dto.picture.PictureUploadRequest;
import com.kihd.cloudpicturebackend.model.entity.Picture;
import com.kihd.cloudpicturebackend.model.entity.User;
import com.kihd.cloudpicturebackend.model.vo.picture.PictureVO;
import org.springframework.web.multipart.MultipartFile;

/**
* @author Nan Liu
* @description 针对表【picture(图片)】的数据库操作Service
* @createDate 2025-03-18 09:44:39
*/
public interface PictureService extends IService<Picture> {

    /**
     * 上传图片
     *
     * @param multipartFile
     * @param pictureUploadRequest
     * @param loginUser
     * @return
     */
    PictureVO uploadPicture(MultipartFile multipartFile,
                            PictureUploadRequest pictureUploadRequest,
                            User loginUser);

}
