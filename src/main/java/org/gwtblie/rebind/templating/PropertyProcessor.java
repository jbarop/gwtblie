package org.gwtblie.rebind.templating;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.google.gwt.core.ext.BadPropertyValueException;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.TreeLogger.Type;
import com.google.gwt.core.ext.UnableToCompleteException;

/**
 * Base of classes who process fields in the generator-class which are annotated by a given
 * annotation to read the corresponding property from the gwt.xml file.
 *
 * @author <a href="mailto:jb@barop.de">Johannes Barop</a>
 */
abstract class PropertyProcessor<T> {
  private final TemplatedGenerator generator;

  private final TreeLogger treeLogger;

  private final Class<? extends Annotation> annotation;

  private final String defaultPropertyName;

  PropertyProcessor(
      TemplatedGenerator generator,
      TreeLogger treeLogger,
      Class<? extends Annotation> annotation,
      String defaultPropertyName) {
    this.generator = generator;
    this.treeLogger = treeLogger;
    this.annotation = annotation;
    this.defaultPropertyName = defaultPropertyName;
  }

  /**
   * @return The "name"-value from the annotation.
   */
  protected abstract String readPropertyName(Field field);

  /**
   * @return Read the associated property from GWT.
   */
  protected abstract T readGwtProperty(String propertyName) throws BadPropertyValueException;

  /**
   * Assign the given value to the given field.
   */
  protected abstract void assign(Field field, T value) throws UnableToCompleteException;

  /**
   * Process all specified annotations and assign the value to the associated field.
   */
  final void process() throws UnableToCompleteException {
    for (Field field : Util.getFields(generator.getClass(), annotation)) {
      String propertyName = getPropertyName(field);
      T value;
      try {
        value = readGwtProperty(propertyName);
      } catch (BadPropertyValueException e) {
        treeLogger.log(Type.ERROR, "Could not find referenced property", e);
        throw new UnableToCompleteException();
      }
      assign(field, value);
    }
  }

  /**
   * Assign the given value to the given Field.
   */
  protected void assignField(Field field, Object value) throws UnableToCompleteException {
    try {
      field.set(generator, value);
    } catch (Exception e) {
      treeLogger.log(Type.ERROR, "Error assigning value to '" + field.getName() + "'.", e);
      throw new UnableToCompleteException();
    }
  }

  /**
   * @return The configured xml-name of the property.
   */
  private String getPropertyName(Field property) {
    PropertyPrefix prefixAnnotation = generator.getClass().getAnnotation(PropertyPrefix.class);
    String prefix = (prefixAnnotation != null) ? prefixAnnotation.value() : "";
    String name = readPropertyName(property);
    name = (defaultPropertyName.equals(name)) ? property.getName() : name;

    return prefix + name;
  }
}
