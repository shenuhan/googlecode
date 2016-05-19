package fr.android.api.display;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import fr.android.api.annotation.Managed;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Inherited
@Managed
public @interface Display {
	Class<?> displayed();
	Class<?> parent() default Object.class;
}
