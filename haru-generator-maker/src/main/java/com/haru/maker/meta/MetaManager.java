package com.haru.maker.meta;

import cn.hutool.core.io.resource.ResourceUtil;
import cn.hutool.json.JSONUtil;

/**
 * @program: haru-generator-maker
 * @ClassName MetaManager
 * @description: 单例模式管理Meta对象
 * @author: HaruLee
 * @createtime: 2024/12/15 18:14
 * @Version 1.0
 **/
public class MetaManager {

    private static volatile Meta meta;

    private MetaManager() {
    //私有化构造函数,防止外部实例化
    }

     public static Meta getMetaObject() {
        if (meta == null) {
            synchronized (MetaManager.class) {
                if (meta == null) {
                    meta = initMeta();
                }
            }
        }
        return meta;
     }

    private static Meta initMeta() {

        String metaJson = ResourceUtil.readUtf8Str("meta.json");

        Meta newMeta = JSONUtil.toBean(metaJson, Meta.class);
        Meta.FileConfig fileConfig = newMeta.getFileConfig();
        //TODO 校验和处理默认值
        return newMeta;

    }
}
