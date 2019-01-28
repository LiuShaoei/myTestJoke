package lzw.app.com.essayjoke.utils;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by LiuZhaowei on 2019/1/28 0028.
 * 标记切点注解
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.CLASS)//编译时
public @interface CheckNet {

}
