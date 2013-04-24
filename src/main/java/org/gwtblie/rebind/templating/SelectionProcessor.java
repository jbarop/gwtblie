package org.gwtblie.rebind.templating;

import java.lang.reflect.Field;

import com.google.gwt.core.ext.BadPropertyValueException;
import com.google.gwt.core.ext.PropertyOracle;
import com.google.gwt.core.ext.SelectionProperty;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.core.ext.UnableToCompleteException;
import com.google.inject.Inject;

/**
 * Processes all fields in the generator-class annotated with {@link Selection} and assigns the
 * corresponding value from the gwt.xml file.
 *
 * @author <a href="mailto:jb@barop.de">Johannes Barop</a>
 *
 */
class SelectionProcessor extends PropertyProcessor<SelectionProperty> {
  private final TreeLogger treeLogger;

  private final PropertyOracle propertyOracle;

  @Inject
  SelectionProcessor(TemplatedGenerator generator, TreeLogger treeLogger, PropertyOracle propertyOracle) {
    super(generator, treeLogger, Selection.class, "");
    this.treeLogger = treeLogger;
    this.propertyOracle = propertyOracle;
  }

  @Override
  protected String readPropertyName(Field field) {
    return field.getAnnotation(Selection.class).name();
  }

  @Override
  protected SelectionProperty readGwtProperty(String propertyName) throws BadPropertyValueException {
    return propertyOracle.getSelectionProperty(treeLogger, propertyName);
  }

  @Override
  protected void assign(Field field, SelectionProperty value) throws UnableToCompleteException {
    Class<?> fieldType = field.getType();

    if (SelectionProperty.class.isAssignableFrom(fieldType)) {
      assignField(field, value);
    } else {
      assignField(field, value.getCurrentValue());
    }

  }
}
