package com.xingye.data.content.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    // 插入时自动填充
    @Override
    public void insertFill(MetaObject metaObject) {
        // 主键ID：若使用数据库自增，无需手动填充（MyBatis-Plus会自动映射）
        // 创建时间
        setFieldValByName("created", LocalDateTime.now(), metaObject);
        setFieldValByName("lastUpdated", LocalDateTime.now(), metaObject); // 插入时同时设置更新时间（可选）
    }

    // 更新时自动填充（可选）
    @Override
    public void updateFill(MetaObject metaObject) {
        // 可在此添加更新时间填充逻辑
        setFieldValByName("lastUpdated", LocalDateTime.now(), metaObject);
    }
}
