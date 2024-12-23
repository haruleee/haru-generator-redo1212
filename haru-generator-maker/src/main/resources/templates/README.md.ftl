# ${name}


> ${description}
>
> 作者: ${author}
>
> HaruLee的 代码生成器项目

可以通过命令行交互式输入的方式动态生成想要的项目代码

## 使用说明

执行项目根目录下的脚本文件:

示例命令:

generator generate <#list modelConfig.models as modelInfo><#if modelInfo.abbr??> -${modelInfo.abbr} </#if></#list>

参数说明:
<#list modelConfig.models as modelInfo>
<#if modelInfo.groupKey??>


${modelInfo?index+1}) ${modelInfo.groupName}

分组类型: ${modelInfo.type}

分组描述: ${modelInfo.description}

<#list modelInfo.models as modelInfo>

    ${modelInfo?index+1}) ${modelInfo.fieldName}

    参数名: ${modelInfo.fieldName}

    参数类型: ${modelInfo.type}

    参数默认值: ${modelInfo.defaultValue?c}

    参数缩写:<#if modelInfo.abbr??> ${modelInfo.abbr} </#if>

    参数描述: ${modelInfo.description}

    </#list>

<#else>


${modelInfo?index+1}) ${modelInfo.fieldName}

参数名: ${modelInfo.fieldName}

参数类型: ${modelInfo.type}

参数默认值: ${modelInfo.defaultValue?c}

参数缩写:<#if modelInfo.abbr??> ${modelInfo.abbr} </#if>

参数描述: ${modelInfo.description}

</#if>



</#list>