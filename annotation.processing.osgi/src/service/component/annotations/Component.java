package service.component.annotations;

@java.lang.annotation.Retention(value=java.lang.annotation.RetentionPolicy.RUNTIME)
@java.lang.annotation.Target(value={java.lang.annotation.ElementType.TYPE})
public @interface Component {
	 boolean immediat() default false;
	 String[] properties() default {};
}
