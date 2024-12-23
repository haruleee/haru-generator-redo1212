package ${basePackage}.generator;
import ${basePackage}.generator.DynamicGenerator;
import ${basePackage}.generator.StaticGenerator;
import ${basePackage}.model.DataModel;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.IOException;

<#macro generateFile indent fileInfo>
${indent}inputFilePath = new File(inputRootPath, "${fileInfo.inputPath}").getAbsolutePath();
${indent}outputFilePath = new File(outputRootPath, "${fileInfo.outputPath}").getAbsolutePath();
<#if fileInfo.generateType == "static">
${indent}StaticGenerator.doGenerate(inputFilePath,outputFilePath);
<#else>
${indent}DynamicGenerator.doGenerate(inputFilePath,outputFilePath,model);
</#if>
</#macro>

/**
* @program: haru-generator-maker
* @ClassName MainGenerator
* @description: 核心生成器
* @author: ${author}
* @createtime: ${createTime}
* @Version ${version}
**/

public class MainGenerator {

    /**
     * 生成
     *
     * @param model 数据模型
     * @throws TemplateException
     * @throws IOException
     */
    public static void doGenerate(DataModel model) throws TemplateException, IOException {

        String inputRootPath = "${fileConfig.inputRootPath}";
        String outputRootPath = "${fileConfig.outputRootPath}";

        String inputFilePath;
        String outputFilePath;

<#list modelConfig.models as modelInfo>
    <#if modelInfo.groupKey??>
        <#list modelInfo.models as subModelInfo>
            ${subModelInfo.type} ${subModelInfo.fieldName} = model.${subModelInfo}.${subModelInfo.fieldName};
        </#list>
    <#else>
        ${modelInfo.type} ${modelInfo.fieldName} = model.${modelInfo.fieldName};
    </#if>
</#list>

<#list fileConfig.files as fileInfo>
    <#if fileInfo.groupKey??>
        <#if fileInfo.condition??>
        if(${fileInfo.condition}){
    <#list fileInfo.files as fileInfo>
            <@generateFile fileInfo=fileInfo indent="            "/>
    </#list>
        }
        <#else>
            <#list fileInfo.files as fileInfo>
                <@generateFile fileInfo=fileInfo indent="        "/>

            </#list>
        </#if>
    <#else>
        <#if fileInfo.condition??>
        if(${fileInfo.condition}){
            <#list fileInfo.files as fileInfo>
                <@generateFile fileInfo=fileInfo indent="            "/>
            </#list>
        }
        <#else>

        <@generateFile fileInfo=fileInfo indent="        "/>

        </#if>
    </#if>
</#list>
    }
}
