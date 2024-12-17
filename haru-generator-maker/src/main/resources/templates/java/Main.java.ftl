package ${basePackage};

import ${basePackage}.cli.CommandExecutor;

public class Main {
    public static void main(String[] args) {


        CommandExecutor commandExecutor = new CommandExecutor();

<#--        if ("generate".equalsIgnoreCase(args[0]) && args.length < 4) {-->
<#--            String[] argsNew = {"generate"<#list modelConfig.models as modelInfo><#if modelInfo.abbr??>, "-${modelInfo.abbr}"</#if></#list> };-->
<#--            args =argsNew;-->
<#--        }-->
        commandExecutor.doExecute(args);
    }
}