package com.haru.maker.generator;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import com.haru.maker.meta.Meta;
import com.haru.maker.meta.MetaManager;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

/**
 * @program: haru-generator-maker
 * @ClassName MainGenerator
 * @description: TODO
 * @author: HaruLee
 * @createtime: 2024/12/15 18:35
 * @Version 1.0
 **/
public class MainGenerator {
    public static void main(String[] args) throws TemplateException, IOException, InterruptedException {

        //获取Meta对象
        Meta meta = MetaManager.getMetaObject();

        //输出根路径
        String projectPath = System.getProperty("user.dir");
        String outputPath = projectPath + File.separator + "generated" + File.separator + meta.getName();
        if (!FileUtil.exist(outputPath)) {
            FileUtil.mkdir(outputPath);
        }


        //读取resources目录
        ClassPathResource classPathResource = new ClassPathResource("");
        String inputResourcePath = classPathResource.getAbsolutePath();


        //Java包基础路径
        String outputBasePackage = meta.getBasePackage();
        String outputBasePackagePath = StrUtil.join("/", StrUtil.split(outputBasePackage, "."));
        String outputBaseJavaPackagePath = outputPath + File.separator + "src/main/java/" + outputBasePackagePath;

        String inputFilePath;
        String outputFilePath;


        //DynamicGenerator
        inputFilePath = inputResourcePath + File.separator + "templates/java/generator/DynamicGenerator.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/generator/DynamicGenerator.java";
        DynamicGenerator.doGenerate(inputFilePath, outputFilePath, meta);

        //StaticGenerator
        inputFilePath = inputResourcePath + File.separator + "templates/java/generator/StaticGenerator.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/generator/StaticGenerator.java";
        DynamicGenerator.doGenerate(inputFilePath, outputFilePath, meta);

        //MainGenerator
        inputFilePath = inputResourcePath + File.separator + "templates/java/generator/MainGenerator.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/generator/MainGenerator.java";
        DynamicGenerator.doGenerate(inputFilePath, outputFilePath, meta);



        //model.DataModel
        inputFilePath = inputResourcePath + File.separator + "templates/java/model/DataModel.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/model/DataModel.java";
        DynamicGenerator.doGenerate(inputFilePath, outputFilePath, meta);

        //ConfigCommand
        inputFilePath = inputResourcePath + File.separator + "templates/java/cli/command/ConfigCommand.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/cli/command/ConfigCommand.java";
        DynamicGenerator.doGenerate(inputFilePath, outputFilePath, meta);

        //GenerateCommand
        inputFilePath = inputResourcePath + File.separator + "templates/java/cli/command/GenerateCommand.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/cli/command/GenerateCommand.java";
        DynamicGenerator.doGenerate(inputFilePath, outputFilePath, meta);

        //ListCommand
        inputFilePath = inputResourcePath + File.separator + "templates/java/cli/command/ListCommand.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/cli/command/ListCommand.java";
        DynamicGenerator.doGenerate(inputFilePath, outputFilePath, meta);

        //CommandExecutor
        inputFilePath = inputResourcePath + File.separator + "templates/java/cli/CommandExecutor.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/cli/CommandExecutor.java";
        DynamicGenerator.doGenerate(inputFilePath, outputFilePath, meta);

        //Main
        inputFilePath = inputResourcePath + File.separator + "templates/java/Main.java.ftl";
        outputFilePath = outputBaseJavaPackagePath + "/Main.java";
        DynamicGenerator.doGenerate(inputFilePath, outputFilePath, meta);


        //pom.xml
        inputFilePath = inputResourcePath + File.separator + "templates/pom.xml.ftl";
        outputFilePath = outputPath + File.separator + "pom.xml";
        DynamicGenerator.doGenerate(inputFilePath, outputFilePath, meta);

        //打Jar包
        JarGenerator.doGenerate(outputPath);

        //封装脚本
        String shellOutputFilePath = outputPath + File.separator + "generator";
        String jarName = String.format("%s-%s-jar-with-dependencies.jar", meta.getName(), meta.getVersion());
        String jarPath = "target/" + jarName;
        ScriptGenerator.doGenerate(shellOutputFilePath,jarPath);

    }
}
