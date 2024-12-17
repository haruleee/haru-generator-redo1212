package ${basePackage}.cli.command;

import cn.hutool.core.util.ReflectUtil;
import ${basePackage}.model.DataModel;
import lombok.Data;
import picocli.CommandLine;

import java.lang.reflect.Field;

/**
 * @program: haru-generator-maker
 * @ClassName ConfigCommand
 * @description: TODO
 * @author: ${author}
 * @createtime: ${createTime}
 * @Version ${version}
 **/
@CommandLine.Command(name = "config",description = "查看参数信息",mixinStandardHelpOptions = true)
@Data
public class ConfigCommand implements Runnable{


    @Override
    public void run() {
        System.out.println("查看参数信息");
        System.out.println("=============================");
        Field[] fields = ReflectUtil.getFields(DataModel.class);
        for (Field field : fields) {
            System.out.println("参数名:" + field.getName());
            System.out.println("参数类型:" + field.getType());
            System.out.println("=============================");

        }
    }
}
