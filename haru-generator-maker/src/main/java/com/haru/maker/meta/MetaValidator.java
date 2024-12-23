package com.haru.maker.meta;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.StrUtil;
import com.haru.maker.meta.enums.FileGenerateTypeEnum;
import com.haru.maker.meta.enums.FileTypeEnum;
import com.haru.maker.meta.enums.ModelTypeEnum;

import java.io.File;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

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

        validAndFillMetaRoot(meta);

        validAndFillFileConfig(meta);

        validAndFillModelConfig(meta);

    }

    /**
     * modelConfig 校验和默认值
     *
     * @param meta 要检验的元信息
     * @author Haru
     * @date 2024/12/18 20:32
     */
    private static void validAndFillModelConfig(Meta meta) {
        Meta.ModelConfig modelConfig = meta.getModelConfig();
        if (modelConfig == null) {
            return;
        }
        //modelConfig默认值
        List<Meta.ModelConfig.ModelInfo> models = modelConfig.getModels();
        if (!CollectionUtil.isNotEmpty(models)) {
            return;
        }
        for (Meta.ModelConfig.ModelInfo modelInfo : models) {
            //为group,不校验
            String groupKey = modelInfo.getGroupKey();
            if(!StrUtil.isEmpty(groupKey)){
                //生成中间参数
                List<Meta.ModelConfig.ModelInfo> subModelInfoList = modelInfo.getModels();
                String allArgsStr = modelInfo.getModels().stream().map(submodelInfo -> String.format("\"--%s\"", submodelInfo.getFieldName())).collect(Collectors.joining(", "));
                modelInfo.setAllArgsStr(allArgsStr);
                continue;
            }

            //模型字段名的默认值
            String fieldName = modelInfo.getFieldName();
            if (StrUtil.isBlank(fieldName)) {
                throw new MetaException("未填写 fieldName");
            }

            String type = modelInfo.getType();
            if (StrUtil.isEmpty(type)) {
                modelInfo.setType(ModelTypeEnum.STRING.getValue());
            }


        }

    }

    /**
     * fileConfig 校验和默认值
     *
     * @param meta 要检验的元信息
     * @author Haru
     * @date 2024/12/18 20:55
     */


    private static void validAndFillFileConfig(Meta meta) {

        Meta.FileConfig fileConfig = meta.getFileConfig();
        if (fileConfig == null) {
            return;
        }

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
        String defaultType = FileTypeEnum.DIR.getValue();

        if (StrUtil.isEmpty(fileConfigType)) {
            fileConfig.setType(defaultType);
        }

        //fileInfo默认值
        List<Meta.FileConfig.FileInfo> fileInfoList = fileConfig.getFiles();
        if (!CollectionUtil.isNotEmpty(fileInfoList)) {
            return;
        }


        for (Meta.FileConfig.FileInfo fileInfo : fileInfoList) {

            //类型为文件组group,就不做校验
            if (FileTypeEnum.GROUP.getValue().equals(fileInfo.getType())){
                continue;
            }
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
                    fileInfo.setType(FileTypeEnum.DIR.getValue());
                } else {
                    fileInfo.setType(FileTypeEnum.FILE.getValue());
                }
            }
            //如果文件结尾不为 ftl, generateType默认为static,否则为dynamic
            String generateType = fileInfo.getGenerateType();
            if (StrUtil.isBlank(generateType)) {
                if (inputPath.endsWith(".ftl")) {
                    fileInfo.setGenerateType(FileGenerateTypeEnum.DYNAMIC.getValue());
                } else {
                    fileInfo.setGenerateType(FileGenerateTypeEnum.STATIC.getValue());
                }
            }


        }
    }

    /**
     * 基础信息校验和默认值
     *
     * @param meta 要检验的元信息
     * @author Haru
     * @date 2024/12/18 20:55
     */


    private static void validAndFillMetaRoot(Meta meta) {

        String name = StrUtil.blankToDefault(meta.getName(), "my-generator");
        String description = StrUtil.emptyToDefault(meta.getDescription(), "我的模板代码生成器");
        String author = StrUtil.emptyToDefault(meta.getAuthor(), "haru");
        String basePackage = StrUtil.blankToDefault(meta.getBasePackage(), "com.haru");
        String version = StrUtil.emptyToDefault(meta.getVersion(), "1.0");
        String createTime = StrUtil.emptyToDefault(meta.getCreateTime(), DateUtil.now());


        meta.setName(name);


        meta.setDescription(description);


        meta.setBasePackage(basePackage);


        meta.setVersion(version);


        meta.setAuthor(author);


        meta.setCreateTime(createTime);

    }
}
