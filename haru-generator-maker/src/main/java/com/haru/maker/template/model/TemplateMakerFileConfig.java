package com.haru.maker.template.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @program: haru-generator-maker
 * @ClassName TemplateMakerFileConfig
 * @description: TODO
 * @author: HaruLee
 * @createtime: 2024/12/24 13:21
 * @Version 1.0
 **/

@Data
public class TemplateMakerFileConfig {


    private List<FileInfoConfig> files;
    //文件的分组信息
    private FileGroupConfig fileGroupConfig;


    @Data
    @NoArgsConstructor
    public static class FileInfoConfig {
        //文件路径
        private String path;
        //过滤规则
        private List<FileFilterConfig> filterConfigList;
    }

    @Data
    public static class FileGroupConfig {
        private String groupKey;
        private String groupName;
        private String condition;
    }
}
