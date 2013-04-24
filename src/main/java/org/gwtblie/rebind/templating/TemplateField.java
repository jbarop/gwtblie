package org.gwtblie.rebind.templating;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies fields which should be exported to the template.
 *
 * <p>
 * The field must be <code>public</code> so that the value can be read.
 * </p>
 *
 * @author <a href="mailto:jb@barop.de">Johannes Barop</a>
 *
 */
@Target({
  ElementType.FIELD
})
@Retention(RetentionPolicy.RUNTIME)
public @interface TemplateField {}
