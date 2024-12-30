package com.haru.maker.template.model;

import lombok.Builder;
import lombok.Data;

/**
 * @program: haru-generator-maker
 * @ClassName FileFilterConfig
 * @description: 文件过滤配置
 * @author: HaruLee
 * @createtime: 2024/12/24 11:05
 * @Version 1.0
 **/
@Data
@Builder
public class FileFilterConfig {

    /**
     * 过滤范围
     */
    private String range;

    /**
     * 过滤规则
     */
    private String rule;

    /**
     * 过滤值
     */
    private String value;


}
