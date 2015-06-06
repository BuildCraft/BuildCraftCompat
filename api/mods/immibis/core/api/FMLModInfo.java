package mods.immibis.core.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Information from this annotation will be copied into the
 * mcmod.info file by AutoPublisher.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.CLASS)
public @interface FMLModInfo {
	public String description();
	public String url();
	public String modid() default "";
	public String name() default "";
	public String updateUrl() default "";
	/** Semicolon-separated list */
	public String authors();
	public String credits() default "";
	public String logoFile() default "";
	/** Semicolon-separated list */
	public String screenshots() default "";
	public String parent() default "";
}
