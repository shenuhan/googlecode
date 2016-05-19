package fr.android.api.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
public @interface Listen {
	public enum Priority {
		VeryLow(4),Low(3),Medium(2),High(1),VeryHigh(0);
		
		private final int value;
		private Priority(int value) {
			this.value = value;
		}
		public int getValue() {
			return this.value;
		}
	}
	
	Class<?> senderClass();
	String event();
	boolean async() default false;
	Priority priority() default Priority.Medium;
}
