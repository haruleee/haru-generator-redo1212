package com.haru.maker.generator;

import cn.hutool.captcha.generator.AbstractGenerator;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import com.haru.maker.generator.main.GenerateTemplate;
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
public class MainGenerator extends GenerateTemplate {
    @Override
    protected void buildDist(String outputPath, String jarPath, String shellOutputFilePath, String sourceCopyDestPath) {
        System.out.println("不生成精简程序了！！");
    }
}
