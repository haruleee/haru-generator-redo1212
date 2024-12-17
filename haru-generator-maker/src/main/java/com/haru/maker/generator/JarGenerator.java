package com.haru.maker.generator;

import java.io.*;

/**
 * @program: haru-generator-maker
 * @ClassName JarGenerator
 * @description: Jar包生成器
 * @author: HaruLee
 * @createtime: 2024/12/16 18:44
 * @Version 1.0
 **/
public class JarGenerator {


    public static void doGenerate(String projectDir) throws IOException, InterruptedException {
        //清理之前的构建并打包
        //注意不同操作系统,执行的命令不同
        String winMavenCommand = "mvn.cmd clean package -DskipTests=true";
        String otherMavenCommand = "mvn clean package -DskipTests=true";
        String mavenCommand = winMavenCommand;

        //启动进程
        ProcessBuilder processBuilder = new ProcessBuilder(mavenCommand.split(" "));
        processBuilder.directory(new File(projectDir));

        Process process = processBuilder.start();

        //读取命令输出
        InputStream inputStream = process.getInputStream();
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        String line = null;
        while ((line = bufferedReader.readLine())!=null){
            System.out.println(line);
        }

        //等待命令执行完成
        int exitCode = process.waitFor();
        System.out.println("命令执行结束,退出码" + exitCode);
    }

    public static void main(String[] args) throws IOException, InterruptedException {
        doGenerate("D:\\file\\studyfile\\JavaStudyFile\\haru-generator-redo1212\\haru-generator-maker\\generated\\acm-template-pro-generator");
    }

}
