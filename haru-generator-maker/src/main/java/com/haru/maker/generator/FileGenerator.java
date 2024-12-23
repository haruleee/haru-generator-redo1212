package com.haru.maker.generator;

import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

/**
 * @program: haru-generator-maker
 * @ClassName FileGenerator
 * @description: 核心生成器
 * @author: HaruLee
 * @createtime: 2024/12/14 16:53
 * @Version 1.0
 **/
public class FileGenerator {

    /**
     * 生成
     * @author Haru
     * @date 2024/12/14 20:10
     * @param dataModel 数据模型
     */
    public static void doGenerate(Object dataModel) throws TemplateException, IOException {
        //获取haru-generator-maker的目录
        String projectPath = System.getProperty("user.dir");
        //获取整个项目的根目录
        File parentFile = new File(projectPath).getParentFile();


        //获取源文件路径
        String inputPath = new File(parentFile, "/haru-generator-demo-projects/acm-template").getAbsolutePath();

        //将haru-generator-maker项目的根路径作为输出路径
        String outputPath = projectPath;

        //生成静态文件
        StaticGenerator.copyFilesByHutool(inputPath, outputPath);
        //生成动态文件
        //获取模板文件路径
        String inputDynamicFilePath = projectPath + File.separator + "src/main/resources/templates/MainTemplate.java.ftl";
        String outputDynamicFilePath = outputPath + File.separator + "acm-template/src/com/haru/acm/MainTemplate.java";
        DynamicGenerator.doGenerate(inputDynamicFilePath,outputDynamicFilePath,dataModel);

    }
}
