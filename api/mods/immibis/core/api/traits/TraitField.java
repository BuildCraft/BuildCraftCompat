package mods.immibis.core.api.traits;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Fields annotated with @TraitField are called trait fields.
 * 
 * The type of the field is either a class or interface which is a subtype of ITrait.
 * If the type of the field is a class, it is called the trait type.
 * If the type of the field is an interface registered in {@link ITrait#knownInterfaces} then the trait type is the
 * corresponding implementation class.
 * 
 * The class containing the field is called the user type.
 * The trait type must implement ITrait and have a constructor with a single Object argument.
 * The trait field will be automatically initialized with a new instance of the trait type.
 * The argument that is passed to the constructor is "this".
 * 
 * After transformation, all interfaces implemented by the trait type will also be implemented by the user type.
 * Those methods not already implemented by the user type will be given default implementations similar to this:
 * <pre>
 * @Override
 * public int interfaceMethod(int arg1, Object arg2) {
 * 		return traitField.interfaceMethod(arg1, arg2);
 * }
 * </pre>
 * 
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.FIELD)
public @interface TraitField {
}
