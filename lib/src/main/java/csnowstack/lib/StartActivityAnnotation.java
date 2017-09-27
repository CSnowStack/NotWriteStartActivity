package csnowstack.lib;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 生成 跳转Activity的代码
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface StartActivityAnnotation {
    /**
     * flag
     */
    int flag() default 789123;

    String keys() default "";
}
