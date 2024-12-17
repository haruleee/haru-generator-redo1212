package com.haru.cli.command;

import cn.hutool.core.bean.BeanUtil;
import com.haru.generator.MainGenerator;
import com.haru.model.MainTemplateConfig;
import lombok.Data;
import picocli.CommandLine;

import java.util.concurrent.Callable;

/**
 * @program: haru-generator-basic
 * @ClassName GenerateCommand
 * @description: TODO
 * @author: HaruLee
 * @createtime: 2024/12/15 11:41
 * @Version 1.0
 **/
@CommandLine.Command(name = "generate", description = "生成代码", mixinStandardHelpOptions = true)
@Data
public class GenerateCommand implements Callable<Integer> {




    //是否生成循环
    @CommandLine.Option(names = {"-l", "--loop"}, arity = "0..1", description = "是否循环", interactive = true, echo = true)
    private boolean loop;
    //作者注释
    @CommandLine.Option(names = {"-a","--author"},arity = "0..1", description = "作者",interactive = true,echo = true)
    private String author = "haru";
    //输出信息
    @CommandLine.Option(names = {"-o","--outputText"},arity = "0..1",description = "输出文本",interactive = true,echo = true)
    private String outputText = "求和输出=";





    @Override
    public Integer call() throws Exception {
        MainTemplateConfig mainTemplateConfig = new MainTemplateConfig();
        BeanUtil.copyProperties(this, mainTemplateConfig);
        System.out.println("配置信息: " + mainTemplateConfig);
        MainGenerator.doGenerate(mainTemplateConfig);
        return 0;
    }


}
