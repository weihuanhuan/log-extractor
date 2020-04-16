package bootstrap;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by JasonFitch on 4/16/2020.
 */
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface LogOption {

    public String opt();

    public String longOpt();

    public boolean require() default false;

    public boolean hasArg() default true;

    public String defaultArg() default "";

    public String description() default "";
}
