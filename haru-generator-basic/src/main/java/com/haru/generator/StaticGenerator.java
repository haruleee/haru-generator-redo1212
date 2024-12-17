package com.haru.generator;

import cn.hutool.core.io.FileUtil;
import cn.hutool.core.util.ArrayUtil;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

/**
 * @program: haru-generator-basic
 * @ClassName StaticGenerator
 * @description: TODO
 * @author: HaruLee
 * @createtime: 2024/12/12 21:46
 * @Version 1.0
 **/
public class StaticGenerator {


    public static void main(String[] args) {
        //获取整个项目的根路径
        String projectPath = System.getProperty("user.dir");
        System.out.println("projectPath = " + projectPath);
        File parentFile = new File(projectPath).getParentFile();
        System.out.println("parentFile = " + parentFile);
        //输入路径:ACM示例代码模板目录
        String inputPath = new File(parentFile, "haru-generator-demo-projects\\acm-template").getAbsolutePath();
        String outputPath = projectPath;
//        copyFilesByHutool(inputPath,outputPath);
        copyFilesByRecursive(inputPath,outputPath);

    }
    /**
     *  拷贝文件(Hutool 实现,会将输入目录完整拷贝到输出目录下)
     * @author Haru
     * @date 2024/12/12 21:49
     * @param inputPath
     * @param outputPath
     */
    public static void copyFilesByHutool(String inputPath, String outputPath) {
        FileUtil.copy(inputPath, outputPath, false);
    }


    public static void copyFilesByRecursive(String inputPath, String outputPath) {
        File inputFile = new File(inputPath);
        File outputFile = new File(outputPath);

        try {
            copyFileByRecursive(inputFile,outputFile);
        } catch (Exception e) {
            System.out.println("文件复制失败");
            e.printStackTrace();
        }

    }

    private static void copyFileByRecursive(File inputFile, File outputFile) throws IOException {
        if (inputFile.isDirectory()) {
            File destOutputFile = new File(outputFile, inputFile.getName());
            if (!destOutputFile.exists()) {
                destOutputFile.mkdirs();
            }
            File[] files = inputFile.listFiles();
            if (ArrayUtil.isEmpty(files)){
                return;
            }
            for (File file : files) {
                copyFileByRecursive(file,destOutputFile);
            }

        }else {
            Path destPath = outputFile.toPath().resolve(inputFile.getName());
            Files.copy(inputFile.toPath(),destPath, StandardCopyOption.REPLACE_EXISTING);
        }
    }




}
