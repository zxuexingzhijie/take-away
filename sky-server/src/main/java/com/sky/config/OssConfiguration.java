package com.sky.config;


import com.sky.properties.AliOssProperties;
import com.sky.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class OssConfiguration {
    @Bean
    public AliOssUtil OssConfiguration(AliOssProperties aliasesOssProperties) {
        log.info("开始创建阿里云OSS对象,{}", aliasesOssProperties);
        return new AliOssUtil(aliasesOssProperties.getEndpoint(),
                aliasesOssProperties.getAccessKeyId(),
                aliasesOssProperties.getAccessKeySecret(),
                aliasesOssProperties.getBucketName());
    }

}
