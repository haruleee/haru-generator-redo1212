package com.haru.maker.template;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.haru.maker.meta.Meta;
import com.haru.maker.meta.enums.FileGenerateTypeEnum;
import com.haru.maker.meta.enums.FileTypeEnum;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @program: haru-generator-maker
 * @ClassName TemplateMaker
 * @description: TODO
 * @author: HaruLee
 * @createtime: 2024/12/21 20:48
 * @Version 1.0
 **/
public class TemplateMaker {



    /**
     * 制作文件模板
     * @author Haru
     * @date 2024/12/23 18:32
     * @param modelInfo
     * @param searchStr
     * @param sourceRootPath
     * @param inputFile
     * @return FileInfo
     */
    private static Meta.FileConfig.FileInfo makeFileTemplate(Meta.ModelConfig.ModelInfo modelInfo,String searchStr,String sourceRootPath,File inputFile){
        //要挖坑的文件绝对路径（用于制作模板)
        //注意win系统需要对路径进行转义
        String fileInputAbsolutePath = inputFile.getAbsolutePath().replaceAll("\\\\", "/");
        String fileOutputAbsolutePath = fileInputAbsolutePath + ".ftl";
        //文件输入输出相对路径（用于生成配置）
        String fileInputPath = fileInputAbsolutePath.replace(sourceRootPath + "/", "");
        String fileOutputPath = fileInputPath + ".ftl";
        //使用字符串替换，生成模板文件
        String fileContent;
        if (FileUtil.exist(fileOutputAbsolutePath)){
            fileContent = FileUtil.readUtf8String(fileOutputAbsolutePath);
        }else {
            fileContent = FileUtil.readUtf8String(fileInputAbsolutePath);
        }
        String replacement = String.format("${%s}", modelInfo.getFieldName());


        String newFileContent = StrUtil.replace(fileContent, searchStr, replacement);



        //文件配置信息
        Meta.FileConfig.FileInfo fileInfo = new Meta.FileConfig.FileInfo();
        fileInfo.setInputPath(fileInputPath);
        fileInfo.setOutputPath(fileOutputPath);
        fileInfo.setType(FileTypeEnum.FILE.getValue());

        //和原文件一致，没有挖坑，则为静态生成
        if (newFileContent.equals(fileContent)){
            fileInfo.setOutputPath(fileInputPath);
            fileInfo.setGenerateType(FileGenerateTypeEnum.STATIC.getValue());
        }else {
            //生成模板文件
            FileUtil.writeUtf8String(newFileContent, fileOutputAbsolutePath);
            fileInfo.setGenerateType(FileGenerateTypeEnum.STATIC.getValue());
        }

        return fileInfo;
    }
    /**
     * 制作模板
     * @author Haru
     * @date 2024/12/23 18:31
     * @param newMeta
     * @param originProjectPath
     * @param inputFilePath
     * @param modelInfo
     * @param searchStr
     * @param id
     * @return long
     */


    private static long makeTemplate(Meta newMeta, String originProjectPath, String inputFilePath, Meta.ModelConfig.ModelInfo modelInfo, String searchStr, Long id) {

        //没有id则生成
        if (id == null) {
            id = IdUtil.getSnowflakeNextId();
        }


        //复制目录
        String projectPath = System.getProperty("user.dir");
        String tempDirPath = projectPath + File.separator + ".temp";
        String templatePath = tempDirPath + File.separator + id;

        //是否为首次制作模板
        //目录不存在，则是首次制作
        if (!FileUtil.exist(templatePath)) {
            FileUtil.mkdir(tempDirPath);
            FileUtil.copy(originProjectPath, templatePath, true);
        }


        //一、输入信息
        //输入文件信息
        String sourceRootPath = templatePath + File.separator + FileUtil.getLastPathEle(Paths.get(originProjectPath)).toString();

        //二、生成文件模板
        //输入文件为目录

        List<Meta.FileConfig.FileInfo> newFileInfoList = new ArrayList<>();
        String inputFileAbsolutePath = sourceRootPath + File.separator + inputFilePath;
        if(FileUtil.isDirectory(inputFileAbsolutePath)){
            List<File> fileList = FileUtil.loopFiles(inputFileAbsolutePath);
            for (File file : fileList) {
                Meta.FileConfig.FileInfo fileInfo = makeFileTemplate(modelInfo, searchStr, sourceRootPath, file);
                newFileInfoList.add(fileInfo);
            }
        }else {
            //输入的是文件
            Meta.FileConfig.FileInfo fileInfo = makeFileTemplate(modelInfo, searchStr, sourceRootPath, new File(inputFileAbsolutePath));
            newFileInfoList.add(fileInfo);
        }


        //三、生成配置文件
        String metaOutputPath = sourceRootPath + File.separator + "meta.json";

        // 如果已有meta文件，说明不是第一次制作，则在 meta 基础上进行更改
        if (FileUtil.exist(metaOutputPath)) {
            Meta oldMeta = JSONUtil.toBean(FileUtil.readUtf8String(metaOutputPath), Meta.class);
            BeanUtil.copyProperties(newMeta, oldMeta, CopyOptions.create().ignoreNullValue());
            newMeta = oldMeta;


            //1.追加配置参数
            List<Meta.FileConfig.FileInfo> fileInfoList = newMeta.getFileConfig().getFiles();
            fileInfoList.addAll(newFileInfoList);
            List<Meta.ModelConfig.ModelInfo> modelInfoList = newMeta.getModelConfig().getModels();
            modelInfoList.add(modelInfo);

            //配置去重
            newMeta.getFileConfig().setFiles(distinctFiles(fileInfoList));
            newMeta.getModelConfig().setModels(distinctModels(modelInfoList));


        } else {

            //1.构造配置参数

            Meta.FileConfig fileConfig = new Meta.FileConfig();
            newMeta.setFileConfig(fileConfig);
            fileConfig.setSourceRootPath(sourceRootPath);
            List<Meta.FileConfig.FileInfo> fileInfoList = new ArrayList<>();
            fileConfig.setFiles(fileInfoList);
            fileInfoList.addAll(newFileInfoList);

            Meta.ModelConfig modelConfig = new Meta.ModelConfig();
            newMeta.setModelConfig(modelConfig);
            List<Meta.ModelConfig.ModelInfo> modelInfoList = new ArrayList<>();
            modelConfig.setModels(modelInfoList);
            modelInfoList.add(modelInfo);

        }
        //2.输出元信息文件
        FileUtil.writeUtf8String(JSONUtil.toJsonPrettyStr(newMeta), metaOutputPath);
        return id;
    }

    public static void main(String[] args) {

        Meta meta = new Meta();
        meta.setName("acm-template-generator");
        meta.setDescription("ACM 示例模板生成器");

        String projectPath = System.getProperty("user.dir");
        String originProjectPath = new File(projectPath).getParent() + File.separator + "haru-generator-demo-projects/springboot-init";

        String inputFilePath = "src/main/java/com/haru/springbootinit";

        //模型参数信息（首次）
        Meta.ModelConfig.ModelInfo modelInfo = new Meta.ModelConfig.ModelInfo();
        modelInfo.setFieldName("outputText");
        modelInfo.setType("String");
        modelInfo.setDefaultValue("sum = ");
        //替换变量（首次）
        String searchStr = "BaseResponse";


//        //模型参数信息（第二次）
//        Meta.ModelConfig.ModelInfo modelInfo = new Meta.ModelConfig.ModelInfo();
//        modelInfo.setFieldName("className");
//        modelInfo.setType("String");
//
//        //替换变量（第二次）
//        String searchStr = "MainTemplate";

        long id = makeTemplate(meta, originProjectPath, inputFilePath, modelInfo, searchStr, null);
        System.out.println("id = " + id);


    }

    /**
     * 文件去重
     *
     * @param fileInfoList
     * @return List<FileInfo>
     * @author Haru
     * @date 2024/12/22 20:35
     */
    private static List<Meta.FileConfig.FileInfo> distinctFiles(List<Meta.FileConfig.FileInfo> fileInfoList) {
        return new ArrayList<>(
                fileInfoList.stream()
                        .collect(
                                Collectors.toMap(Meta.FileConfig.FileInfo::getInputPath, o -> o, (e, r) -> r)
                        ).values()
        );
    }


    /**
     * 模型去重
     *
     * @param modelInfoList
     * @return List<ModelInfo>
     * @author Haru
     * @date 2024/12/22 20:35
     */
    private static List<Meta.ModelConfig.ModelInfo> distinctModels(List<Meta.ModelConfig.ModelInfo> modelInfoList) {
        return new ArrayList<>(
                modelInfoList.stream()
                        .collect(
                                Collectors.toMap(Meta.ModelConfig.ModelInfo::getFieldName, o -> o, (e, r) -> r)
                        ).values()
        );
    }


}
