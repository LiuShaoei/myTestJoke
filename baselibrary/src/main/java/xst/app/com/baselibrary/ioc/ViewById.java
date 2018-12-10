package xst.app.com.baselibrary.ioc;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by LiuZhaowei on 2018/12/5 0005.
 * @Target(ElementType.FIELD) 代表annotation的位置, FIELD 代表属性  TYPE类上
 * @Retention(RetentionPolicy.CLASS)  什么时候生效  CLASS编译时,  RUNTIME运行时  SOURCE 源码资源
 */

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ViewById {
    int value();
}
