package ${basePackage}.cli;

import ${basePackage}.cli.command.ConfigCommand;
import ${basePackage}.cli.command.GenerateCommand;
import ${basePackage}.cli.command.ListCommand;
import picocli.CommandLine;

/**
 * @program: haru-generator-maker
 * @ClassName CommandExecutor
 * @description: 命令执行器
 * @author: ${author}
 * @createtime: ${createTime}
 * @Version ${version}
 **/

//设置命令名,并开启帮助文档生成选项
@CommandLine.Command(name = "${name}", mixinStandardHelpOptions = true)
public class CommandExecutor implements Runnable{
    private final CommandLine commandLine;
    {
        commandLine = new CommandLine(this)
                .addSubcommand(new GenerateCommand())
                .addSubcommand(new ListCommand())
                .addSubcommand(new ConfigCommand());
    }
    @Override
    public void run() {
        //不输入子命令时给出友好提示
        System.out.println("请输入具体命令,或者输入 --help 查看命令提示");
    }


    /**
     * 执行命令
     * @author ${author}
     * @date 2024/12/15 11:50
     * @param args
     * @return Integer
     */
    public Integer doExecute(String[] args) {
        return commandLine.execute(args);
    }
}
