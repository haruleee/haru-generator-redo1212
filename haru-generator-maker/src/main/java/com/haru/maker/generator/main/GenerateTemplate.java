package com.haru.maker.generator.main;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import com.haru.maker.generator.DynamicGenerator;
import com.haru.maker.generator.JarGenerator;
import com.haru.maker.generator.ScriptGenerator;
import com.haru.maker.meta.Meta;
import com.haru.maker.meta.MetaManager;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.IOException;

/**
 * @program: haru-generator-maker
 * @ClassName GenerateTemplate
 * @description: TODO
 * @author: HaruLee
 * @createtime: 2024/12/18 21:24
 * @Version 1.0
 **/
public abstract class GenerateTemplate {


    public void doGenerate() throws TemplateException, IOException, InterruptedException {

        //获取Meta对象
        Meta meta = MetaManager.getMetaObject();

        //0、输出根路径
        String projectPath = System.getProperty("user.dir");
        String outputPath = projectPath + File.separator + "generated" + File.separator + meta.getName();
        if (!FileUtil.exist(outputPath)) {
            FileUtil.mkdir(outputPath);
        }

        //1、复制原始文件
        String sourceCopyDestPath = copySource(meta, projectPath);

        //2、代码生成
        generateCode(meta, outputPath);

        //3、构建Jar包
        String jarPath = buildJar(outputPath, meta);

        //4、封装脚本
        String shellOutputFilePath = buildScript(outputPath, jarPath);


        //5、生成精简版的程序（产物包）
        buildDist(outputPath, jarPath, shellOutputFilePath, sourceCopyDestPath);


    }

    /**
     * 生成精简版的程序
     *
     * @param outputPath
     * @param jarPath
     * @param shellOutputFilePath
     * @param sourceCopyDestPath
     * @author Haru
     * @date 2024/12/18 21:40
     */
    protected void buildDist(String outputPath, String jarPath, String shellOutputFilePath, String sourceCopyDestPath) {
        String disOutputPath = outputPath + "-dist";
        //-拷贝jar包
        String targetAbsolutePath = disOutputPath + File.separator + "target";
        FileUtil.mkdir(targetAbsolutePath);
        String jarAbsolutePath = outputPath + File.separator + jarPath;
        FileUtil.copy(jarAbsolutePath, targetAbsolutePath, true);
        //-拷贝脚本文件
        //FileUtil.copy(shellOutputFilePath,disOutputPath,true);
        FileUtil.copy(shellOutputFilePath + ".bat", disOutputPath, true);
        //拷贝源模板文件
        FileUtil.copy(sourceCopyDestPath, disOutputPath, true);
    }

    /**
     * 封装脚本
     *
     * @param outputPath
     * @param jarPath
     * @return String
     * @author Haru
     * @date 2024/12/18 21:41
     */
    protected String buildScript(String outputPath, String jarPath) {
        String shellOutputFilePath = outputPath + File.separator + "generator";
        ScriptGenerator.doGenerate(shellOutputFilePath, jarPath);
        return shellOutputFilePath;
    }

    /**
     * 构建jar
     *
     * @param outputPath
     * @param meta
     * @return String
     * @author Haru
     * @date 2024/12/18 21:41
     */
    protected String buildJar(String outputPath, Meta meta) throws IOException, InterruptedException {
        JarGenerator.doGenerate(outputPath);
        String jarName = String.format("%s-%s-jar-with-dependencies.jar", meta.getName(), meta.getVersion());
        String jarPath = "target/" + jarName;
        return jarPath;
    }

    /**
     * 代码生成
     *
     * @param meta
     * @param outputPath
     * @author Haru
     * @date 2024/12/18 21:36
     */
    protected void generateCode(Meta meta, String outputPath) throws IOException, TemplateException {

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

        //README.md
        inputFilePath = inputResourcePath + File.separator + "templates/README.md.ftl";
        outputFilePath = outputPath + File.separator + "README.md";
        DynamicGenerator.doGenerate(inputFilePath, outputFilePath, meta);
    }


    /**
     * 复制原始文件
     *
     * @param meta
     * @param projectPath
     * @return String
     * @author Haru
     * @date 2024/12/18 21:28
     */
    protected String copySource(Meta meta, String projectPath) {
        String sourceRootPath = meta.getFileConfig().getSourceRootPath();
        String sourceCopyDestPath = projectPath + File.separator + ".source";
        FileUtil.copy(sourceRootPath, sourceCopyDestPath, false);
        return sourceCopyDestPath;
    }
}
