package com.haru.generator;

import com.haru.model.MainTemplateConfig;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

/**
 * @program: haru-generator-basic
 * @ClassName DynamicGenerator
 * @description: 动态文件生成器
 * @author: HaruLee
 * @createtime: 2024/12/13 21:41
 * @Version 1.0
 **/
public class DynamicGenerator {

    /**
     * 生成动态文件方法
     * @author Haru
     * @date 2024/12/14 16:51
     * @param inputPath 模板文件输入路径
     * @param outputPath  输出路径
     * @param model 数据模型
     */
    public static void doGenerate(String inputPath, String outputPath, Object model) throws IOException, TemplateException {

        //new 出Configuration对象,参数为FreeMarker版本号
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_32);

        //指定模板文件所在的路径
        File templateDir = new File(inputPath).getParentFile();
        configuration.setDirectoryForTemplateLoading(templateDir);

        //设置模板文件使用的字符集
        configuration.setDefaultEncoding("UTF-8");

        //创建模板对象,加载指定模板
        String templateName = new File(inputPath).getName();
        Template template = configuration.getTemplate(templateName);

        //生成
        Writer out = new FileWriter(outputPath);
        template.process(model, out);

        //关闭数据流
        out.close();

    }
}