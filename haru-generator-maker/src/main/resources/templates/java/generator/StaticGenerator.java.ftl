package ${basePackage}.generator;

import cn.hutool.core.io.FileUtil;

/**
 * @program: haru-generator-maker
 * @ClassName StaticGenerator
 * @description: 静态文件生成器
 * @author: ${author}
 * @createtime: ${createTime}
 * @Version ${version}
 **/
public class StaticGenerator {

    /**
     * 拷贝文件(Hutool 实现,会将输入目录完整拷贝到输出目录下)
     *
     * @param inputPath 文件输入路径
     * @param outputPath 文件输出路径
     * @author: ${author}
     * @createtime: ${createTime}
     */
    public static void doGenerate(String inputPath, String outputPath) {
        FileUtil.copy(inputPath, outputPath, false);
    }
}
