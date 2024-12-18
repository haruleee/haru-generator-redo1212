package com.haru.maker.meta;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.file.PathUtil;
import cn.hutool.core.util.StrUtil;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

/**
 * @program: haru-generator-maker
 * @ClassName MetaValidator
 * @description: 元信息校验
 * @author: HaruLee
 * @createtime: 2024/12/18 18:55
 * @Version 1.0
 **/
public class MetaValidator {
    public static void doValidAndFill(Meta meta) {

        //基础信息校验和默认值
        String name = meta.getName();
        if (StrUtil.isBlank(name)) {
            name = "my-generator";
            meta.setName(name);
        }

        String description = meta.getDescription();
        if (StrUtil.isEmpty(description)) {
            description = "我的模板代码生成器";
            meta.setDescription(description);
        }

        String basePackage = meta.getBasePackage();
        if (StrUtil.isBlank(basePackage)) {
            basePackage = "com.haru";
            meta.setBasePackage(basePackage);
        }

        String version = meta.getVersion();
        if (StrUtil.isEmpty(version)) {
            version = "1.0";
            meta.setVersion(version);
        }

        String author = meta.getAuthor();
        if (StrUtil.isEmpty(author)) {
            meta.setAuthor("Haru");
        }

        String createTime = meta.getCreateTime();
        if (StrUtil.isEmpty(createTime)) {
            createTime = DateUtil.now();
            meta.setCreateTime(createTime);
        }

        //fileConfig 校验和默认值
        Meta.FileConfig fileConfig = meta.getFileConfig();
        if (fileConfig != null) {

            //sourceRootPath必填
            String sourceRootPath = fileConfig.getSourceRootPath();
            if (StrUtil.isBlank(sourceRootPath)) {
                throw new MetaException("未填写 sourceRootPath");
            }

            //inputRootPath: .source + sourceRootPath 的最后一个层级路径
            String inputRootPath = fileConfig.getInputRootPath();
            String defaultInputRootPath = ".source" + File.separator + FileUtil.getLastPathEle(Paths.get(sourceRootPath)).getFileName().toString();
            if (StrUtil.isEmpty(inputRootPath)) {
                fileConfig.setInputRootPath(defaultInputRootPath);
            }

            //outputRootPath:默认为当前路径下的 generated
            String outputRootPath = fileConfig.getOutputRootPath();
            String defaultOutputRootPath = "generated";
            if (StrUtil.isEmpty(outputRootPath)) {
                fileConfig.setOutputRootPath(defaultOutputRootPath);
            }

            String fileConfigType = fileConfig.getType();
            String defaultType = "dir";
            if (StrUtil.isEmpty(fileConfigType)) {
                fileConfig.setType(defaultType);
            }

            //fileInfo默认值
            List<Meta.FileConfig.FileInfo> fileInfoList = fileConfig.getFiles();
            if (CollectionUtil.isNotEmpty(fileInfoList)) {
                for (Meta.FileConfig.FileInfo fileInfo : fileInfoList) {

                    //inputPath: 必填
                    String inputPath = fileInfo.getInputPath();
                    if (StrUtil.isBlank(inputPath)) {
                        throw new MetaException("未填写 inputPath");
                    }
                    //outputPath: 默认等于inputPath
                    String outputPath = fileInfo.getOutputPath();
                    if (StrUtil.isEmpty(outputPath)) {
                        fileInfo.setOutputPath(inputPath);
                    }
                    //type: 默认inputPath 有文件后缀（如 .java）为file,否则为dir
                    String type = fileInfo.getType();
                    if (StrUtil.isBlank(type)) {
                        //无文件后缀
                        String suffix = FileUtil.getSuffix(inputPath);
                        if (StrUtil.isBlank(suffix)) {
                            fileInfo.setType("dir");
                        } else {
                            fileInfo.setType("file");
                        }
                    }
                    //如果文件结尾不为 ftl, generateType默认为static,否则为dynamic
                    String generateType = fileInfo.getGenerateType();
                    if (StrUtil.isBlank(generateType)) {
                        if (inputPath.endsWith("ftl")) {
                            fileInfo.setGenerateType("dynamic");
                        } else {
                            fileInfo.setGenerateType("static");
                        }
                    }


                }
            }


        }

        //modelConfig 校验和默认值
        Meta.ModelConfig modelConfig = meta.getModelConfig();
        if (modelConfig != null) {
            List<Meta.ModelConfig.ModelInfo> models = modelConfig.getModels();
            if (CollectionUtil.isNotEmpty(models)) {
                for (Meta.ModelConfig.ModelInfo modelInfo : models) {

                    //模型字段名的默认值
                    String fieldName = modelInfo.getFieldName();
                    if (StrUtil.isBlank(fieldName)) {
                        throw new MetaException("未填写 fieldName");
                    }

                    String type = modelInfo.getType();
                    if (StrUtil.isEmpty(type)) {
                        modelInfo.setType("String");
                    }


                }
            }

        }

    }
}
