package com.sky.controller.admin;


import com.sky.constant.MessageConstant;
import com.sky.result.Result;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/admin/upload")
@Slf4j
public class FileUploaderController {

    @Autowired
    private AliOssUtil aliOssUtil;


    @PostMapping("/file")
    @ConditionalOnMissingBean
    public Result<String> upload(MultipartFile file) {
        log.info("文件上传开始：{}", file.getOriginalFilename());
        try {
            //1.将文件上传到OSS服务器上
            //1.1获取oss文件上传客户端
            //1.2获取上传文件的原始文件名
            //1.3使用UUID重新生成一个文件名，防止文件名重复导致上传文件失败
            //1.4设置上传文件的访问权限为公共读
            //1.5创建上传文件的目录
            //1.6上传文件
            //1.7获取上传后的文件地址
            //1.8将文件地址返回
            String originalFilename = file.getOriginalFilename();
            String suffix = originalFilename.substring(originalFilename.lastIndexOf("."));
            String objectname = UUID.randomUUID().toString() + suffix;
            String fileurl = aliOssUtil.upload(file.getBytes(), objectname);
            return Result.success(fileurl);
        } catch (Exception e) {
            log.error("文件上传失败：{}", e.getMessage());
            e.printStackTrace();
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }

}
