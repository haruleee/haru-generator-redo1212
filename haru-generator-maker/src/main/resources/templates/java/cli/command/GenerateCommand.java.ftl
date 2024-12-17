package ${basePackage}.cli.command;

import cn.hutool.core.bean.BeanUtil;
import ${basePackage}.generator.MainGenerator;
import ${basePackage}.model.DataModel;
import lombok.Data;
import picocli.CommandLine;

import java.util.concurrent.Callable;

/**
 * @program: haru-generator-maker
 * @ClassName GenerateCommand
 * @description: TODO
 * @author: ${author}
 * @createtime: ${createTime}
 * @Version ${version}
 **/
@CommandLine.Command(name = "generate", description = "生成代码", mixinStandardHelpOptions = true)
@Data
public class GenerateCommand implements Callable<Integer> {

<#list modelConfig.models as modelInfo>

    //${modelInfo.description}
    @CommandLine.Option(names = {<#if modelInfo.abbr??>"-${modelInfo.abbr}", </#if>"--${modelInfo.fieldName}"}, arity = "0..1", <#if modelInfo.description??>description = "${modelInfo.description}", </#if> interactive = true, echo = true)
    private ${modelInfo.type} ${modelInfo.fieldName} <#if modelInfo.dedefaultValue??> = ${modelInfo.dedefaultValue?c} </#if>;

</#list>






    @Override
    public Integer call() throws Exception {
        DataModel dataModel = new DataModel();
        BeanUtil.copyProperties(this, dataModel);
        System.out.println("配置信息: " + dataModel);
        MainGenerator.doGenerate(dataModel);
        return 0;
    }


}
