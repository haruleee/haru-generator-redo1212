package ${basePackage}.cli.command;

import cn.hutool.core.io.FileUtil;
import lombok.Data;
import picocli.CommandLine;
import java.io.File;
import java.util.List;

/**
 * @program: haru-generator-maker
 * @ClassName ListCommand
 * @description: TODO
 * @author: ${author}
 * @createtime: ${createTime}
 * @Version ${version}
 **/
@CommandLine.Command(name = "list", description = "查看文件列表", mixinStandardHelpOptions = true)
public class ListCommand implements Runnable {
    @Override
    public void run() {
        String projectPath = System.getProperty("user.dir");
        //整个项目的根路径
        File parentFile = new File(projectPath).getParentFile();
        //输入路径
        String inputPath = "${fileConfig.inputRootPath}";
        List<File> files = FileUtil.loopFiles(inputPath);
        for (File file : files) {
            System.out.println(file);
        }

    }
}
