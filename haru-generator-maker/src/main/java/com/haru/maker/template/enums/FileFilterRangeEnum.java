package com.haru.maker.template.enums;

import lombok.Getter;

/**
 * 文件过滤范围枚举
 * @author Haru
 * @date 2024/12/24 13:25
 */
@Getter
public enum FileFilterRangeEnum {

    FILE_NAME("文件名称","fileName"),
    FILE_CONTENT("文件内容","fileContent"),
    ;

    private final String text;
    private final String value;

    FileFilterRangeEnum(String text, String value) {
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


    public static FileFilterRangeEnum getEnumByValue(String value) {
        if (value==null) return null;
        for (FileFilterRangeEnum e : FileFilterRangeEnum.values()) {
            if (e.value.equals(value)) {
                return e;
            }
        }
        return null;
    }
}
