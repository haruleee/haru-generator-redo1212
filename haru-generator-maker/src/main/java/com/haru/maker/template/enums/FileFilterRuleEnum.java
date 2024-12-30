package com.haru.maker.template.enums;

import lombok.Getter;

/**
 * 文件过滤规则枚举
 * @author Haru
 * @date 2024/12/24 13:25
 */
@Getter
public enum FileFilterRuleEnum {

    CONTAINS("包含","contains"),
    STARTS_WITH("前缀匹配","startsWith"),
    ENDS_WITH("后缀匹配","endsWith"),
    REGEX("正则","regex"),
    EQUALS("相等","equals")
    ;

    private final String text;
    private final String value;

    FileFilterRuleEnum(String text, String value) {
        this.text = text;
        this.value = value;
    }

    /**
     * 根据value获取枚举值
     * @author Haru
     * @date 2024/12/24 13:31
     * @param value
     * @return FileFilterRangeEnum
     */
    public static FileFilterRuleEnum getEnumByValue(String value) {
        if (value==null) return null;
        for (FileFilterRuleEnum e : FileFilterRuleEnum.values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        return null;
    }
}
