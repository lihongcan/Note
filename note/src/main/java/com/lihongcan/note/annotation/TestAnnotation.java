package com.lihongcan.note.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by lihongcan on 2016/7/21.
 *
 * 注解类
 *
 * 元注解 @target 用来定义你的注解将用在什么地方，比如方法或字段上
 * 元注解 @retention 用来定义该注解在哪一个级别可用 在源代码中(SOURCE)、类文件中(CLASS)、运行时(RUNTIME)
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface TestAnnotation {
    /**
     * 在注解中，一般都会包含一些元素标示某些值，当分析处理注解时，程序或工具可用利用这些值，
     * 注解的元素看起来像接口的方法，唯一的区别是你可以为其指定默认值
     *
     * 没有包含元素的注解称为标记注解
     */
    int id();

    /**
     * 指定默认值 "a test annotation"
     * @return
     */
    String description() default "a test annotation";
}
