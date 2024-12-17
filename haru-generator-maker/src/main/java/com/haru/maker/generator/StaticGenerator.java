package com.haru.maker.generator;

import cn.hutool.core.io.FileUtil;

/**
 * @program: haru-generator-maker
 * @ClassName StaticGenerator
 * @description: TODO
 * @author: HaruLee
 * @createtime: 2024/12/12 21:46
 * @Version 1.0
 **/
public class StaticGenerator {
    /**
     * 拷贝文件(Hutool 实现,会将输入目录完整拷贝到输出目录下)
     *
     * @param inputPath 文件输入路径
     * @param outputPath 文件输出路径
     * @author Haru
     * @date 2024/12/12 21:49
     */
    public static void copyFilesByHutool(String inputPath, String outputPath) {
        FileUtil.copy(inputPath, outputPath, false);

    }
}
