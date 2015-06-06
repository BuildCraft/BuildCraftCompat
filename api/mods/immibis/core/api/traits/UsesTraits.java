
package mods.immibis.core.api.traits;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Classes containing a @TraitField field must use this or the transformer will skip them.
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface UsesTraits {

}
