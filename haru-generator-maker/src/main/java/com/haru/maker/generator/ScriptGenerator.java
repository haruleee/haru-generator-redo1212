package com.haru.maker.generator;

import cn.hutool.core.io.FileUtil;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.PosixFilePermission;
import java.nio.file.attribute.PosixFilePermissions;
import java.util.Set;

/**
 * @program: haru-generator-maker
 * @ClassName ScriptGenerator
 * @description: 生成用于执行代码生成器jar包的脚本文件
 * @author: HaruLee
 * @createtime: 2024/12/17 13:55
 * @Version 1.0
 **/
public class ScriptGenerator {
    public static void doGenerate(String outputPath, String jarPath) {
        //直接写入脚本文件
        //linux
        StringBuilder sb = new StringBuilder();
//        sb.append("#!/bin/bash").append("\n");
//        sb.append(String.format("java -jar %s \"$@\"", jarPath)).append("\n");
//        FileUtil.writeBytes(sb.toString().getBytes(StandardCharsets.UTF_8), outputPath);
//        //添加可执行权限
//        try {
//            Set<PosixFilePermission> permissions = PosixFilePermissions.fromString("rwxrwxrwx");
//            Files.setPosixFilePermissions(Paths.get(outputPath), permissions);
//        } catch (IOException e) {
//
//        }

        //windows
        sb = new StringBuilder();
        sb.append("@echo off").append("\n");
        sb.append(String.format("java -jar %s %%*", jarPath)).append("\n");
        FileUtil.writeBytes(sb.toString().getBytes(StandardCharsets.UTF_8), outputPath + ".bat");
    }

//    public static void main(String[] args) {
//        String outputPath = System.getProperty("user.dir") + File.separator + "generator";
//        doGenerate(outputPath,"");
//    }
}