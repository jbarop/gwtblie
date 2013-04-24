package org.gwtblie.rebind.templating;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Specifies the template which should be used to generate the file.
 *
 * @author <a href="mailto:jb@barop.de">Johannes Barop</a>
 *
 */
@Target({
  ElementType.TYPE
})
@Retention(RetentionPolicy.RUNTIME)
public @interface PropertyPrefix {
  String value();
}
