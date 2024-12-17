package com.haru.maker.model;

import lombok.Data;

/**
 * @program: haru-generator-maker
 * @ClassName DataModel
 * @description: 动态模板配置
 * @author: HaruLee
 * @createtime: 2024/12/13 21:34
 * @Version 1.0
 **/

@Data
public class DataModel {
    /**
     * 是否生成循环
     */
    private boolean loop;
    /**
     * 作者注释
     */
    private String author = "haru";
    /**
     * 输出信息
     */
    private String outputText = "求和输出=";


}
