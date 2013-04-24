package org.gwtblie.rebind.templating;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.google.gwt.core.ext.ConfigurationProperty;
import com.google.inject.BindingAnnotation;

/**
 * Specifies fields which should be initialized with the corresponding {@link ConfigurationProperty}
 * from GWT.
 *
 * <p>
 * The field must be <code>public</code> so that a value can be set.
 * </p>
 *
 * @author <a href="mailto:jb@barop.de">Johannes Barop</a>
 *
 */
@BindingAnnotation
@Target({
  ElementType.FIELD
})
@Retention(RetentionPolicy.RUNTIME)
public @interface Configuration {
  String name() default "";
}
