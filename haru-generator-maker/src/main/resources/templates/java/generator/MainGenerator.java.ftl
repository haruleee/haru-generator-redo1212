package ${basePackage}.generator;
import ${basePackage}.generator.DynamicGenerator;
import ${basePackage}.generator.StaticGenerator;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.resource.ClassPathResource;
import cn.hutool.core.util.StrUtil;
import freemarker.template.TemplateException;
import java.io.File;
import java.io.IOException;

/**
 * @program: haru-generator-maker
 * @ClassName MainGenerator
 * @description: TODO
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
    public static void doGenerate(Object model) throws TemplateException, IOException {


        String inputRootPath = "${fileConfig.inputRootPath}";
        String outputRootPath = "${fileConfig.outputRootPath}";


        String inputFilePath;
        String outputFilePath;

        <#list fileConfig.files as fileInfo>

            inputFilePath = new File(inputRootPath, "${fileInfo.inputPath}").getAbsolutePath();
            outputFilePath = new File(outputRootPath, "${fileInfo.outputPath}").getAbsolutePath();
            <#if fileInfo.generateType == "static">
                StaticGenerator.doGenerate(inputFilePath,outputFilePath);
                <#else>
                DynamicGenerator.doGenerate(inputFilePath,outputFilePath,model);
            </#if>

        </#list>
    }
}
